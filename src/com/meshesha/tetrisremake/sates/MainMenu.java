package com.meshesha.tetrisremake.sates;

import com.meshesha.tetrisremake.drawables.Button;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * Simple Main Menu State with 3 buttons and Custom made Tetris Logo
 */
public class MainMenu extends TetrisState {
    public static final int ID = 1;
    private Image logo;
    private Button play;
    private Button pieceSelector;
    private Button quit;
    private float screenWidth;
    private float screenHeight;

    @Override
    public int getID() {
        return ID;
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();
        background = new Image("res/images/Background_blurred.png");


        logo = new Image("res/images/TetrisLogo.png");
        play = new Button(800, 500, 300, 70, 5);
        play.setText("Play", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        play.setClickedAction(() -> game.enterState(Game.ID, new FadeOutTransition(), new FadeInTransition()));
        pieceSelector = new Button(play.getX(), play.getY() + play.getHeight() * 2, play.getWidth(), play.getHeight(), 5);
        pieceSelector.setText("Piece Selector", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        pieceSelector.setClickedAction(() -> game.enterState(PieceSelector.ID, new PauseTransition(100), new EmptyTransition()));


        quit = new Button(pieceSelector.getX(), pieceSelector.getY() + pieceSelector.getHeight() * 2, pieceSelector.getWidth(), pieceSelector.getHeight(), 5);
        quit.setText("Quit", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        quit.setClickedAction(container::exit);
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        logo.draw(container.getWidth() / 2f - 500f / 2f * (container.getWidth() / 1920f), 10 * container.getHeight() / 1080f, 500f * (container.getWidth() / 1920f), 500f * container.getHeight() / 1080f);
        play.draw();
        pieceSelector.draw();
        quit.draw();


    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

    }


    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        play.playClicked();
        pieceSelector.playClicked();
        quit.playClicked();
    }


    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        play.playHovered(newx, newy, screenWidth, screenHeight);
        pieceSelector.playHovered(newx, newy, screenWidth, screenHeight);
        quit.playHovered(newx, newy, screenWidth, screenHeight);
        super.mouseMoved(oldx, oldy, newx, newy);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);

    }
}
