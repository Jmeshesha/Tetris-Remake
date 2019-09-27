package Game.Pieces;

import Exceptions.ColorException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class Block {
    private int x;
    private int y;
    private int size;
    private Color currColor;



    private Image currentColor;


    public Block(String color, int x, int y) throws SlickException{
        Image currentColor = new Image(color);
        this.currentColor = currentColor;
        this.x = x;
        this.y = y;
        size = 50;

    }
    //uses rotation matrix
    public void rotate(double centerX, double centerY, int angle){
        double xFromCenter = x - centerX;
        double yFromCenter = y - centerY;
        double rad = Math.toRadians(angle);
        x = (int)Math.round((centerX +(xFromCenter * Math.cos(rad) - yFromCenter * Math.sin(rad))));
        y = (int)(centerY + (xFromCenter * Math.sin(rad) + yFromCenter * Math.cos(rad)));

    }


    public Image getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Image currentColor) {
        this.currentColor = currentColor;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void moveDown(int y){
        this.y+=y;
    }
    public void moveSide(int x){
        this.x +=x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getSize(){
        return size;
    }
    public void draw(float startX, float startY){
        if(y >= 0)
        currentColor.draw(startX+size*x, startY + size*y, size,  size, currColor );
    }

}

