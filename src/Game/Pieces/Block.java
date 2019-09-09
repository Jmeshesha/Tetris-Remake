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


    public Block(String color) throws SlickException{
        Image currentColor = new Image(color);
        this.currentColor = currentColor;
        x = 5;
        y = 0;
        size = 50;

    }
    public void rotate(double centerX, double centerY){
        double angle = 90;
        double xFromCenter = x - centerX;
        double yFromCenter = y - centerY;
        double distanceFromCenter = Math.sqrt(Math.pow(xFromCenter, 2) + Math.pow(yFromCenter, 2) );
        double UnitAngleOriginal = Math.asin(xFromCenter/distanceFromCenter);
        if(x<0){
            UnitAngleOriginal += Math.PI;
        }
        double UnitAngleNew = UnitAngleOriginal + Math.toRadians(angle);
        double newX = centerX + distanceFromCenter * Math.sin(UnitAngleNew);
        double newY = centerY + distanceFromCenter * Math.cos(UnitAngleNew);
        x = (int)newX;
        y = (int)newY;
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
    public void moveDown(int x){
        this.y-=y;
    }
    public void moveSide(int y){
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
        currentColor.draw(startX+size*x, startY + size*y, size,  size, currColor );
    }

}

