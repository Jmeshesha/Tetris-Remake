package com.meshesha.tetrisremake.Sates;

import com.meshesha.tetrisremake.Drawables.*;
import com.meshesha.tetrisremake.Drawables.Pieces.Block;
import com.meshesha.tetrisremake.FileIO.BlockJson;
import com.meshesha.tetrisremake.FileIO.JSONReader;
import com.meshesha.tetrisremake.Main.Main;
import com.google.gson.Gson;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PieceCreator extends TetrisState {
    public static int ID = 5;
    Grid grid;
    Button back;
    Button save;
    Image selection;
    Slider redSlider;
    Slider greenSlider;
    Slider blueSlider;
    TextUI redValue;
    TextUI greenValue;
    TextUI blueValue;
    TextUI modeText;
    TextBox title;


    boolean drawSelection;
    boolean saving;
    int selectX = 0;
    int selectY = 0;
    int mode;
    int midX;
    int midY;

    Image midpoint;
    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        drawSelection = false;
        midX = 2;
        midY = 2;
        saving =false;
        grid = new Grid(700, 100, 100, 100, 5, 5);
        back = new Button(600, 900, 300, 70, 5);
        back.setText("Back", 40, "AldoTheApache", Color.white);
        back.setClickedAction(() -> {
            game.enterState(PieceSelector.ID, new PauseTransition(100), new EmptyTransition());
        });
        save = new Button(1000, 900, 300, 70, 5);
        save.setText("Save and Exit", 40, "AldoTheApache", Color.white);
        save.setClickedAction(() -> {
            savePiece();
            saving = false;
            game.enterState(PieceSelector.ID, new PauseTransition(100), new EmptyTransition());
        });

        modeText = new TextUI(20, "Selection Mode: Structure" , "Square", Color.white);
        title = new TextBox(800, 30, "Title", "Forced Square", 20, 40, Color.white);
        mode = 0;


        midpoint = new Image("res/images/EndLoc_Block.png");
        midpoint.setImageColor(1.0f, 0.5f, 0f);
        redSlider = new Slider(1500, 100, 300, 10, 0, 1, 1);
        greenSlider = new Slider(1500, 150, 300, 10, 0, 1, 1);
        blueSlider = new Slider(1500, 200, 300, 10, 0, 1, 0);
        redValue = new TextUI(20, "Red: ", "Square", Color.white);
        greenValue = new TextUI(20, "Red: ", "Square", Color.white);
        blueValue = new TextUI(20, "Red: ", "Square", Color.white);
        background = new Image("res/images/Background2_blurred.png");
        selection = new Image("res/images/Block.png");
        selection.setImageColor(1f, 1f, 0f, 0.75f);
        setBackground(background);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
        grid.drawBackground(g);
        grid.draw(g);
        back.draw(g);
        title.draw(g);
        if(midX >= 0 && midY >= 0){
            midpoint.draw(grid.startX + midX*grid.blockWidth ,grid.startY + midY*grid.blockHeight, grid.blockWidth, grid.blockHeight);
        }

        if(drawSelection){
            selection.draw(grid.startX + selectX*grid.blockWidth ,grid.startY + selectY*grid.blockHeight, grid.blockWidth, grid.blockHeight);
        }
        redSlider.draw(g);
        greenSlider.draw(g);
        blueSlider.draw(g);
        redValue.draw(1250, 100);
        greenValue.draw(1250, 150);
        blueValue.draw(1250, 200);
        modeText.draw(1250, 300);
        save.draw(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if(!saving) {
            redValue.setText("Red: " + redSlider.getValue());
            greenValue.setText("Green: " + greenSlider.getValue());
            blueValue.setText("Blue: " + blueSlider.getValue());
            if (mode == 0) {
                selection.setImageColor(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), 0.75f);
            } else if (mode == 1) {
                selection.setImageColor(1f, 1f, 0f);
            } else if (mode == 2) {
                selection.setImageColor(1f, 0f, 0.5f);
            }
            title.update();
        }
    }
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if(!saving) {
            back.playClicked();
            redSlider.updateSlider(x, y, true);
            greenSlider.updateSlider(x, y, true);
            blueSlider.updateSlider(x, y, true);
            title.updateCursor(x, y);
            save.playClicked();

            if (drawSelection) {
                if (mode == 0) {
                    if (grid.getBlock(selectX, selectY) != null) {
                        grid.removeBlock(selectX, selectY);
                    } else {
                        try {
                            Image i = new Image("res/images/Block.png");
                            i.setImageColor(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
                            Block b = new Block(i, selectX, selectY);
                            b.setColor(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
                            grid.putBlockToGrid(b);
                        } catch (SlickException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (mode == 1) {
                    if (selectX == midX && selectY == midY) {
                        midX = -1;
                        midY = -1;
                    } else {
                        midX = selectX;
                        midY = selectY;
                    }


                } else if (mode == 2) {
                    Block b = grid.getBlock(selectX, selectY);
                    if (b != null) {
                        Color color = b.getColor();
                        redSlider.setValue(color.r);
                        greenSlider.setValue(color.g);
                        blueSlider.setValue(color.b);
                    }

                }

            }
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(!saving) {
            title.keyboardInput(key, c);
            if (key == Input.KEY_E && !title.isTyping()) {
                try {
                    toggleMode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        super.mouseDragged(oldx, oldy, newx, newy);
        if(!saving) {
            redSlider.updateSlider(newx, newy, true);
            greenSlider.updateSlider(newx, newy, true);
            blueSlider.updateSlider(newx, newy, true);
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

        super.mouseMoved(oldx, oldy, newx, newy);
        if(!saving) {
            back.playHovered(newx, newy);
            save.playHovered(newx, newy);
            Integer relX = grid.getRelativeX(newx);
            Integer relY = grid.getRelativeY(newy);
            if (relX != null && relY != null) {
                selectX = relX;
                selectY = relY;
                drawSelection = true;
            } else {
                drawSelection = false;
            }
            redSlider.updateSlider(newx, newy, false);
            greenSlider.updateSlider(newx, newy, false);
            blueSlider.updateSlider(newx, newy, false);
        }
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);
    }
    public void toggleMode() throws SlickException {
        switch(mode){
            case 0:
                selection = new Image("res/images/EndLoc_Block.png");
                modeText.setText("Selection Mode: Rotation Point");
                mode=1;
                break;
            case 1:
                selection = new Image("res/images/EndLoc_Block.png");
                modeText.setText("Selection Mode: Get Color" );
                mode=2;

                break;
            case 2:
                selection = new Image("res/images/Block.png");
                modeText.setText("Selection Mode: Structure");
                mode = 0;
                break;
        }

    }
    public void savePiece()  {
        try {
            saving = true;
            List<BlockJson> list = new ArrayList<>();
            for (Block[] b1 : grid.getBlockArr()) {
                for (Block b : b1) {
                    if(b != null){
                        list.add(new BlockJson(b.getX(), b.getY(), b.getColor().r, b.getColor().g, b.getColor().b, midX == b.getX() && midY == b.getY()));
                    }
                }
            }
            if(!list.isEmpty()) {
                File file = new File("res/Pieces/" + title.getText() + ".json");

                file.createNewFile();
                FileWriter writer = new FileWriter("res/Pieces/" + title.getText() + ".json");


                Gson gson = new Gson();
                writer.write(gson.toJson(list));
                writer.close();
                System.out.println(JSONReader.toJsonString(file));

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
