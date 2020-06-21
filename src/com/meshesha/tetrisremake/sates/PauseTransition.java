package com.meshesha.tetrisremake.sates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;


/*
 * This Transition fades into the next state by gradually increasing the alpha of the next state's background
 */
public class PauseTransition implements Transition {
    private Image secondBackground;
    private float interval;

    PauseTransition(float duration) {
        interval = 1 / duration;
    }

    @Override
    public void update(StateBasedGame game, GameContainer container, int delta) {
        secondBackground.setAlpha(secondBackground.getAlpha() + interval);
    }

    @Override
    public void preRender(StateBasedGame game, GameContainer container, Graphics g) {

    }

    @Override
    public void postRender(StateBasedGame game, GameContainer container, Graphics g) {
        secondBackground.draw(0, 0, container.getWidth(), container.getHeight());

    }

    @Override
    public boolean isComplete() {
        return secondBackground.getAlpha() >= 1f;
    }

    @Override
    public void init(GameState firstState, GameState secondState) {



        try {
            secondBackground = new Image(((TetrisState) secondState).getBackground().getResourceReference());
        } catch (SlickException e) {
            e.printStackTrace();
        }
        secondBackground.setAlpha(0);

    }
}
