package com.meshesha.tetrisremake.Drawables;

import org.newdawn.slick.Image;

public class ImagePopUp {
    Image[] images;
    Image image;
    int duration;
    int currTime;
    float endAlpha;
    float currAlpha;
    float interval;
    float startAlpha;
    float endError;

    boolean isDone;
    public ImagePopUp(Image image, int duration, float endAlpha, float endError){
        this.image = image;
        this.duration = duration;
        this.endAlpha = 0f;
        this.startAlpha = endAlpha;
        this.currAlpha = startAlpha;
        this.endError = endError;
        isDone = true;

    }
    public void initSingle(){
            isDone = false;
            currTime = 0;
            currAlpha = endAlpha;
            endAlpha = startAlpha;
            startAlpha = currAlpha;
            //currAlpha = image.getAlpha();
            interval = (endAlpha-startAlpha)/(duration);


    }
    public void draw(int x, int y, int width, int height){
        image.draw(x, y, width, height);
    }
    public void play(int delta){
        currTime += delta;
        if(!isDone) {


            if (currTime >= 1  && Math.abs(endAlpha - currAlpha) > endError) {
                //System.out.println(currAlpha + " " + endAlpha);
                currAlpha += interval;
                image.setAlpha(currAlpha);


            }else if(Math.abs(endAlpha - currAlpha) <= endError){
                //whenDone();
                isDone = true;
            }
            currTime = 0;
        }
    }
    public void whenDone(){
        currAlpha = endAlpha;
        endAlpha = startAlpha;
        startAlpha = currAlpha;
    }
    public boolean isDone(){
        return isDone;
    }
    public float getCurrAlpha(){
        return  currAlpha;
    }
}
