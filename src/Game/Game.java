package Game;

import Game.Pieces.*;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

public class Game extends BasicGameState {
    public static final int ID = 1;
    private StateBasedGame game;
    private Grid grid;
    BasicPiece piece;
    int timeSinceUpdate = 0;
    int timeBetweenUpdate = 200;
    @Override

    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        grid = new Grid(500, 0);
        randomizePiece();


    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        piece.draw(grid.startX, grid.startY);
        grid.drawGrid();
        for(int k = 0; k <= 9; k++) {
            g.drawLine(grid.startX + k * 50, grid.startY, grid.startX + k * 50, grid.startY + 21 * 50);
        }
        for(int j = 0; j<= 21; j++){
            g.drawLine(grid.startX, grid.startY+ j*50, grid.startX + 9*50, grid.startY+j*50);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        boolean stopMoving = false;
        timeSinceUpdate += delta;
        if(timeSinceUpdate >= timeBetweenUpdate){
            for(int i = 0; i < piece.getStructure().length; i++){
                if( piece.getStructure()[i].getY() >= 20 ||grid.isFilled(piece.getStructure()[i].getX(), piece.getStructure()[i].getY()+1)){
                    stopMoving = true;
                }
            }
            if(stopMoving){
                for(int i = 0; i < piece.getStructure().length; i++ ){
                    grid.putBlockToGrid(piece.getStructure()[i]);
                }
                randomizePiece();
            }else{
                piece.moveDown();
            }
            timeSinceUpdate = 0;
        }

    }

    @Override
    public void keyPressed(int key, char c) {
        boolean moveLeft = true;
        boolean moveRight = true;
        for(int i = 0; i < piece.getStructure().length; i++){
            if(piece.getStructure()[i].getX() <= 0){
                moveLeft = false;
            }else if(piece.getStructure()[i].getX() >=8){
                moveRight = false;

            }
        }
        if(key == Input.KEY_S){
            piece.moveDown();
        }
        if(key == Input.KEY_D && moveRight){

            piece.moveSideways(1);
        }
        if(key == Input.KEY_A && moveLeft){
            piece.moveSideways(-1);
        }
        if(key == Input.KEY_W){
            piece.rotate();
        }
        super.keyPressed(key, c);
    }
    public void randomizePiece() throws SlickException{
        Random rand = new Random();
        int selection = rand.nextInt(6);
        switch(selection){
            case 0:
                piece = new L_Piece();
                break;
            case 1:
                piece = new J_Piece();
                break;
            case 2:
                piece = new I_Piece();
                break;
            case 3:
                piece = new T_Piece();
                break;
            case 4:
                piece = new S_Piece();
                break;
            case 5:
                piece = new Z_Piece();
        }
    }
}
