package Game;

import Game.Pieces.Block;
import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
    public static final int ID = 1;
    private StateBasedGame game;
    private Grid grid;
    @Override

    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        this.game = game;
        grid = new Grid(500, 100);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        Block block1 =  new Block("images/green.png");
        Block block2 =  new Block("images/green.png");
        Block block3 =  new Block("images/green.png");
        Block block4 =  new Block("images/green.png");

        grid.putBlockToGrid(block1, 2, 2);
        grid.putBlockToGrid(block2, 2, 3);
        grid.putBlockToGrid(block3, 1, 2);
        grid.putBlockToGrid(block4, 1, 3);
        grid.drawGrid();

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }

}
