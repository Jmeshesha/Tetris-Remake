package com.meshesha.tetrisremake.Drawables;

import com.meshesha.tetrisremake.Actions.BasicAction;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;

public class Button {
    int x;
    int y;
    int width;
    int height;
    Image background;
    Color bgColor;
    TextUI text;
    BasicAction hoveredAction;
    BasicAction clickedAction;
    BasicAction[] toggleActions;
    int hoveredDiff = 0;
    int maxHoveredDiff;
    int outlineLength = 1;
    TextUI copy;
    int toggleMode;

    boolean isHovered = false;
    public Button(int x, int y, int width, int height, int maxHoveredDiff){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHoveredDiff = maxHoveredDiff;

    }
    public void setHoveredAction(BasicAction action){
        hoveredAction = action;
    }
    public void setClickedAction(BasicAction... actions){
        toggleActions = actions;
        toggleMode = 0;
    }
    public void setBackground(Image background, Color filter){
        this.background = background;
        bgColor = filter;
    }
    public void draw(Graphics g){

        if(text != null){
            if(hoveredDiff != 0) copy.draw(x + width/2-text.getWidth()*0.5f, y + height/2- text.getHeight());
            text.draw(x + width/2-text.getWidth()*0.5f + hoveredDiff, y + height/2- text.getHeight() + hoveredDiff);

        }
    }
    public void drawBackground(Graphics g){
        if(background == null){
            g.setLineWidth(1);
            g.setColor(new Color(1, 1, 1, 0f));
            RoundedRectangle rect = new RoundedRectangle(x, y, width, height,  2);
            GradientFill fill = new GradientFill(0, 0, new Color(1f, 1f, 1f, 0.125f), width, height, new Color(1f, 1f, 1f, 0.125f), true);
            g.fill(rect, fill);
            g.draw(rect);
            g.setLineWidth(1.5f);
            g.setColor(new Color(1f, 1f, 1f));
            TextUI copy = new TextUI(text.size, text.text, text.fontName, Color.black);
            copy.draw(x + width/2-0.3f*text.size*text.getText().length(), y + height/2- 0.25f*text.getSize() );


        }else{
            background.draw(x, y, width, height, bgColor);
        }


    }
    public void drawHovered(){

        copy.draw(x + width/2-0.3f*text.size*text.getText().length(), y + height/2- 0.25f*text.getSize());

    }


    public void playAction(BasicAction action, int mouseX, int mouseY){
        if(mouseX > x && mouseX < width + x && mouseY > y && mouseY < height +y) {
            action.play();
        }
    }

    public void setStartLocation(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void setText(String string, int size, String font, Color color){
        text = new TextUI(size, string, font, color);
        copy = new TextUI(size, string, font, Color.black);
    }
    public TextUI getCopy(){
        return copy;
    }
    public void playHovered(int mouseX, int mouseY){
        if(mouseX > x && mouseX < width + x && mouseY > y && mouseY < height +y) {
            if(hoveredAction != null){
                hoveredAction.play();
            }
            hoveredDiff = -maxHoveredDiff;
            text.setColor(Color.cyan);
            isHovered = true;
        }else{
            hoveredDiff = 0;
            text.setColor(Color.white);
            isHovered = false;
        }

    }
    public void playClicked(){
        if(isHovered && toggleActions != null){
            toggleActions[toggleMode].play();
            if(toggleMode  >= toggleActions.length -1) {
                toggleMode = 0;
            }else{
                toggleMode++;
            }
        }
    }
    public boolean isHovered(){
        return isHovered;
    }
    public BasicAction getHoveredAction(){
        return hoveredAction;
    }
    public BasicAction getClickedAction(){
        return clickedAction;
    }
}
