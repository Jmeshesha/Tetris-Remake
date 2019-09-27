package Game;

import Game.Pieces.*;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Random;

public class Physics {

    BasicPiece currentPiece;
    int fallingTime;
    int timeSinceUpdate;


    public Physics(){

    }
    public void updatePhyiscs(Grid grid, int delta){
        timeSinceUpdate += delta;
        if(timeSinceUpdate >= fallingTime ){

        }
    }
    public void randomizePiece() throws SlickException {
        Random rand = new Random();
        int pieceNum = rand.nextInt(3);
        switch(pieceNum){
            case 0:
                currentPiece = new I_Piece();
                break;
            case 1:
                currentPiece = new J_Piece();
                break;
            case 2:
                currentPiece = new L_Piece();
                break;
        }
    }


}
