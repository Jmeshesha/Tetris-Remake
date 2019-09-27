package Game.Pieces;

import org.newdawn.slick.SlickException;

public class J_Piece extends BasicPiece {

    public J_Piece() throws SlickException {
        super(4);
        structure[0] = new Block(getCurrentColor(), 5, -2);
        structure[1] = new Block(getCurrentColor(), 5, -1);
        structure[2] = new Block(getCurrentColor(), 5, 0);
        structure[3] = new Block(getCurrentColor(), 4, 0);
        centerX = structure[1].getX();
        centerY = structure[1].getY();

    }

    @Override
    public String getCurrentColor() throws SlickException{


        return  "images/green.png";
    }
}
