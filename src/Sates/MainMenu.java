package Sates;

import Drawables.Button;
import Drawables.ImagePopUp;
import Main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenu  extends TetrisState {
    public static final int ID = 2;
    private StateBasedGame game;
    //Image background;
    Image logo;
    Image bgCopy;
    Button play;
    Button options;
    Button pieceCreator;
    Button quit;
    ImagePopUp playTransition;
    boolean isPlay = false;

    @Override
    public int getID() {
        return ID;
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
            game.enterState(Game.ID, new FadeOutTransition(), new FadeInTransition());
        });
        pieceCreator = new Button(play.getX(), play.getY()+play.getHeight()*2, play.getWidth(), play.getHeight(), 5);
        pieceCreator.setText("Piece Creator", 40, "AldoTheApache", Color.white);
        pieceCreator.setClickedAction(() -> game.enterState(5));
        options = new Button(pieceCreator.getX(), pieceCreator.getY()+pieceCreator.getHeight()*2, pieceCreator.getWidth(), pieceCreator.getHeight(), 5);
        options.setText("Options", 40, "AldoTheApache", Color.white);
        options.setClickedAction(() -> game.enterState(3));

        quit = new Button(options.getX(), options.getY()+options.getHeight()*2, options.getWidth(), options.getHeight(), 5);
        quit.setText("Quit", 40, "AldoTheApache", Color.white);
        quit.setClickedAction(() -> container.exit());
        setBackground(background);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
            background.draw(0, 0, 1920, 1080);
            logo.draw(Main.getScreenWidth()/2 - 500f/2f*(1920f/Main.getScreenWidth()), 10*1080/Main.getScreenHeight(), 500f, 500f);
            play.draw(g);
            options.draw(g);
            pieceCreator.draw(g);
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
        options.playClicked();
        pieceCreator.playClicked();
        quit.playClicked();
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        play.playHovered(newx, newy);
        options.playHovered(newx, newy);
        pieceCreator.playHovered(newx, newy);
        quit.playHovered(newx, newy);
        super.mouseMoved(oldx, oldy, newx, newy);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);
        //playTransition.initSingle();
    }
}
