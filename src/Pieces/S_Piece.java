package Pieces;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class S_Piece extends BasicPiece {

        public S_Piece() throws SlickException {
            super(4);



        }



    @Override
    public void init(boolean isEndLoc) throws SlickException {
        Image i = new Image(getCurrentColor(isEndLoc));
        i.setImageColor(0, 0.5f, 1f);
        structure[0] = new Block(i, 6, -1);
        structure[1] = new Block(i, 5, -1);
        structure[2] = new Block(i, 5, 0);
        structure[3] = new Block(i, 4, 0);
        centerX = structure[2].getX();
        centerY = structure[2].getY();
    }
}

