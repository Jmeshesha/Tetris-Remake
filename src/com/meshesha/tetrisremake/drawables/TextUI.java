package com.meshesha.tetrisremake.drawables;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;

/*
 * Drawable String that includes fonts and colors
 */
public class TextUI {


    private UnicodeFont font;
    private String text;
    private Color color;
    private float size;
    private String fontName;
    private float x;
    private float y;
    private float screenWidth;
    private float screenHeight;

    public TextUI(float size, String text, String fontName, Color color, float screenWidth, float screenHeight) {
        this.size = size;
        this.text = text;
        this.color = color;
        this.fontName = fontName;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        initFont();
    }

    private void initFont() {
        try {
            Font UIFont1 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/" + fontName + ".ttf"));
            UIFont1 = UIFont1.deriveFont(Font.PLAIN, (screenWidth / 1920f) * size);
            font = new UnicodeFont(UIFont1);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(java.awt.Color.white));
            font.addAsciiGlyphs();
            font.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setFont(String fontName) {
        this.fontName = fontName;
        initFont();
    }

    public float getSize() {
        return size;
    }

    public String getText() {
        return text;
    }

    public void draw(float x, float y) {
        font.drawString((screenWidth / 1920f) * x, (screenHeight / 1080f) * y, text, color);
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return font.getWidth(text);
    }

    public int getHeight() {
        return font.getHeight(text);
    }

    public UnicodeFont getFont() {
        return font;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
