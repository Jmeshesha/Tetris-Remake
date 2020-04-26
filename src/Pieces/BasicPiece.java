package Pieces;

import Game.Grid;
import org.newdawn.slick.SlickException;



public abstract class BasicPiece  {
    protected Block[] structure;
    protected double centerX;
    protected double centerY;
    protected int size;
    protected boolean canRotate = true;
    public BasicPiece(int size)throws SlickException{
        structure = new Block[size];

    }
    public abstract String getCurrentColor(boolean isEndLoc) throws SlickException;
    public void draw(float startX, float startY){
        for(int i = 0; i<structure.length; i++){
            structure[i].draw(startX, startY);
        }
    }
    public boolean moveUp(Grid grid, int x) throws SlickException {
        Block[] copy = new Block[structure.length];
        for(int i = 0; i< structure.length; i++){
            copy[i] = new Block(structure[i].getColorReference(), structure[i].getX(), structure[i].getY());
            copy[i].moveDown(-x);
            if(!grid.canMoveHere(copy[i].getX(), copy[i].getY())){
                return false;
            }
        }
        centerY -= x;

        structure = copy;
        return true;
    }
    public boolean rotate(Grid grid) throws SlickException {
        if(!canRotate)
            return false;
        Block[] copy = new Block[structure.length];
        for(int i = 0; i< structure.length; i++){
            copy[i] = new Block(structure[i].getColorReference(), structure[i].getX(), structure[i].getY());
            copy[i].rotate(centerX, centerY);
            if(!grid.canMoveHere(copy[i].getX(), copy[i].getY())){
                return false;
            }
        }

        structure = copy;
        return true;
    }
    public boolean moveSideways(Grid grid, int x) throws SlickException {
        Block[] copy = new Block[structure.length];
        for(int i = 0; i< structure.length; i++){
            copy[i] = new Block(structure[i].getColorReference(), structure[i].getX(), structure[i].getY());
            copy[i].moveSide(x);
            if(!grid.canMoveHere(copy[i].getX(), copy[i].getY())){
                return false;
            }
        }
        structure = copy;
        centerX += x;
        return true;
    }


    public boolean moveDown(Grid grid) throws SlickException {
        Block[] copy = new Block[structure.length];
        for(int i = 0; i< structure.length; i++){
            copy[i] = new Block(structure[i].getColorReference(), structure[i].getX(), structure[i].getY());
            copy[i].moveDown(1);
            if(!grid.canMoveHere(copy[i].getX(), copy[i].getY())){
                return false;
            }
        }
        structure = copy;
        centerY++;
        return  true;
    }
    public Block[] getStructure(){
        return structure;
    }
    public double getCenterX(){
        return centerX;
    }
    public double getCenterY(){
        return centerY;
    }
}
