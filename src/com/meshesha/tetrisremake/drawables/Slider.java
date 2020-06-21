package com.meshesha.tetrisremake.drawables;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.RoundedRectangle;


public class Slider {
    private int x;
    private int y;
    private float width;
    private float height;
    private float minValue;
    private float maxValue;
    private float currValue;
    private float midy;
    private Color circleColor;
    private int circleRadius;

    public Slider(int x, int y, float width, float height, float minValue, float maxValue, float startingValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.midy = y + height / 2f;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.circleRadius = (int) (height);
        circleColor = Color.white;
        currValue = startingValue;
    }

    public void updateSlider(int mousex, int mousey, boolean isClicked, float screenWidth, float screenHeight) {
        if (mousex >= x * screenWidth / 1920f && mousex <= (x + width) * screenWidth / 1920f && mousey >= (midy - (circleRadius + 5)) * screenHeight / 1080f && mousey <= (midy + circleRadius + 5) * screenHeight / 1080f && isClicked) {
            currValue = ((1920f / screenWidth * mousex - x) / width) * (maxValue - minValue) + minValue;
            circleColor = Color.cyan;
            circleRadius = (int) (height * 5 / 4);

        } else {
            circleColor = Color.white;
            circleRadius = (int) (height);
        }
    }

    public void setValue(float value) {
        currValue = value;
    }

    public float getValue() {
        return currValue;
    }

    public void draw(Graphics g, float screenWidth, float screenHeight) {
        Circle selection = new Circle(screenWidth / 1920f * (x + width * (currValue - minValue) / (maxValue - minValue)), (y + height / 2f) * screenHeight / 1080f, screenWidth / 1920f * circleRadius);

        RoundedRectangle rect = new RoundedRectangle(screenWidth / 1920f * x, y * screenHeight / 1080f, screenWidth / 1920f * width, height * screenHeight / 1080f, height * screenHeight / 1080f);
        g.fill(rect, new GradientFill(0, 0, Color.black, screenWidth, screenHeight, Color.black));
        g.draw(rect);
        g.fill(selection, new GradientFill(0, 0, circleColor, screenWidth, screenHeight, circleColor));

        g.setColor(circleColor);
        g.draw(selection);
        g.setColor(Color.white);


    }
}
