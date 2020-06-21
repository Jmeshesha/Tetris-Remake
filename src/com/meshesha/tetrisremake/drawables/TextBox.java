package com.meshesha.tetrisremake.drawables;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class TextBox {
    private String text;
    private TextUI textDraw;
    private int height;
    private int maxChars;
    private boolean isTyping;
    private float x;
    private float y;
    private float screenWidth;
    private float screenHeight;

    public TextBox(float x, float y, String defaultText, String fontName, int maxChars, int textSize, Color textColor, float screenWidth, float screenHeight) {
        if (defaultText.length() > maxChars) {
            text = defaultText.substring(0, maxChars);
        } else {
            text = defaultText;
        }
        this.x = x;
        this.y = y;

        isTyping = false;
        textDraw = new TextUI(textSize, defaultText, fontName, textColor, screenWidth, screenHeight);
        this.maxChars = maxChars;
        height = textDraw.getHeight();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;


    }

    public void draw(Graphics g) {
        textDraw.draw(x, y);
        if (isTyping) {
            g.drawLine((screenWidth / 1920f) * x + textDraw.getWidth(), (screenHeight / 1080f) * y, (screenWidth / 1920f) * x + textDraw.getWidth(), (screenHeight / 1080f) * y + height);
        }
    }


    public void updateCursor(int mouseX, int mouseY) {
        isTyping = mouseX >= (screenWidth / 1920f) * x && mouseX <= (screenWidth / 1920f) * (x + 75) + textDraw.getWidth() && mouseY >= (screenHeight / 1080f) * y && mouseY <= (screenHeight / 1080f) * y + height;
    }

    public void keyboardInput(int key, char c) {
        if (isTyping) {
            if (key == Input.KEY_BACK) {
                int x = text.length() - 1;
                if (x < 0) {
                    x = 0;
                }
                text = text.substring(0, x);
                textDraw.setText(text);

                // Makes sure text included is by font and doesn't try to include something like the alt or control key into text
            } else if (text.length() < maxChars && (0x02 <= key && key <= 0x0E) || (0x10 <= key && key <= 0x1B) || (0x1E <= key && key <= 0x29) || (0x2B <= key && key <= 0x35) || key == 0x37 || key == 0x39 || (0x47 <= key && key <= 0x53)) {
                text = text + c;
                textDraw.setText(text);
            }
        }

    }

    public boolean isTyping() {
        return isTyping;
    }

    public String getText() {
        return text;
    }

}
