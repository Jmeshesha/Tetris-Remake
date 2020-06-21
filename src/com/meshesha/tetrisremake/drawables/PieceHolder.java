package com.meshesha.tetrisremake.drawables;

import com.meshesha.tetrisremake.drawables.Pieces.BasicPiece;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;


public class PieceHolder {
    private float blockWidth;
    private float blockHeight;
    private BasicPiece piece;
    private TextUI text;
    private float x;
    private float y;
    private float width;
    private float height;
    private float screenWidth;
    private float screenHeight;

    public PieceHolder(float x, float y, float blockWidth, float blockHeight, String text, float screenWidth, float screenHeight) {
        this.x = x;
        this.y = y;
        this.width = blockWidth * 5;
        this.height = blockHeight * 5;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.text = new TextUI(30f, text, "Square", Color.white, screenWidth, screenHeight);

    }

    public PieceHolder(float x, float y, float width, float height, float blockWidth, float blockHeight, String text, float screenWidth, float screenHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.text = new TextUI(30, text, "Square", Color.white, screenWidth, screenHeight);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public void draw() {


        piece.draw(x - 3 * blockWidth, y + blockHeight / 2, blockWidth, blockHeight, screenWidth, screenHeight);


    }

    public void setPiece(BasicPiece piece) {
        this.piece = piece;
        if (piece.getStructure().length > 4) {
            blockWidth = (float) (width / 5f * Math.pow((4f / piece.getStructure().length), 0.2f));
            blockHeight = (float) (width / 5f * Math.pow(4f / piece.getStructure().length, 0.2f));
        }

    }

    public BasicPiece getPiece() {
        return piece;
    }

    public void drawBackground(Graphics g) {

        g.setLineWidth(3);
        RoundedRectangle rect = new RoundedRectangle(screenWidth / 1920f * x, screenHeight / 1080f * y, screenWidth / 1920f * width, screenHeight / 1080f * height, 5);
        GradientFill fill = new GradientFill(0, 0, new Color(0, 0, 0), width, height, new Color(0, 0, 0), true);
        g.fill(rect, fill);
        g.draw(rect);
        text.draw(x + width / 4, y - 40);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
