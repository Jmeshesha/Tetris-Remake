package com.meshesha.tetrisremake.Sates;

import com.meshesha.tetrisremake.Drawables.Button;
import com.meshesha.tetrisremake.Drawables.TextUI;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import com.meshesha.tetrisremake.Main.Main;


public class GameOver  extends TetrisState {
    public static final int ID = 4;
    private StateBasedGame game;
    TextUI gameOver;
    TextUI gameOverBg;
    Button startOver;
    Button mainMenu;
    Button quit;
    Image background;
    @Override
    public int getID() {
        return ID;
    }




    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        gameOver = new TextUI(70, "Game Over", "Square", new Color(0.8f, 0.0f, 0.0f));
        startOver = new Button(800, 500, 300, 70, 7);
        startOver.setText("Start Over", 40, "AldoTheApache", Color.white);
        startOver.setClickedAction(() -> game.enterState(1, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500)));
        mainMenu = new Button(startOver.getX(), startOver.getY()+startOver.getHeight()*2, startOver.getWidth(), startOver.getHeight(), 7);
        mainMenu.setText("Main Menu", 40, "AldoTheApache", Color.white);
        mainMenu.setClickedAction(() -> game.enterState(2, new FadeOutTransition(), new FadeInTransition()));
        quit = new Button(mainMenu.getX(), mainMenu.getY()+mainMenu.getHeight()*2, mainMenu.getWidth(), mainMenu.getHeight(), 7);
        quit.setText("Quit", 40, "AldoTheApache", Color.white);
        quit.setClickedAction(() -> container.exit());
        background = new Image("res/images/Background2.png");
        setBackground(background);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //TODO: Add Buttons


        background.setImageColor(0.2f, 0.25f, 0.25f);
        background.draw(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
        gameOver.draw((Main.getScreenWidth()) * (800f - 0.02f*gameOver.getText().length() * gameOver.getSize())/1920f, Main.getScreenHeight()/5);
        startOver.draw(g);
        mainMenu.draw(g);
        quit.draw(g);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

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
        startOver.playHovered(newx, newy);
        mainMenu.playHovered(newx, newy);
        quit.playHovered(newx, newy);
        super.mouseMoved(oldx, oldy, newx, newy);

    }
}
