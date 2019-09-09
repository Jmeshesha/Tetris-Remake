package Game;


import Game.Pieces.Block;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.jar.JarEntry;

public class Grid {
    Graphics g;
    Block[][] grid = new Block[21][9];
    float startX;
    float startY;
    public Grid(float startX, float startY) throws SlickException {

        this.g = g;
        this.startX = startX;
        this.startY = startY;
    }
    public void putBlockToGrid(Block b, int x, int y ){

        grid[y][x] = b;
        b.setX(x);
        b.setY(y);

    }

    /**
     * @param lineNumber
     *            Starts at 0 where 0 is the bottom Line
     *
     */
    public void removeLine(int lineNumber){
        Block[] temp;
        if(grid[lineNumber].length == 0){
            return;
        }
        while(lineNumber <20 && grid[lineNumber].length != 0 ){
            grid[lineNumber++] = grid[lineNumber];
        }
        if(lineNumber == 20){
            grid[lineNumber] = new Block[9];
        }

    }
    public boolean isFilled(int x, int y){
        return grid[y][x] != null;
    }

    public void drawGrid(){
        for (int i = 0; i< grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                Block current = grid[i][j];
                if(current != null){
                    //g.drawImage(current.getCurrentColor(), getX1(current), getY1(current), getX2(current), getY2(current), 0, 0, 1000, 1000);
                    //current.getCurrentColor().draw(getX1(current), getY1(current), current.getSize(),  current.getSize());
                    /*g.drawLine(getX1(current), getY1(current), getX1(current), getY2(current));
                    g.drawLine(getX2(current), getY1(current), getX2(current), getY2(current));
                    g.drawLine(getX1(current), getY1(current), getX2(current), getY1(current));
                    g.drawLine(getX1(current), getY2(current), getX2(current), getY2(current));*/
                    current.draw(startX, startY);

                }
            }
        }
    }
    public int getX1(Block current){
        return current.getSize()*current.getX();
    }
    public int getY1(Block current){
        return current.getSize()*current.getY();
    }
    public int getX2(Block current){
        return current.getSize()*current.getX() + current.getSize();
    }
    public int getY2(Block current){
        return current.getSize()*current.getY() + current.getSize();
    }
    public void drawBoxAroundBlock(){

    }
}
