package com.meshesha.tetrisremake.sates;

import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;

/*
 * Custom State Class to allow background to accessed in Pause Transition
 */
public abstract class TetrisState extends BasicGameState {
    Image background;

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

}
