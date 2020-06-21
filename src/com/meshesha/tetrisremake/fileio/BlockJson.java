package com.meshesha.tetrisremake.fileio;

/*
 * Structure for GSON to save .json files and convert them Back into objects
 */
public class BlockJson {
    int x;
    int y;
    float r;
    float g;
    float b;
    boolean isRotation;

    public BlockJson(int x, int y, float r, float g, float b, boolean isRotation) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.g = g;
        this.b = b;
        this.isRotation = isRotation;
    }
}
