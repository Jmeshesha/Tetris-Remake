package com.meshesha.tetrisremake.drawables.Pieces;

import com.meshesha.tetrisremake.drawables.Grid;
import org.newdawn.slick.SlickException;

/*
* Abstract Piece class that should be extended to change the structure
*/
public abstract class BasicPiece implements Cloneable {
    protected Block[] structure;
    protected double centerX;
    protected double centerY;
    protected Integer minY;
    protected int size;
    private boolean isSelected = false;
    protected boolean canRotate = false;
    protected String name;

    public BasicPiece(int size)  {
        structure = new Block[size];

    }


    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }




    public Object clone() throws CloneNotSupportedException {
        BasicPiece b = (BasicPiece) super.clone();
        b.structure = new Block[structure.length];
        return b;

    }

    public Integer getMinY() {
        return minY;
    }


    /*
    * Gets the ref of piece depending on if its an end location
    */
    public String getCurrentColor(boolean isEndLoc)  {
        if (isEndLoc) {
            return "res/images/EndLoc_Block.png";
        }
        return "res/images/Block.png";
    }

    /*
    * Where the structure of piece will be specified when extended
    */
    public abstract void init(boolean isEndLoc) throws SlickException;

    public void draw(float startX, float startY, float width, float height, float screenWidth, float screenHeight) {
        for (Block block : structure) {
            block.draw(startX, startY, width, height, screenWidth, screenHeight);

        }
    }


    /*
    * Movement methods return boolean to show whether this movement can be performed
    */
    public boolean moveUp(Grid grid, int x) throws SlickException {
        Block[] copy = new Block[structure.length];
        for (int i = 0; i < structure.length; i++) {
            copy[i] = new Block(structure[i].getImage(), structure[i].getX(), structure[i].getY());
            copy[i].moveDown(-x);
            if (!grid.canMoveHere(copy[i].getX(), copy[i].getY())) {
                return false;
            }
        }
        centerY -= x;

        structure = copy;
        return true;
    }

    public boolean rotate(Grid grid) throws SlickException {
        if (!canRotate)
            return false;
        Block[] copy = new Block[structure.length];
        for (int i = 0; i < structure.length; i++) {
            copy[i] = new Block(structure[i].getImage(), structure[i].getX(), structure[i].getY());
            copy[i].rotate(centerX, centerY);
            if (!grid.canMoveHere(copy[i].getX(), copy[i].getY())) {
                return false;
            }
        }

        structure = copy;
        return true;
    }

    public boolean moveSideways(Grid grid, int x) throws SlickException {
        Block[] copy = new Block[structure.length];
        for (int i = 0; i < structure.length; i++) {
            copy[i] = new Block(structure[i].getImage(), structure[i].getX(), structure[i].getY());
            copy[i].moveSide(x);
            if (!grid.canMoveHere(copy[i].getX(), copy[i].getY())) {
                return false;
            }
        }
        structure = copy;
        centerX += x;
        return true;
    }


    public boolean moveDown(Grid grid) throws SlickException {
        Block[] copy = new Block[structure.length];
        for (int i = 0; i < structure.length; i++) {
            copy[i] = new Block(structure[i].getImage(), structure[i].getX(), structure[i].getY());
            copy[i].moveDown(1);
            if (!grid.canMoveHere(copy[i].getX(), copy[i].getY())) {
                return false;
            }
        }
        structure = copy;
        centerY++;
        return true;
    }

    public boolean moveDown(int y) throws SlickException {
        Block[] copy = new Block[structure.length];
        for (int i = 0; i < structure.length; i++) {
            copy[i] = new Block(structure[i].getImage(), structure[i].getX(), structure[i].getY());
            copy[i].moveDown(y);
        }
        structure = copy;
        centerY++;
        return true;
    }



    public Block[] getStructure() {
        return structure;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
