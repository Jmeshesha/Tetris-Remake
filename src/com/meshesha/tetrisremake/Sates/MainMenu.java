package com.meshesha.tetrisremake.Sates;

import com.meshesha.tetrisremake.Drawables.Button;
import com.meshesha.tetrisremake.Drawables.ImagePopUp;
import com.meshesha.tetrisremake.Main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu  extends TetrisState {
    public static final int ID = 2;
    private StateBasedGame game;
    //Image background;
    Image logo;
    Image bgCopy;
    Button play;
    Button pieceSelector;
    Button quit;
    ImagePopUp playTransition;
    boolean isPlay = false;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        if(Main.selectedPieces != null)
        System.out.println(Main.selectedPieces.length);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        isPlay = false;
        this.game = game;
        background = new Image("res/images/Background2_blurred.png");
        bgCopy = new Image(background.getResourceReference());
        bgCopy.setAlpha(0);
        playTransition = new ImagePopUp(bgCopy, 25, 1, 0.0003f);
        logo = new Image("res/images/TetrisLogo.png");
        play = new Button(800, 500, 300, 70, 5);
        play.setText("Play", 40, "AldoTheApache", Color.white);
        play.setClickedAction(() -> {
            game.enterState(com.meshesha.tetrisremake.Sates.Game.ID, new FadeOutTransition(), new FadeInTransition());
        });
        pieceSelector = new Button(play.getX(), play.getY()+play.getHeight()*2, play.getWidth(), play.getHeight(), 5);
        pieceSelector.setText("Piece Selector", 40, "AldoTheApache", Color.white);
        pieceSelector.setClickedAction(() -> game.enterState(PieceSelector.ID, new PauseTransition(100), new EmptyTransition()));


        quit = new Button(pieceSelector.getX(), pieceSelector.getY()+pieceSelector.getHeight()*2, pieceSelector.getWidth(), pieceSelector.getHeight(), 5);
        quit.setText("Quit", 40, "AldoTheApache", Color.white);
        quit.setClickedAction(() -> container.exit());
        setBackground(background);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
            background.draw(0, 0, 1920, 1080);
            logo.draw(Main.getScreenWidth()/2 - 500f/2f*(1920f/ Main.getScreenWidth()), 10*1080/ Main.getScreenHeight(), 500f, 500f);
            play.draw(g);
            pieceSelector.draw(g);
            quit.draw(g);




    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(isPlay){
            if(!playTransition.isDone()) {
                playTransition.play(delta);
            }else{
                game.enterState(Game.ID);
            }

        }
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
        play.playHovered(newx, newy);
        pieceSelector.playHovered(newx, newy);
        quit.playHovered(newx, newy);
        super.mouseMoved(oldx, oldy, newx, newy);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);

    }
}
