package com.meshesha.tetrisremake.Drawables;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.RoundedRectangle;


public class Slider {
    int x;
    int y;
    float width;
    float height;
    float minValue;
    float maxValue;
    float currValue;
    float midx;
    float midy;
    Color circleColor;
    int circleRadius;
    Circle selection;
    public Slider(int x, int y, float width, float height,  float minValue, float maxValue, float startingValue){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.midx = x + width/2f;
        this.midy = y + height/2f;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.circleRadius = (int)(height);
        circleColor = Color.white;
        currValue = startingValue;
    }
    public void updateSlider(int mousex, int mousey, boolean isClicked){
        if(mousex >= x && mousex <= x+width &&  mousey >= midy -(circleRadius+5)  && mousey <= midy+circleRadius+5 && isClicked){
            currValue = ((mousex - x)/width)*(maxValue-minValue)+ minValue;
            circleColor = Color.cyan;
            circleRadius = (int)(height*5/4);

        }else{
            circleColor = Color.white;
            circleRadius = (int)(height);
        }
    }
    public void setValue(float value){
        currValue = value;
    }
    public float getValue(){
        return currValue;
    }
    public void draw(Graphics g){
        selection = new Circle(x + width*(currValue-minValue)/(maxValue-minValue), y + height/2f, circleRadius);

        RoundedRectangle rect = new RoundedRectangle(x , y , width, height, height);
        g.fill(rect, new GradientFill(0, 0, Color.black, 1920, 1080, Color.black));
        g.draw(rect);
        g.fill(selection, new GradientFill(0,0, circleColor, 1920, 1080,  circleColor));

        g.setColor(circleColor);
        g.draw(selection);
        g.setColor(Color.white);



    }
}
