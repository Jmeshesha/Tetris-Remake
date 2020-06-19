package com.meshesha.tetrisremake.Drawables.Pieces;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class O_Piece extends BasicPiece {

    public O_Piece() throws SlickException {
        super(4);

        canRotate = false;


    }


    @Override
    public void init(boolean isEndLoc) throws SlickException {
        Image i = new Image(getCurrentColor(isEndLoc));
        i.setImageColor(0f, 1f,  1f);
        structure[0] = new Block(i, 5, -1);
        structure[1] = new Block(i, 6, -1);
        structure[2] = new Block(i, 5, 0);
        structure[3] = new Block(i, 6, 0);
    }
}