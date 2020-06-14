package Sates;

import Drawables.Button;
import Drawables.TextUI;
import Main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;

public class PauseState extends TetrisState {
    Image background;
    TextUI paused;
    StateBasedGame game;
    Button resume;
    Button quit;
    @Override
    public int getID() {
        return 6;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        background = new Image("res/images/Background2_blurred2.png");
        paused = new TextUI(90, "Paused", "Square", Color.white);
        resume = new Button(800, 500, 300, 70, 7);
        resume.setText("Resume", 50, "AldoTheApache", Color.white);
        resume.setClickedAction(() ->game.enterState(1, new PauseTransition(40), new EmptyTransition()));
        quit = new Button(resume.getX()+2, resume.getY()+resume.getHeight()*2, resume.getWidth(), resume.getHeight(), 7);
        quit.setText("Quit", 50, "AldoTheApache", Color.white);
        quit.setClickedAction(() -> container.exit());
        setBackground(background);
        this.game = game;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
        paused.draw(800, 100);
        resume.draw(g);
        quit.draw(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (key == Input.KEY_ESCAPE) {

            //pause.initSingle();
            //isPaused = !isPaused;
            game.enterState(1, new PauseTransition(40), new EmptyTransition());

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
        resume.playHovered(newx, newy);
        quit.playHovered(newx, newy);
        super.mouseMoved(oldx, oldy, newx, newy);

    }
}
