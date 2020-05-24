package Game;


import Main.Main;
import Pieces.*;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

public class Game  extends BasicGameState  {
    public static final int ID = 1;
    private StateBasedGame game;
    private Grid grid;
    BasicPiece piece;
    BasicPiece endLocation;
    BasicPiece heldPiece;
    BasicPiece nextPiece;
    int timeSinceUpdate = 0;
    int timeBetweenUpdate =  1000;
    Block[] pieceStructure;
    boolean canHold = true;
    Image background;

    @Override
    public int getID() {
        return ID;
    }
    /**
     * Init method is called once
     * @param container
     * @param game
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        grid = new Grid(500, 10, 50);
        background = new Image("res/images/background.jpg");
        try {
            randomizePiece();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Render method is periodically called to render graphcics
     * @param container
     * @param game
     * @param g This is one way of rendering graphics
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, 1920, 1080);
        grid.drawBackground(g);
        piece.draw(grid.startX, grid.startY, grid.blockSize);
        endLocation.draw(grid.startX, grid.startY, grid.blockSize);
        grid.draw(g);


        if(heldPiece != null) heldPiece.draw(10, 10, 20);

        grid.drawStats(g);
    }
    /**
     * Update method is periodically called
     * @param container
     * @param game
     * @param delta This is used for getting the amount of ms passed since this method has been called
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        timeBetweenUpdate = (int) ((grid.tpl[grid.level]/60.0)*1000);
        pieceStructure = piece.getStructure();
        while(endLocation.moveDown(grid));
        timeSinceUpdate += delta;
        if(timeSinceUpdate >= timeBetweenUpdate){
            if(!piece.moveDown(grid)){
                placePiece();
            }
            timeSinceUpdate = 0;
        }

    }

    /**
     * Keyboard input detection method is periodically
     * @param key This is the specific keyboard key that is pressed in numerical form
     * @param c this is specific keyboard key that is pressed in char form
     */
    @Override
    public void keyPressed(int key, char c) {
        boolean resetTime = false;
        try {
            //Press S to move piece down
            if (key == Input.KEY_S) {
                resetTime = piece.moveDown(grid);

            }else if (key == Input.KEY_D) {

                piece.moveSideways(grid,1);
                endLocation.moveUp(grid, (int)(endLocation.getCenterY() - piece.getCenterY()));
                endLocation.moveSideways(grid, 1);

            }else if (key == Input.KEY_A) {
                piece.moveSideways(grid, -1);
                endLocation.moveUp(grid, (int)(endLocation.getCenterY() - piece.getCenterY()));
                endLocation.moveSideways(grid, -1);
                while(endLocation.moveDown(grid));
            }else if (key == Input.KEY_W) {
                resetTime = piece.rotate(grid);
                endLocation.moveUp(grid, (int)(endLocation.getCenterY() - piece.getCenterY()));
                endLocation.rotate(grid);
            }else if(key == Input.KEY_SPACE){
                piece.moveUp(grid, (int)(piece.getCenterY() - endLocation.getCenterY()));
                timeSinceUpdate = timeBetweenUpdate;
            }else if(key == Input.KEY_E && canHold){
                if(heldPiece == null){
                    heldPiece = piece;
                    randomizePiece();
                }else{
                    BasicPiece copy = heldPiece;
                    heldPiece = piece;
                    piece = copy;
                    endLocation = (BasicPiece)piece.clone();
                    endLocation.init(true);
                    piece.init(false);

                }
                heldPiece.init(false);
                heldPiece.moveDown(grid);
                heldPiece.moveDown(grid);
                canHold = false;
            }

        } catch (SlickException | CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(resetTime)
            timeSinceUpdate = 0;
        super.keyPressed(key, c);
    }

    /**
     * Method to randomize the current piece being moved.
     * @throws SlickException
     */
    public void randomizePiece() throws SlickException, CloneNotSupportedException {
        Random rand = new Random();
        int selection = rand.nextInt(7);
        BasicPiece[] pieceType = {new L_Piece(), new J_Piece(), new I_Piece(),
                new T_Piece(), new O_Piece(), new S_Piece(), new Z_Piece()};
        piece = pieceType[selection];
        endLocation = (BasicPiece)piece.clone();

        endLocation.init(true);
        piece.init(false);
        canHold = true;

    }
    /**
     * Method to place the current piece
     */
    public void placePiece(){
        //Puts piece into grid array
        for (Block block : pieceStructure) {
            try {
                grid.putBlockToGrid(block);
            }catch(java.lang.ArrayIndexOutOfBoundsException e){
                game.enterState(4);
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
}
