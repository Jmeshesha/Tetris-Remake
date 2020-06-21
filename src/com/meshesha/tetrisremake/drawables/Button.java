package com.meshesha.tetrisremake.drawables;

import com.meshesha.tetrisremake.actions.BasicAction;
import org.newdawn.slick.Color;

/*
 * Button Object used in states that run some action when clicked
 */
public class Button {
    private int x;
    private int y;
    private int width;
    private int height;

    private TextUI text;
    private BasicAction[] toggleActions;
    private int hoveredDiff = 0;
    private int maxHoveredDiff;
    private TextUI copy;
    private int toggleMode;
    private float screenWidth;
    private float screenHeight;

    private boolean isHovered = false;

    public Button(int x, int y, int width, int height, int maxHoveredDiff) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHoveredDiff = maxHoveredDiff;

    }

    public void setClickedAction(BasicAction... actions) {
        toggleActions = actions;
        toggleMode = 0;
    }

    public void draw() {

        if (text != null) {
            if (hoveredDiff != 0)
                copy.draw((x + width / 2f - 1920f / screenWidth * text.getWidth() * 0.5f), (y + height / 2f - 1080f / screenHeight * text.getHeight()));
            text.draw(x + width / 2f - 1920f / screenWidth * text.getWidth() * 0.5f + hoveredDiff, y + height / 2f - 1080f / screenHeight * text.getHeight() + hoveredDiff);

        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void setText(String string, int size, String font, Color color, float screenWidth, float screenHeight) {
        text = new TextUI(size, string, font, color, screenWidth, screenHeight);
        copy = new TextUI(size, string, font, Color.black, screenWidth, screenHeight);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }


    public TextUI getCopy() {
        return copy;
    }

    /*
     * Change button color and make it have a 3d effect when hovered
     */
    public void playHovered(int mouseX, int mouseY, float screenWidth, float screenHeight) {
        if (mouseX > x * screenWidth / 1920f && mouseX < screenWidth / 1920f * (width + x) && mouseY > screenHeight / 1080f * y && mouseY < screenHeight / 1080f * (height + y)) {

            hoveredDiff = -maxHoveredDiff;
            text.setColor(Color.cyan);
            isHovered = true;
        } else {
            hoveredDiff = 0;
            text.setColor(Color.white);
            isHovered = false;
        }

    }

    /*
     * Toggle between the actions set to happen when clicked
     */
    public void playClicked() {
        if (isHovered && toggleActions != null) {
            toggleActions[toggleMode].play();
            if (toggleMode >= toggleActions.length - 1) {
                toggleMode = 0;
            } else {
                toggleMode++;
            }
        }
    }

    public boolean isHovered() {
        return isHovered;
    }


}
