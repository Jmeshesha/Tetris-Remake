package com.meshesha.tetrisremake.sates;


import com.meshesha.tetrisremake.drawables.Grid;
import com.meshesha.tetrisremake.drawables.PieceHolder;
import com.meshesha.tetrisremake.drawables.Pieces.*;
import com.meshesha.tetrisremake.main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.HashMap;
import java.util.Random;

public class Game extends TetrisState {
    public static final int ID = 2;
    private StateBasedGame game;
    private Grid grid;
    private BasicPiece piece;
    private BasicPiece endLocation;
    private PieceHolder heldPiece;
    private PieceHolder nextPiece;
    private int timeSinceUpdate;
    private int timeBetweenUpdate;
    private boolean canHold;
    private HashMap<Integer, Float> keysHeld;
    private boolean isPaused = false;
    private int top;
    private BasicPiece[] pieceType;


    @Override
    public int getID() {
        return ID;
    }


    /*
     * On enter, the piece types are initialized (whether that be the custom or default pieces) then staring piece and next piece are randomized
     */
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if(!isPaused) {
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
        isPaused = false;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        this.game = game;
        float blockWidth = 50f;
        float blockHeight = 50f;
        canHold = true;

        grid = new Grid(960 - (blockWidth) * 5, 10, blockWidth, blockHeight);
        grid.setTop(top);
        background = new Image("res/images/Background.png");
        grid.initStats(container.getWidth(), container.getHeight());
        heldPiece = new PieceHolder(grid.startX - (3.5f * grid.blockWidth) - 5, grid.startY + grid.blockHeight, 0.7f * grid.blockWidth, 0.7f * grid.blockHeight, "Hold", container.getWidth(), container.getHeight());
        nextPiece = new PieceHolder(grid.startX + grid.blockWidth * 9 + 5, grid.startY + grid.blockHeight, 0.7f * grid.blockWidth, 0.7f * grid.blockHeight, "Next", container.getWidth(), container.getHeight());

        keysHeld = new HashMap<>();
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        background.draw(0, 0, container.getWidth(), container.getHeight());

        grid.drawBackground(g, container.getWidth(), container.getHeight());
        piece.draw(grid.startX, grid.startY, grid.blockWidth, grid.blockHeight, container.getWidth(), container.getHeight());
        endLocation.draw(grid.startX, grid.startY, grid.blockWidth, grid.blockHeight, container.getWidth(), container.getHeight());
        grid.draw(container.getWidth(), container.getHeight());
        heldPiece.drawBackground(g);
        nextPiece.drawBackground(g);
        // Only draws piece if not null to avoid null pointer exception
        if (heldPiece.getPiece() != null) {
            heldPiece.draw();
        }
        nextPiece.draw();

        grid.drawStats(g);


    }


    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        //Custom made held key method because slick library doesn't include one see method for details
        keyHeld(delta);

        // This updates the downwards speed of the piece based on level
        timeBetweenUpdate = (int) ((grid.tpl[grid.level] / 60.0) * 1000);
        //Loop here to make sure that the end location is as far down as possible (method returns false if it fails to move down)
        while (endLocation.moveDown(grid));
        timeSinceUpdate += delta;
        //Moves down until it fails then places piece
        if (timeSinceUpdate >= timeBetweenUpdate) {

            if (!piece.moveDown(grid)) {
                placePiece();
            }
            timeSinceUpdate = 0;
        }

    }

    /* Custom Held Key input method implemented using a hash table (each key must be held for certain amount to avoid it being too sensitive)
     *
     * S Key: moves piece down and makes sure that the piece timer for moving down is reset
     * D Key: moves piece right
     * A Key: moves piece left
     */
    private void keyHeld(int delta) {
        boolean resetTime = false;
        keysHeld.replaceAll((key, time) -> time + delta);
        try {
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
    // Removes held keys from hashtable
    @Override
    public void keyReleased(int key, char c) {
        keysHeld.remove(key);
    }

    /*
     * S Key: moves piece down and makes sure that the piece timer for moving down is reset (Can be held down)
     * D Key: moves piece right (Can be held down)
     * A Key: moves piece left (Can be held down)
     * W Key: rotates piece about its rotation point if exists
     * Space Key: piece hard drops to end location
     * E Key: put piece into hold position. If the hold position is already filled swap the current piece and hold piece
     * Escape Key: Pause game
     *
     *
     * all pressed keys are put into hash table here
     * */

    @Override
    public void keyPressed(int key, char c) {
        keysHeld.put(key, -250f);
        boolean resetTime = false;
        try {
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
                isPaused = true;
                game.enterState(PauseState.ID, new PauseTransition(25), new EmptyTransition());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resetTime)
            timeSinceUpdate = 0;
        super.keyPressed(key, c);
    }


    private void randomizePiece() throws SlickException, CloneNotSupportedException {
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
        if (nextPiece.getPiece().getMinY() != null) {
            nextPiece.getPiece().moveDown(-nextPiece.getPiece().getMinY());
        } else {
            nextPiece.getPiece().moveDown(2);
        }


    }

    private void placePiece() {
        //Puts piece into grid array if it fails then go to game over state
        for (Block block : piece.getStructure()) {
            try {
                grid.putBlockToGrid(block);
            } catch (ArrayIndexOutOfBoundsException e) {
                game.enterState(GameOver.ID, new FadeOutTransition(new Color(0.25f, 0f, 0f), 500), new FadeInTransition(new Color(0.25f, 0f, 0f), 1000));
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

    //When game over, the top score is saved and variables are reinitialized to speed up load time on enter
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        if (!isPaused) {
            if (top < grid.getTop()) {
                top = grid.getTop();
            }
            init(container, game);
        }


        super.leave(container, game);


    }
}
