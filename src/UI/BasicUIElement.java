package UI;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class BasicUIElement {
    protected float x = 0;
    protected float y = 0;
    protected float width = 0;
    protected float height = 0;
    protected Image background;
    public BasicUIElement(float  x, float y, float width, float height) throws SlickException {
        this(x, y, width, height, 0, 0, 0);

    }
    public BasicUIElement(float x, float y, float width, float height, int r, int g, int b) throws SlickException {
        this.background = new Image((int)width, (int)height);
        background.setImageColor(r, g, b);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public BasicUIElement(float x, float y, float width, float
            height, Image background){
        this.background = background;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawBackground(){

    }
    abstract void draw();



}
