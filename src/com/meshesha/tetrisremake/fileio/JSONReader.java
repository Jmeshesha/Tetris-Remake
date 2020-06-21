package com.meshesha.tetrisremake.fileio;

import com.meshesha.tetrisremake.drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.drawables.Pieces.Block;
import com.google.gson.Gson;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Methods to read Json files and convert them into Block Json Objects
 * */
public class JSONReader {

    /*
     * Converts a json file into a json string
     */
    public static String toJsonString(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder s = new StringBuilder();
        while (scanner.hasNext()) {

            s.append(scanner.nextLine());
        }
        return s.toString();
    }

    /*
     * Converts a json file into BasicPiece object
     */
    public static BasicPiece toPiece(File file) throws SlickException, FileNotFoundException {

        Scanner scanner = new Scanner(file);
        StringBuilder jsonString = new StringBuilder();
        while (scanner.hasNext()) {

            jsonString.append(scanner.nextLine());
        }
        Gson gson = new Gson();
        BlockJson[] block = gson.fromJson(jsonString.toString(), BlockJson[].class);

        return new BasicPiece(block.length) {
            @Override
            public void init(boolean isEndLoc) throws SlickException {
                int minX = 4;
                int maxY = 0;
                for (BlockJson b : block) {
                    if (b.x < minX) {
                        minX = b.x;
                    }
                    if (b.y > maxY) {
                        maxY = b.y;
                    }
                }
                for (int i = 0; i < block.length; i++) {

                    Image image = new Image(getCurrentColor(isEndLoc));
                    image.setImageColor(block[i].r, block[i].g, block[i].b);
                    structure[i] = new Block(image, block[i].x + (4 - minX), block[i].y - maxY);

                    if (block[i].isRotation) {
                        centerX = block[i].x + 4 - minX;
                        centerY = block[i].y - maxY;
                        canRotate = true;
                    }
                }
            }
        };
    }
}
