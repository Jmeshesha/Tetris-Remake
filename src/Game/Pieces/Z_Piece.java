package Game.Pieces;

import org.newdawn.slick.SlickException;

public class Z_Piece extends BasicPiece {

    public Z_Piece() throws SlickException {
        super(4);
        structure[0] = new Block(getCurrentColor(), 4, -1);
        structure[1] = new Block(getCurrentColor(), 5, -1);
        structure[2] = new Block(getCurrentColor(), 5, 0);
        structure[3] = new Block(getCurrentColor(), 6, 0);
        centerX = structure[2].getX();
        centerY = structure[2].getY();
    }

    @Override
    public String getCurrentColor() throws SlickException{


        return  "images/green.png";
    }
}
