package com.meshesha.tetrisremake.sates;

import com.meshesha.tetrisremake.drawables.Button;
import com.meshesha.tetrisremake.drawables.TextUI;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
* Simple Game Over state with 3 buttons
*/
public class GameOver extends TetrisState {
    public static final int ID = 3;
    private TextUI gameOver;
    private Button startOver;
    private Button mainMenu;
    private Button quit;
    private Image background;
    private float screenWidth;
    private float screenHeight;

    @Override
    public int getID() {
        return ID;
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        gameOver = new TextUI(70, "Game Over", "Square", new Color(0.8f, 0.0f, 0.0f), container.getWidth(), container.getHeight());
        startOver = new Button(800, 500, 300, 70, 7);
        startOver.setText("Start Over", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        startOver.setClickedAction(() -> game.enterState(Game.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500)));
        mainMenu = new Button(startOver.getX(), startOver.getY() + startOver.getHeight() * 2, startOver.getWidth(), startOver.getHeight(), 7);
        mainMenu.setText("Main Menu", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        mainMenu.setClickedAction(() -> game.enterState(MainMenu.ID, new FadeOutTransition(), new FadeInTransition()));
        quit = new Button(mainMenu.getX(), mainMenu.getY() + mainMenu.getHeight() * 2, mainMenu.getWidth(), mainMenu.getHeight(), 7);
        quit.setText("Quit", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        quit.setClickedAction(container::exit);
        background = new Image("res/images/Background.png");
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {

        background.setImageColor(0.2f, 0.25f, 0.25f);
        background.draw(0, 0, container.getWidth(), container.getHeight());
        gameOver.draw(800f - 0.02f * gameOver.getText().length() * gameOver.getSize(), 216);
        startOver.draw();
        mainMenu.draw();
        quit.draw();

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)  {

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        startOver.playClicked();
        mainMenu.playClicked();
        quit.playClicked();
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        startOver.playHovered(newx, newy, screenWidth, screenHeight);
        mainMenu.playHovered(newx, newy, screenWidth, screenHeight);
        quit.playHovered(newx, newy, screenWidth, screenHeight);
        super.mouseMoved(oldx, oldy, newx, newy);

    }
}
