package Pieces;

import org.newdawn.slick.SlickException;

public class L_Piece extends BasicPiece {

    public L_Piece() throws SlickException {
        super(4);



    }

    @Override
    public String getCurrentColor(boolean isEndLoc) throws SlickException{

        if(isEndLoc) return "images/EndLoc_Block.png";
        return  "images/green.png";
    }

    @Override
    public void init(boolean isEndLoc) throws SlickException {
        structure[0] = new Block(getCurrentColor(isEndLoc), 5, -2);
        structure[1] = new Block(getCurrentColor(isEndLoc), 5, -1);
        structure[2] = new Block(getCurrentColor(isEndLoc), 5, 0);
        structure[3] = new Block(getCurrentColor(isEndLoc), 6, 0);
        centerX = structure[1].getX();
        centerY = structure[1].getY();
    }
}
