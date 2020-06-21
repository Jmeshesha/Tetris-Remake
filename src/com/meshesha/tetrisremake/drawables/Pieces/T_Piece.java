package com.meshesha.tetrisremake.drawables.Pieces;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class T_Piece extends BasicPiece {

    public T_Piece() throws SlickException {
        super(4);


    }


    @Override
    public void init(boolean isEndLoc) throws SlickException {
        Image i = new Image(getCurrentColor(isEndLoc));
        i.setImageColor(1f, 0.5f, 0f);
        structure[0] = new Block(i, 5, -2);
        structure[1] = new Block(i, 5, -1);
        structure[2] = new Block(i, 6, -1);
        structure[3] = new Block(i, 5, 0);
        centerX = structure[1].getX();
        centerY = structure[1].getY();
        canRotate = true;
    }
}
