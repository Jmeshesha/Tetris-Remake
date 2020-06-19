package com.meshesha.tetrisremake.Drawables;


import com.meshesha.tetrisremake.Drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.Drawables.Pieces.Block;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;

public class Grid {
    Block[][] grid;
    BasicPiece endLocation;
    BasicPiece prevPiece;
    public float startX;
    public float startY;
    public float blockWidth;
    public float blockHeight;

    TextUI stats ;
    PieceHolder statsHolder;
    public int level = 0;
    public int levelCounter = 0;
    public int score = 0;
    public int top;
    public int lines = 0;
    public int width;
    public int height;
    //Score per line
    public int[] spl = {0, 40, 100, 300, 1200};
    //Frames per line out of 60 frames
    public int[] tpl = {48, 43, 38, 33,  28, 23, 18, 13, 8, 6, 5, 4, 3, 2, 1};

    public Grid(float startX, float startY, float blockWidth, float blockHeight) throws SlickException {

        this.startX = startX;
        this.startY = startY;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.height = 21;
        this.width = 9;
        grid = new Block[21][9];

    }
    public Grid(float startX, float startY, float blockWidth, float blockHeight, int width, int height){
        this.startX = startX;
        this.startY = startY;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.height = height;
        this.width = width;
        grid = new Block[height][width];
    }
    public Block[][] getBlockArr(){
        return grid;

    }
    public Block getBlock(int x, int y){
        return grid[y][x];
    }
    public void putBlockToGrid(Block b){

        grid[b.getY()][b.getX()] = b;

    }
    public void removeBlock(int x, int y){
        grid[y][x] = null;
    }
    public void initStats(){
        stats = new TextUI( 22.5f, "Current Score "+ score + "\nTop Score\n" + top + "\nLevel\n" + level + "\nLines Cleared\n" + lines, "Square", Color.white);
        statsHolder = new PieceHolder(startX+blockWidth*9+1, startY +blockHeight*8, stats.getSize()*10, stats.getSize()*11, blockWidth, blockHeight,  "Score");
    }
    public void setTop(int newTop){
        if(newTop > top)
            top = newTop;
    }
    public Integer getRelativeX(int x){

        if(x <= startX || x >= startX + width*blockWidth) return null;
        return (int)((x-startX)/blockWidth);
    }
    public Integer getRelativeY(int y){

        if(y <= startY || y >= startY + height*blockHeight) return null;
        return (int)((y-startY)/blockHeight);
    }
    public int getTop(){
        return top;
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
        for(int i = 0; i< height; i++){
            boolean lineFilled = true;
            for(int j = 0; j < width; j++){
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
        if (score > top){
            top = score;
        }
        stats.setText("Current Score\n"+ score + "\nTop Score\n" + top + "\nLevel\n" + level + "\nLines Cleared\n" + lines);
        statsHolder.drawBackground(g);
        stats.draw(statsHolder.getX() + blockWidth/2, statsHolder.getY() + blockHeight/4);
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
                range = width-1;
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
        return x >= 0 && x <= width-1 && y <= height-1 && !isFilled(x, y);
    }
    /**
     * Draws every non-null block in the grid array
     * */
    public void drawBackground(Graphics g){
        g.setLineWidth(3);
        RoundedRectangle rect = new RoundedRectangle(startX-2, startY, grid[0].length*blockWidth+6, (grid.length)*blockHeight+3, 5);
        GradientFill fill = new GradientFill(0, 0, new Color(0, 0, 0), 1920, 1080, new Color(0, 0, 0), true);
        g.fill(rect, fill);
        g.draw(rect);
        g.setLineWidth(1.5f);
    }
    public void draw(Graphics g){


        for (int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(grid[i][j]!= null){
                    Block current = grid[i][j];
                    current.draw(startX, startY, blockWidth, blockHeight);
                }
            }
        }
    }




}
