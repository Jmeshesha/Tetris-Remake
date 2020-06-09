package Sates;

import Actions.BasicAction;
import Drawables.Button;
import Main.Main;
import Drawables.TextUI;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.w3c.dom.Text;

import java.util.LinkedList;


public class GameOver  extends BasicGameState {
    public static final int ID = 4;
    private StateBasedGame game;
    TextUI gameOver;
    Button startOver;
    Button mainMenu;
    Button quit;

    @Override
    public int getID() {
        return ID;
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        gameOver = new TextUI(70, "Game Over", "8-bit40", Color.white);
        startOver = new Button(800, 500, 300, 70);
        startOver.setText("Start Over", 40, "AldoTheApache", Color.white);
        startOver.setClickedAction(() -> game.enterState(1));
        mainMenu = new Button(startOver.getX(), startOver.getY()+startOver.getHeight()*2, startOver.getWidth(), startOver.getHeight());
        mainMenu.setText("Main Menu", 40, "AldoTheApache", Color.white);
        mainMenu.setClickedAction(() -> game.enterState(2));
        quit = new Button(mainMenu.getX(), mainMenu.getY()+mainMenu.getHeight()*2, mainMenu.getWidth(), mainMenu.getHeight());
        quit.setText("Quit", 40, "AldoTheApache", Color.white);
        quit.setClickedAction(() -> container.exit());

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        //TODO: Add Buttons

        Image i = new Image("res/images/Background2.png");
        i.setImageColor(0.2f, 0.25f, 0.25f);
        i.draw(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
        gameOver.draw((Main.getScreenWidth()) * (750f - 0.1f*gameOver.getText().length() * gameOver.getSize())/1920f, Main.getScreenHeight()/5);
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
