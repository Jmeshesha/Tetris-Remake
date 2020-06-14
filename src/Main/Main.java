package Main;


import Sates.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame  {

    static String Title = "Tetris";
    public static int WindowWidth = 1920;
    public static int WindowHeight = 800;

    static boolean isFullscreen = true;
    static AppGameContainer App;

    public static void main(String[] args) throws SlickException {
        App = new AppGameContainer(new Main());
        App.setDisplayMode(App.getScreenWidth(), App.getScreenHeight(), isFullscreen);
        System.out.println(App.getScreenHeight());
        App.start();
    }
    public Main(){
        super(Title);
    }
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainMenu());
        addState(new Game());
        addState(new Options());
        addState(new GameOver());
        addState(new PieceCreator());
        addState(new PauseState());
        enterState(2);
    }
    public static float getScreenHeight(){
        return App.getScreenHeight();
    }
    public static float getScreenWidth(){
        return App.getScreenWidth();
    }

}
