package Game.Pieces;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public class L_Piece extends BasicPiece {

    public L_Piece() throws SlickException {
        super(4);
        structure[0] = new Block(getCurrentColor(), 5, -2);
        structure[1] = new Block(getCurrentColor(), 5, -1);
        structure[2] = new Block(getCurrentColor(), 5, 0);
        structure[3] = new Block(getCurrentColor(), 6, 0);
        centerX = structure[1].getX();
        centerY = structure[1].getY();

    }

    @Override
    public String getCurrentColor() throws SlickException{


        return  "images/green.png";
    }
}
