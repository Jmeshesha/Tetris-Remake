package Game;

import Main.Main;
import Pieces.*;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Game  extends BasicGameState  {
    public static final int ID = 1;
    private StateBasedGame game;
    private Grid grid;
    BasicPiece piece;
    BasicPiece endLocation;
    BasicPiece storedPiece;
    int timeSinceUpdate = 0;
    int timeBetweenUpdate =  1000;
    int timeSinceDown = 0;
    Block[] pieceStructure;
    boolean stopMoving = false;

    @Override

    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        grid = new Grid(500, 0);
        try {
            randomizePiece();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        piece.draw(grid.startX, grid.startY);
        endLocation.draw(grid.startX, grid.startY);
        grid.drawGrid();
        for(int k = 0; k <= 9; k++) {
            g.drawLine(grid.startX + k * 50, grid.startY, grid.startX + k * 50, grid.startY + 21 * 50);
        }
        for(int j = 0; j<= 21; j++){
            g.drawLine(grid.startX, grid.startY+ j*50, grid.startX + 9*50, grid.startY+j*50);
        }
        grid.drawScoreAndLvl(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        timeBetweenUpdate = (int) (250*(2.0/(grid.level)));
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

    @Override
    public void keyPressed(int key, char c) {
        boolean resetTime = false;
        try {
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
            }

        } catch (SlickException e) {
            e.printStackTrace();
        }


        if(resetTime) timeSinceUpdate = 0;
        super.keyPressed(key, c);
    }
    public void randomizePiece() throws SlickException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Random rand = new Random();
        int selection = rand.nextInt(7);
        BasicPiece[] pieceType = {new L_Piece(false), new J_Piece(false), new I_Piece(false),
                new T_Piece(false), new O_Piece(false), new S_Piece(false), new Z_Piece(false)};
        BasicPiece[] endLocType = {new L_Piece(true), new J_Piece(true), new I_Piece(true),
                new T_Piece(true), new O_Piece(true), new S_Piece(true), new Z_Piece(true)};
        piece = pieceType[selection];
        endLocation = endLocType[selection];
    }
    public void placePiece(){
        for (Block block : pieceStructure) {
            try {
                grid.putBlockToGrid(block);
            }catch(java.lang.ArrayIndexOutOfBoundsException e){
                game.enterState(4);
            }
        }
        grid.checkLines();
        try {
            randomizePiece();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
