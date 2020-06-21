package com.meshesha.tetrisremake.sates;

import com.meshesha.tetrisremake.drawables.Button;
import com.meshesha.tetrisremake.drawables.TextUI;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;

/*
 * Simple Main Menu State with 2 buttons and Paused Text
 */
public class PauseState extends TetrisState {
    private TextUI paused;
    private StateBasedGame game;
    private Button resume;
    private Button quit;
    private float screenWidth;
    private float screenHeight;
    public static int ID = 4;

    @Override
    public int getID() {
        return ID;
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();
        background = new Image("res/images/Background_blurred2.png");
        paused = new TextUI(90, "Paused", "Square", Color.white, container.getWidth(), container.getHeight());
        resume = new Button(800, 500, 300, 70, 7);
        resume.setText("Resume", 50, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        resume.setClickedAction(() -> game.enterState(Game.ID, new PauseTransition(40), new EmptyTransition()));
        quit = new Button(resume.getX() + 2, resume.getY() + resume.getHeight() * 2, resume.getWidth(), resume.getHeight(), 7);
        quit.setText("Quit", 50, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        quit.setClickedAction(container::exit);
        this.game = game;
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        paused.draw(800, 100);
        resume.draw();
        quit.draw();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

    }

    /*
     * Escape can be pressed to resume instead of the resume button if preferred
     */
    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Input.KEY_ESCAPE) {


            game.enterState(Game.ID, new PauseTransition(40), new EmptyTransition());

        }
    }


    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        resume.playClicked();
        quit.playClicked();
    }


    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        resume.playHovered(newx, newy, screenWidth, screenHeight);
        quit.playHovered(newx, newy, screenWidth, screenHeight);
        super.mouseMoved(oldx, oldy, newx, newy);

    }
}
