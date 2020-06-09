package Drawables.Pieces;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class L_Piece extends BasicPiece {

    public L_Piece() throws SlickException {
        super(4);



    }



    @Override
    public void init(boolean isEndLoc) throws SlickException {
        Image i = new Image(getCurrentColor(isEndLoc));
        i.setImageColor(0, 1f, 0);
        structure[0] = new Block(i, 5, -2);
        structure[1] = new Block(i, 5, -1);
        structure[2] = new Block(i, 5, 0);
        structure[3] = new Block(i, 6, 0);
        centerX = structure[1].getX();
        centerY = structure[1].getY();
    }
}
