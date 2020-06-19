package com.meshesha.tetrisremake.Sates;

import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;

public abstract class TetrisState extends BasicGameState {
    Image background;
    public Image getBackground(){
        return background;
    }
    public void setBackground(Image background){
        this.background = background;
    }

}
