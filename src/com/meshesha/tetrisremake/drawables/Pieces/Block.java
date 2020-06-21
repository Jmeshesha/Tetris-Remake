package com.meshesha.tetrisremake.drawables.Pieces;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*
 * Block object to be used by grid and pieces as an array
 */
public class Block {
    private int x;
    private int y;
    private Color currColor;


    private Image currentColor;


    public Block(Image color, int x, int y) {

        currentColor = color;
        this.x = x;
        this.y = y;

    }

    //uses rotation matrix
    public void rotate(double centerX, double centerY) {
        double xFromCenter = x - centerX;
        double yFromCenter = y - centerY;
        x = (int) (centerX - yFromCenter);
        y = (int) (centerY + xFromCenter);

    }


    public void setImage(Image currentColor) {
        this.currentColor = currentColor;
    }

    public Image getImage() {
        return currentColor;
    }

    public void setColor(Color color) {
        currColor = color;
        currentColor.setImageColor(color.r, color.g, color.b);
    }

    public Color getColor() {
        return currColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /*
     * Block Movement Methods
     */
    public void moveDown(int y) {
        this.y += y;
    }

    public void moveSide(int x) {
        this.x += x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /*
     * Draws block
     */
    public void draw(float startX, float startY, float width, float height, float screenWidth, float screenHeight) {
        if (y >= 0) {
            currentColor.draw(screenWidth * (startX + width * x) / 1920f, screenHeight * (startY + height * y) / 1080f, screenWidth * width / 1920, screenHeight * height / 1080, currColor);

        }
    }

}

