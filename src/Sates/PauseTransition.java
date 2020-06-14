package Sates;

import Main.Main;
import Sates.TetrisState;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public class PauseTransition implements Transition {
    TetrisState firstState;
    TetrisState secondState;
    Image firstBackground;
    Image secondBackground;
    int deltaAdder;
    float interval;
    public PauseTransition(float duration){
        interval = 1/duration;
    }
    @Override
    public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
        deltaAdder += delta;
        if(deltaAdder > 1){
            secondBackground.setAlpha(secondBackground.getAlpha() + interval);
            deltaAdder = 0;
        }
        secondState.setBackground(secondBackground);


    }

    @Override
    public void preRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {

    }

    @Override
    public void postRender(StateBasedGame game, GameContainer container, Graphics g) throws SlickException {
        secondBackground.draw(0, 0, 1920, 1080);

    }

    @Override
    public boolean isComplete() {
        return secondBackground.getAlpha() >= 1f;
    }

    @Override
    public void init(GameState firstState, GameState secondState) {
        this.firstState = (TetrisState) firstState;
        this.secondState = (TetrisState) secondState;

        deltaAdder = 0;

        try {
            firstBackground = new Image(((TetrisState) firstState).getBackground().getResourceReference());
            secondBackground = new Image(((TetrisState) secondState).getBackground().getResourceReference());
        } catch (SlickException e) {
            e.printStackTrace();
        }
        secondBackground.setAlpha(0);
        ((TetrisState) secondState).setBackground(secondBackground);
    }
}
