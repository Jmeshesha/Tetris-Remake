package com.meshesha.tetrisremake.FileIO;

public class BlockJson {

    int x;
    int y;
    float r;
    float g;
    float b;
    boolean isRotation;
    public BlockJson(int x, int y, float r, float g, float b, boolean isRotation){
        this.x =x;
        this.y =y;
        this.r = r;
        this.g = g;
        this.b = b;
        this.isRotation = isRotation;
    }
}
