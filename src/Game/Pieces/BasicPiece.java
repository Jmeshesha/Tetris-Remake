package Game.Pieces;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public abstract class BasicPiece {
    protected Block[] structure;
    protected double centerX;
    protected double centerY;
    private Block[] endLocation;
    public BasicPiece()throws SlickException{

        structure = new Block[4];
        for(int i = 0; i< 4; i++ ){
            structure[i] = new Block(getCurrentColor());
        }
        calculateEndLocation();
    }
    public abstract String getCurrentColor() throws SlickException ;
    public void calculateEndLocation(){
        //TODO: Write code calculating the end location

    }
    public void draw(float startX, float startY){
        for(int i = 0; i<structure.length; i++){
            endLocation[i].draw(startX, startY);
        }
    }
    public void rotate(){

        for(int i = 0; i< structure.length; i++){
            structure[i].rotate(centerX, centerY);
        }
        calculateEndLocation();
    }
    public void moveSideways(int x){
        centerX += x;
        calculateEndLocation();
    }
    public void moveDown(){
        centerX--;
    }
    public void moveToEndLocation(){
        for(int i = 0; i < structure.length; i++){
            structure[i].setX(endLocation[i].getX());
            structure[i].setY(endLocation[i].getY());
        }
        calculateEndLocation();
    }
    public Block[] getStructure(){
        return structure;
    }
}
