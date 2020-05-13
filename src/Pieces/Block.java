package Pieces;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class Block {
    private int x;
    private int y;
    private Color currColor;
    private String colorString;




    private Image currentColor;


    public Block(String color, int x, int y) throws SlickException{
        colorString = color;
        Image currentColor = new Image(color);
        this.currentColor = currentColor;
        this.x = x;
        this.y = y;

    }

    //uses rotation matrix
    public void rotate(double centerX, double centerY){
        double xFromCenter = x - centerX;
        double yFromCenter = y - centerY;
        x = (int)(centerX  - yFromCenter);
        y = (int)(centerY + xFromCenter);

    }


    public Image getCurrentColor() {
        return currentColor;
    }
    public String getColorReference(){
        return currentColor.getResourceReference();
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
    public void draw(float startX, float startY, float size){
        if(y >= 0) {
            currentColor.draw(startX + size * x, startY + size * y, size, size, currColor);

        }
    }


}

