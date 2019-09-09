package Main;


import Game.Game;
import Sates.MainMenu;
import Sates.Options;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    static String Title = "Test";
    static int WindowHeight = 1920;
    static int WindowWidth = 1080;
    static boolean isFullscreen = true;

    public static void main(String[] args) throws SlickException {
        AppGameContainer App = new AppGameContainer(new Main());
        App.setDisplayMode(WindowHeight, WindowWidth, isFullscreen);

        App.start();
        System.out.println("test");


    }
    public Main(){
        super(Title);
    }
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainMenu());
        addState(new Game());
        addState(new Options());
        enterState(1);
    }
}
