package UI;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TextUI  {

    UnicodeFont font;
    String text;
    Color color;
    float size;
    String fontName;
    public TextUI(float size, String text, String fontName, Color color){
        this.size = size;
        this.text = text;
        this.color = color;
        this.fontName = fontName;
        initFont();
    }
    public void initFont(){
        try{
            Font UIFont1 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/fonts/" + fontName +".ttf"));
            UIFont1 = UIFont1.deriveFont(Font.PLAIN, size); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 16.f is the size of your font

            font = new UnicodeFont(UIFont1);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
            font.addAsciiGlyphs();
            font.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setText(String text){
        this.text = text;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void setFont(String fontName) {
        this.fontName = fontName;
        initFont();
    }
    public float getSize(){
        return size;
    }

    public void draw(float x, float y) {
        font.drawString(x, y, text, color);

    }
}
