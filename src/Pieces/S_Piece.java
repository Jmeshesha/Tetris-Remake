package Pieces;

import org.newdawn.slick.SlickException;

public class S_Piece extends BasicPiece {

        public S_Piece(boolean isEndLoc) throws SlickException {
            super(4);
            structure[0] = new Block(getCurrentColor(isEndLoc), 6, -1);
            structure[1] = new Block(getCurrentColor(isEndLoc), 5, -1);
            structure[2] = new Block(getCurrentColor(isEndLoc), 5, 0);
            structure[3] = new Block(getCurrentColor(isEndLoc), 4, 0);
            centerX = structure[2].getX();
            centerY = structure[2].getY();

        }

    @Override
    public String getCurrentColor(boolean isEndLoc) throws SlickException{

        if(isEndLoc) return "images/EndLoc_Block.png";
        return  "images/green.png";
    }
}

