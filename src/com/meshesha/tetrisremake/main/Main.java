package com.meshesha.tetrisremake.main;

import com.meshesha.tetrisremake.drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.sates.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
    //Title of window on display (Not shown because game is full screen but still needed)
    static String Title = "Tetris";
    public static BasicPiece[] selectedPieces;

    static boolean isFullscreen = true;
    static AppGameContainer App;

    /**
     * Creates a full screen display with its native resolution
     */
    public static void main(String[] args) throws SlickException {
        App = new AppGameContainer(new Main());
        App.setDisplayMode(App.getScreenWidth(), App.getScreenHeight(), true);


        App.start();
    }

    public Main() {
        super(Title);
    }

    /**
     * Adds all the states needed and starts in the main menu state
     */
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainMenu());
        addState(new Game());
        addState(new GameOver());
        addState(new PieceCreator());
        addState(new PauseState());
        addState(new PieceSelector());
        enterState(MainMenu.ID);
    }

}
