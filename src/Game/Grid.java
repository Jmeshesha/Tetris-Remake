package Game;


import Pieces.BasicPiece;
import Pieces.Block;
import UI.TextUI;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Polygon;

public class Grid {
    Block[][] grid = new Block[22][9];
    BasicPiece endLocation;
    BasicPiece prevPiece;
    float startX;
    float startY;
    float blockSize;

    TextUI scoreTxt ;
    TextUI levelTxt;
    TextUI linesTxt;
    int level = 0;
    int levelCounter = 0;
    int score = 0;
    int lines = 0;
    //Score per line
    int[] spl = {0, 40, 100, 300, 1200};
    //Frames per line out of 60 frames
    public int[] tpl = {48, 43, 38, 33,  28, 23, 18, 13, 8, 6, 5, 4, 3, 2, 1};

    public Grid(float startX, float startY, float blockSize) throws SlickException {

        this.startX = startX;
        this.startY = startY;
        this.blockSize = blockSize;
        scoreTxt = new TextUI(1000, 100, 55, "Score: "+ score, "8-bit Arcade In", Color.white);
        levelTxt = new TextUI(300, 100, 55, "Level: " + level, "8-bit Arcade In", Color.white );
        linesTxt = new TextUI(105, 150, 55,"Lines Cleared: "  + lines, "8-bit Arcade In", Color.white);


    }
    public void putBlockToGrid(Block b){

        grid[b.getY()][b.getX()] = b;

    }

    /**
     * Removes a line from the grid array and pushes down every line above it
     * @param lineNumber
     *            Starts at 0 where 0 is the bottom Line
     *
     */
    public void removeLine(int lineNumber){
        if(lineNumber != 0 ){
            for(int i = 0; i<9; i++){
                grid [lineNumber][i] = grid[lineNumber-1][i];
                if(grid[lineNumber-1][i] != null)
                    grid[lineNumber-1][i].moveDown(1);

            }
            removeLine(lineNumber-1);
        }

    }
    /**
     * Determines if a line has been cleared
     * */
    public void checkLines(){
        int lineCounter= 0;
        for(int i = 0; i< 21; i++){
            boolean lineFilled = true;
            for(int j = 0; j < 9; j++){
                if(!isFilled(j,i)){
                    lineFilled = false;
                }
            }
            if(lineFilled) {
                removeLine(i);
                lineCounter++;
                levelCounter++;
                checkLevel();
            }
        }
        lines +=  lineCounter;
        score += (level+1)*spl[lineCounter];
    }
    /**
     * Determines if a level up has occurred
     * */
    public void checkLevel(){
        if(levelCounter >=10){
            level++;
            levelCounter = 0;
        }
    }
    public void drawStats(Graphics g) throws SlickException {
        scoreTxt.setText("Score " + score);
        levelTxt.setText("Level " + level);
        linesTxt.setText("Lines Cleared " + lines);

        scoreTxt.draw();
        levelTxt.draw();
        linesTxt.draw();
        /*g.drawString("Score: " + score, 1000, 100);
        g.drawString("Level: " + level, 350, 100);
        g.drawString("Lines Cleared: "  + lines, 277, 150);*/
    }
    /**
     * Checks if a specific block in the grid array is filled
     * @param x
     *         The x value of the block
     * @param y
     *        The y value of the block
     * */
    public boolean isFilled(int x, int y){
        if(y < 0 || x<0)
            return false;
        try {
            return grid[y][x] != null;
        }catch (ArrayIndexOutOfBoundsException e){
            int range;
            if(x <0){
                range = 0;
            }else{
                range = 8;
            }
            return grid[y][range] != null;
        }
    }
    /**
     * Checks if a block can move to a specific coordinate
     * @param x
     *         The x value of the block
     * @param y
     *        The y value of the block
     * */
    public boolean canMoveHere(int x, int y){
        return x >= 0 && x <= 8 && y <= 20 && !isFilled(x, y);
    }
    /**
     * Draws every non-null block in the grid array
     * */
    public void drawBackground(Graphics g){
        Polygon rect = new Polygon();
        rect.addPoint(startX, startY);
        rect.addPoint(startX + 9*blockSize, startY);
        rect.addPoint(startX + 9*blockSize, startY +21*blockSize);
        rect.addPoint(startX, startY + 21*blockSize);
        GradientFill fill = new GradientFill(0, 0, new Color(0, 0, 0), 1920, 1080, new Color(0, 0, 0), true);
        g.fill(rect, fill);
        g.draw(rect);
    }
    public void draw(Graphics g){


        for (int i = 0; i < 21; i++){
            for(int j = 0; j < 9; j++){
                if(grid[i][j]!= null){
                    Block current = grid[i][j];
                    current.draw(startX, startY, blockSize);
                }
            }
        }

        for(int k = 0; k <= 9; k++) {
            g.drawLine(startX + k * blockSize, startY, startX + k * blockSize, startY + 21 * blockSize);
        }
        for(int j = 0; j<= 21; j++){
            g.drawLine(startX, startY+ j*blockSize, startX + 9*blockSize, startY+j*blockSize);
        }

    }




}
