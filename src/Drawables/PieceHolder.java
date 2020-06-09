package Drawables;

import Main.Main;
import Drawables.Pieces.BasicPiece;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;


public class PieceHolder {
    float blockWidth;
    float blockHeight;
    public BasicPiece piece;
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
        this.text = new TextUI(25 , text, "8-Bit40", Color.white);

    }
    public PieceHolder(float x, float y, float width, float height, float blockWidth, float blockHeight, String text)  {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.text = new TextUI( Main.getScreenWidth() * 25/1920f, text, "8-Bit40", Color.white);
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
    public void drawBackground(Graphics g) {
        g.setLineWidth(3);
        RoundedRectangle rect = new RoundedRectangle(x, y, width, height, 5);
        GradientFill fill = new GradientFill(0, 0, new Color(0, 0, 0), width, height, new Color(0, 0, 0), true);
        g.fill(rect, fill);
        g.draw(rect);
        text.draw(x+width/4, y-(Main.getScreenHeight()/1080f)*40);
    }
}
