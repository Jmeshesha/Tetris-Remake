package com.meshesha.tetrisremake.Drawables;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class TextBox {
    String text;
    String fontName;
    int textSize;
    int cursorPos;
    TextUI textDraw;
    int height;
    Color textColor;
    int maxChars;
    boolean isTyping;
    float x;
    float y;
    public TextBox(float x, float y, String defaultText, String fontName, int maxChars, int textSize, Color textColor){
        if(defaultText.length() > maxChars){
            text = defaultText.substring(0, maxChars);
        }else{
            text = defaultText;
        }
        this.x = x;
        this.y = y;

        isTyping = false;
        this.fontName = fontName;
        this.textSize = textSize;
        textDraw = new TextUI(textSize, defaultText, fontName, textColor);
        this.maxChars = maxChars;
        height = textDraw.getHeight();


    }
    public void draw(Graphics g){
        textDraw.draw(x, y);
        if(isTyping){
            g.drawLine(x + textDraw.getFont().getWidth(text.substring(0, cursorPos)), y, x + textDraw.getFont().getWidth(text.substring(0, cursorPos)), y+ height);
        }
    }
    public void update(){
        textDraw.setText(text);
        if(isTyping){
            cursorPos = text.length();
        }
    }

    public void updateCursor(int mouseX, int mouseY){
        isTyping = mouseX >= x && mouseX <= x + textDraw.getWidth() * 1.2 && mouseY >= y && mouseY <= y + textDraw.getHeight();
    }
    public void updateCursor(int posDiff){
        if(!(cursorPos + posDiff > maxChars || cursorPos + posDiff < 0)) {
            cursorPos += posDiff;
        }
    }
    public void keyboardInput(int key, char c){
        if(isTyping){
            if(key == Input.KEY_BACK){
                int x = text.length() -1;
                if(x < 0){
                    x = 0;
                }
                text = text.substring(0, x);
                cursorPos--;

            }else if(text.length() < maxChars && (0x02 <= key  && key <=0x0E ) ||(0x10 <= key  && key <=0x1B) || (0x1E <= key  && key <=0x29) || (0x2B <= key  && key <=0x35) || key == 0x37 ||  key == 0x39 || (0x47 <= key  && key <=0x53)){
                text = text + c;
            }
        }

    }
    public boolean isTyping(){
        return isTyping;
    }
    public String getText(){
        return text;
    }

}
