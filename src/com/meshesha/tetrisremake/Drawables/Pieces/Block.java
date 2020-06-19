package com.meshesha.tetrisremake.Drawables.Pieces;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Block {
    private int x;
    private int y;
    private String colorString;
    private Color currColor;



    private Image currentColor;



    public Block(Image color, int x, int y) throws SlickException {

        currentColor = color;
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


    public String getColorReference(){
        return currentColor.getResourceReference();
    }

    public void setImage(Image currentColor) {
        this.currentColor = currentColor;
    }
    public Image getImage(){
        return currentColor;
    }
    public void setColor(Color color){
        currColor = color;
        currentColor.setImageColor(color.r, color.g, color.b);
    }
    public Color getColor(){
        return currColor;
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
    public void draw(float startX, float startY, float width, float height)  {
        if(y >= 0) {
            currentColor.draw(startX + width * x, startY + height * y, width, height, currColor);

        }
    }

}

