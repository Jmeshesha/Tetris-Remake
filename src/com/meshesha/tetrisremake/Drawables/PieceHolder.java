package com.meshesha.tetrisremake.Drawables;

import com.meshesha.tetrisremake.Drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.Main.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;


public class PieceHolder {
    float blockWidth;
    float blockHeight;
    private BasicPiece piece;
    TextUI text;
    TextUI text2;
    float x;
    float y;
    float width;
    float height;
    public PieceHolder(float x, float y, float blockWidth, float blockHeight, String text)  {
        this.x = x;
        this.y = y;
        this.width = blockWidth*5;
        this.height = blockHeight*5;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.text = new TextUI(30f , text, "Square", Color.white);

    }
    public PieceHolder(float x, float y, float width, float height, float blockWidth, float blockHeight, String text)  {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.text = new TextUI( Main.getScreenWidth() * 30f/1920f, text, "Square", Color.white);
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }


    public void draw(Graphics g) {


        piece.draw(x - 3 * blockWidth, y+blockHeight/2, blockWidth, blockHeight);


        //piece.draw(x, y,  blockSize);

    }
    public void draw(float x, float y, float width, float height, float blockWidth, float blockHeight){

    }
    public void setPiece(BasicPiece piece){
        this.piece = piece;
        if(piece.getStructure().length > 4){
            blockWidth = (float)(width/5f* Math.pow((4f/piece.getStructure().length), 0.2f));
            blockHeight = (float)(width/5f *Math.pow(4f/piece.getStructure().length, 0.2f));
        }

    }
    public BasicPiece getPiece(){
        return piece;
    }
    public void drawBackground(Graphics g) {

        g.setLineWidth(3);
        RoundedRectangle rect = new RoundedRectangle(x, y, width, height, 5);
        GradientFill fill = new GradientFill(0, 0, new Color(0, 0, 0), width, height, new Color(0, 0, 0), true);
        g.fill(rect, fill);
        g.draw(rect);
        text.draw(x+width/4, y-(Main.getScreenHeight()/1080f)*40);
    }
    public float  getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }

}
