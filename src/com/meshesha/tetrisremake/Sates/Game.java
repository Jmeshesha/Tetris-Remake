package com.meshesha.tetrisremake.Sates;


import com.meshesha.tetrisremake.Drawables.Grid;
import com.meshesha.tetrisremake.Drawables.PieceHolder;
import com.meshesha.tetrisremake.Drawables.Pieces.*;
import com.meshesha.tetrisremake.Drawables.TextUI;
import com.meshesha.tetrisremake.Main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.HashMap;
import java.util.Random;

public class Game extends TetrisState {
    public static final int ID = 1;
    private StateBasedGame game;
    private Grid grid;
    BasicPiece piece;
    BasicPiece endLocation;
    PieceHolder heldPiece;
    PieceHolder nextPiece;
    int timeSinceUpdate;
    int timeBetweenUpdate;
    Block[] pieceStructure;
    boolean canHold;
    Image background;
    Image clear;
    HashMap<Integer, Float> keysHeld;
    float alphaChange;
    float valueChange;
    boolean isGameOver;
    TextUI paused;
    int top;
    Image foreground;
    BasicPiece[] pieceType;


    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if (Main.selectedPieces == null) {
            pieceType = new BasicPiece[]{new L_Piece(), new J_Piece(), new I_Piece(),
                    new T_Piece(), new O_Piece(), new S_Piece(), new Z_Piece()};
        } else {
            pieceType = Main.selectedPieces;
        }
        try {
            randomizePiece();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Init method is called once
     *
     * @param container
     * @param game
     */

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        isGameOver = false;
        this.game = game;
        alphaChange = 0;
        valueChange = 1;
        canHold = true;

        grid = new Grid(Main.getScreenWidth() / (2f) - (Main.getScreenWidth() * (50f / 1920f)) * 5, Main.getScreenHeight() * 10f / 1080f, Main.getScreenWidth() * (50f / 1920f), (Main.getScreenHeight() * 50f) / 1080f);
        grid.setTop(top);
        background = new Image("res/images/Background2.png");
        grid.initStats();
        foreground = new Image(background.getResourceReference());
        foreground.setImageColor(1, 1, 1, 0);
        heldPiece = new PieceHolder(grid.startX - (3.5f * grid.blockWidth) - 5, grid.startY + grid.blockHeight, 0.7f * grid.blockWidth, 0.7f * grid.blockHeight, "Hold");
        nextPiece = new PieceHolder(grid.startX + grid.blockWidth * 9 + 5, grid.startY + grid.blockHeight, 0.7f * grid.blockWidth, 0.7f * grid.blockHeight, "Next");

        keysHeld = new HashMap<>();
        paused = new TextUI(90, "Paused", "Square", Color.white);
        setBackground(background);
    }

    /**
     * Render method is periodically called to render graphcics
     *
     * @param container
     * @param game
     * @param g         This is one way of rendering graphics
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, 1920, 1080);

        grid.drawBackground(g);
        piece.draw(grid.startX, grid.startY, grid.blockWidth, grid.blockHeight);
        endLocation.draw(grid.startX, grid.startY, grid.blockWidth, grid.blockHeight);
        grid.draw(g);
        heldPiece.drawBackground(g);
        nextPiece.drawBackground(g);

        if (heldPiece.getPiece() != null) {
            heldPiece.draw(g);
        }
        nextPiece.draw(g);

        grid.drawStats(g);


    }

    /**
     * Update method is periodically called
     *
     * @param container
     * @param game
     * @param delta     This is used for getting the amount of ms passed since this method has been called
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        keyHeld(delta);

        timeBetweenUpdate = (int) ((grid.tpl[grid.level] / 60.0) * 1000);
        pieceStructure = piece.getStructure();
        while (endLocation.moveDown(grid)) ;
        timeSinceUpdate += delta;
        if (timeSinceUpdate >= timeBetweenUpdate) {
            if (!piece.moveDown(grid)) {
                placePiece();
            }
            timeSinceUpdate = 0;
        }
        System.out.println(piece.getCenterX() + " " + piece.getCenterY());
    }


    public void keyHeld(int delta) {
        boolean resetTime = false;
        keysHeld.replaceAll((key, time) -> Float.valueOf(time + delta));
        try {
            //Press S to move piece down
            if (keysHeld.containsKey(Input.KEY_S) && keysHeld.get(Input.KEY_S) > 75) {

                resetTime = piece.moveDown(grid);
                keysHeld.replace(Input.KEY_S, 0f);

            } else if (keysHeld.containsKey(Input.KEY_D) && keysHeld.get(Input.KEY_D) > 75) {

                piece.moveSideways(grid, 1);
                endLocation.moveUp(grid, (int) (endLocation.getCenterY() - piece.getCenterY()));
                endLocation.moveSideways(grid, 1);
                keysHeld.replace(Input.KEY_D, 0f);

            } else if (keysHeld.containsKey(Input.KEY_A) && keysHeld.get(Input.KEY_A) > 75) {
                piece.moveSideways(grid, -1);
                endLocation.moveUp(grid, (int) (endLocation.getCenterY() - piece.getCenterY()));
                endLocation.moveSideways(grid, -1);
                keysHeld.replace(Input.KEY_A, 0f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resetTime)
            timeSinceUpdate = 0;

    }

    @Override
    public void keyReleased(int key, char c) {
        keysHeld.remove(key);
    }

    /**
     * Keyboard input detection method is periodically
     *
     * @param key This is the specific keyboard key that is pressed in numerical form
     * @param c   this is specific keyboard key that is pressed in char form
     */

    @Override
    public void keyPressed(int key, char c) {
        keysHeld.put(key, -250f);
        boolean resetTime = false;
        try {
            //Press S to move piece down
            if (key == Input.KEY_S) {

                resetTime = piece.moveDown(grid);

            } else if (key == Input.KEY_D) {

                piece.moveSideways(grid, 1);
                endLocation.moveUp(grid, (int) (endLocation.getCenterY() - piece.getCenterY()));
                endLocation.moveSideways(grid, 1);

            } else if (key == Input.KEY_A) {
                piece.moveSideways(grid, -1);
                endLocation.moveUp(grid, (int) (endLocation.getCenterY() - piece.getCenterY()));
                endLocation.moveSideways(grid, -1);
            } else if (key == Input.KEY_W) {
                resetTime = piece.rotate(grid);
                endLocation.moveUp(grid, (int) (endLocation.getCenterY() - piece.getCenterY()));
                endLocation.rotate(grid);
            } else if (key == Input.KEY_SPACE) {
                piece.moveUp(grid, (int) (piece.getCenterY() - endLocation.getCenterY()));
                timeSinceUpdate = timeBetweenUpdate;
            } else if (key == Input.KEY_E && canHold) {
                if (heldPiece.getPiece() == null) {
                    heldPiece.setPiece(piece);
                    randomizePiece();
                } else {
                    BasicPiece copy = heldPiece.getPiece();
                    heldPiece.setPiece((BasicPiece) piece.clone());
                    piece = copy;
                    endLocation = (BasicPiece) piece.clone();
                    endLocation.init(true);
                    piece.init(false);

                }
                heldPiece.getPiece().init(false);
                heldPiece.getPiece().moveDown(grid);
                heldPiece.getPiece().moveDown(grid);


                canHold = false;
            } else if (key == Input.KEY_ESCAPE) {

                //pause.initSingle();
                //isPaused = !isPaused;
                game.enterState(6, new PauseTransition(25), new EmptyTransition());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resetTime)
            timeSinceUpdate = 0;
        super.keyPressed(key, c);
    }

    /**
     * Method to randomize the current piece being moved.
     *
     * @throws SlickException
     */
    public void randomizePiece() throws SlickException, CloneNotSupportedException {
        Random rand1 = new Random();
        Random rand2 = new Random();
        int selection1 = rand1.nextInt(pieceType.length);
        int selection2 = rand2.nextInt(pieceType.length);

        if (nextPiece.getPiece() != null) {
            piece = (BasicPiece) nextPiece.getPiece().clone();

        } else {
            piece = (BasicPiece) pieceType[selection1].clone();
        }
        nextPiece.setPiece((BasicPiece) pieceType[selection2].clone());

        endLocation = (BasicPiece) piece.clone();

        nextPiece.getPiece().init(false);
        endLocation.init(true);
        piece.init(false);
        canHold = true;
        if( nextPiece.getPiece().getMinY() != null){
            nextPiece.getPiece().moveDown(-nextPiece.getPiece().getMinY());
        }else{
            nextPiece.getPiece().moveDown(2);
        }


    }

    /**
     * Method to place the current piece
     */
    public void placePiece() {
        //Puts piece into grid array
        for (Block block : pieceStructure) {
            try {
                grid.putBlockToGrid(block);
            } catch (ArrayIndexOutOfBoundsException e) {
                game.enterState(4, new FadeOutTransition(new Color(0.25f, 0f, 0f), 500), new FadeInTransition(new Color(0.25f, 0f, 0f), 1000));
                isGameOver = true;
            }
        }
        //check if there are any line clears
        grid.checkLines();

        //randomize piece
        try {
            randomizePiece();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        if (isGameOver) {
            if (top < grid.getTop()) {
                top = grid.getTop();
            }
            init(container, game);
        }


        super.leave(container, game);


    }
}
