package com.meshesha.tetrisremake.FileIO;

import com.meshesha.tetrisremake.Drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.Drawables.Pieces.Block;
import com.google.gson.Gson;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JSONReader {

    public static String toJsonString(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String s = "";
        while (scanner.hasNext()){

            s += scanner.nextLine();
        }
        return s;
    }
    public BasicPiece toPiece(String jsonString) throws SlickException {
        Gson gson = new Gson();
         BlockJson[] block = gson.fromJson(jsonString, BlockJson[].class);
        return new BasicPiece(block.length) {
            @Override
            public void init(boolean isEndLoc) throws SlickException {
                int minX =4;
                int maxY = 0;
                minY = 0;
                for(BlockJson b : block ) {
                    if (b.x < minX) {
                        minX = b.x;
                    }
                    if (b.y > maxY) {
                        maxY = b.y;
                    }
                }
                for(int i = 0; i < block.length; i++){

                    Image image = new Image(getCurrentColor(isEndLoc));
                    image.setImageColor(block[i].r, block[i].g, block[i].b);
                    structure[i] = new Block(image, block[i].x+(4-minX), block[i].y-maxY);
                    if(minY > block[i].y-maxY){
                        minY = block[i].y-maxY;
                    }

                    if(block[i].isRotation){
                        centerX = block[i].x+4-minX;
                        centerY = block[i].y-maxY;
                    }
                }
            }
        };
    }
    public static BasicPiece toPiece(File file) throws SlickException, FileNotFoundException {

        Scanner scanner = new Scanner(file);
        String jsonString = "";
        while (scanner.hasNext()){

            jsonString += scanner.nextLine();
        }
        Gson gson = new Gson();
        BlockJson[] block = gson.fromJson(jsonString, BlockJson[].class);

        return new BasicPiece(block.length) {
            @Override
            public void init(boolean isEndLoc) throws SlickException {
                int minX =4;
                int maxY = 0;
                for(BlockJson b : block ) {
                    if (b.x < minX) {
                        minX = b.x;
                    }
                    if (b.y > maxY) {
                        maxY = b.y;
                    }
                }
                for(int i = 0; i < block.length; i++){

                    Image image = new Image(getCurrentColor(isEndLoc));
                    image.setImageColor(block[i].r, block[i].g, block[i].b);
                    structure[i] = new Block(image, block[i].x+(4-minX), block[i].y-maxY);

                    if(block[i].isRotation){
                        centerX = block[i].x+4-minX;
                        centerY = block[i].y-maxY;
                        canRotate = true;
                    }
                }
            }
        };
    }
}
