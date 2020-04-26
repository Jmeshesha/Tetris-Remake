package Game;


import Pieces.BasicPiece;
import Pieces.Block;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Grid {
    Block[][] grid = new Block[22][9];
    BasicPiece endLocation;
    BasicPiece prevPiece;
    float startX;
    float startY;
    int level = 1;
    int levelCounter = 0;
    int score = 0;
    public Grid(float startX, float startY) throws SlickException {

        this.startX = startX;
        this.startY = startY;
    }
    public void putBlockToGrid(Block b){

        grid[b.getY()][b.getX()] = b;

    }
    public void putPieceToGrid(BasicPiece piece) {

    }

    /**
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

    public void checkLines(){
        for(int i = 0; i< 21; i++){
            boolean lineFilled = true;
            for(int j = 0; j < 9; j++){
                if(!isFilled(j,i)){
                    lineFilled = false;
                }
            }
            if(lineFilled) {
                removeLine(i);
                score += level*10;
                levelCounter++;
                checkLevel();
            }
        }
    }
    public void checkLevel(){
        if(levelCounter >= 50){
            level++;
            levelCounter = 0;
        }
    }
    public void drawScoreAndLvl(Graphics g){
        g.drawString("Score: " + score, 1000, 100);
        g.drawString("Level: " + level, 350, 100);
    }
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
    public boolean canMoveHere(int x, int y){
        return x >= 0 && x <= 8 && y <= 20 && !isFilled(x, y);
    }
    public void drawGrid(){
        for (int i = 0; i< 21; i++){
            for(int j = 0; j<9; j++){
                if(grid[i][j]!= null){
                    Block current = grid[i][j];
                    current.draw(startX, startY);
                }
            }
        }
    }
}
