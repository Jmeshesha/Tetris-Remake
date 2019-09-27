package Game.Pieces;

import org.newdawn.slick.SlickException;

public class I_Piece extends BasicPiece {

    public I_Piece() throws SlickException {
        super(4);
        structure[0] = new Block(getCurrentColor(), 5, -2);
        structure[1] = new Block(getCurrentColor(), 5, -1);
        structure[2] = new Block(getCurrentColor(), 5, 0);
        structure[3] = new Block(getCurrentColor(), 5, 1);
        centerX = structure[2].getX();
        centerY = structure[2].getY();
    }

    @Override
    public String getCurrentColor() throws SlickException{


        return  "images/green.png";
    }
    @Override
    public void rotate(){

        for(int i = 0; i< structure.length; i++){
            structure[i].rotate(centerX, centerY, rotationAngle);
        }
        calculateEndLocation();
        rotationAngle *= -1;
    }
}
