package Sates;

import Main.Main;
import UI.TextUI;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver  extends BasicGameState {
    public static final int ID = 4;
    private StateBasedGame game;
    TextUI gameOver;
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        gameOver = new TextUI(50, "Game Over", "8-BIT WONDER", Color.white);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //TODO: Add Buttons
        Image i = new Image("res/images/Background2.png");
        i.setImageColor(0.25f, 0.25f, 0.25f);
        i.draw(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
        gameOver.draw(850, Main.getScreenHeight()/4);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }
}
