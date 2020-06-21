package com.meshesha.tetrisremake.sates;

import com.meshesha.tetrisremake.drawables.*;
import com.meshesha.tetrisremake.drawables.Pieces.Block;
import com.meshesha.tetrisremake.fileio.BlockJson;
import com.meshesha.tetrisremake.fileio.JSONReader;
import com.google.gson.Gson;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * State for creating pieces and saving them to a json file using the GSON library
 */
public class PieceCreator extends TetrisState {
    public static int ID = 6;
    private int selectX = 0;
    private int selectY = 0;
    private int mode;
    private int midX;
    private int midY;

    private float screenWidth;
    private float screenHeight;

    private Grid grid;
    private Button back;
    private Button save;

    private Image selection;
    private Image midpoint;

    private Slider redSlider;
    private Slider greenSlider;
    private Slider blueSlider;

    private TextUI redValue;
    private TextUI greenValue;
    private TextUI blueValue;
    private TextUI modeText;
    private TextBox title;

    private boolean drawSelection;
    private boolean saving;


    @Override
    public int getID() {
        return ID;
    }

    /*
     * Grid, Back button, Save button, Mode text, Rotation point, selection, background, and RGB sliders initialized
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        drawSelection = false;
        midX = 2;
        midY = 2;
        saving = false;
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();
        grid = new Grid(700, 100, 100, 100, 5, 5);
        back = new Button(600, 900, 300, 70, 5);
        back.setText("Back", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        back.setClickedAction(() -> game.enterState(PieceSelector.ID, new PauseTransition(100), new EmptyTransition()));
        save = new Button(1000, 900, 300, 70, 5);
        save.setText("Save and Exit", 40, "AldoTheApache", Color.white, container.getWidth(), container.getHeight());
        save.setClickedAction(() -> {
            savePiece();
            saving = false;
            game.enterState(PieceSelector.ID, new PauseTransition(100), new EmptyTransition());
        });

        modeText = new TextUI(20, "Selection Mode: Structure", "Square", Color.white, container.getWidth(), container.getHeight());
        title = new TextBox(800, 30, "Title", "Forced Square", 20, 40, Color.white, container.getWidth(), container.getHeight());
        mode = 0;


        midpoint = new Image("res/images/EndLoc_Block.png");
        midpoint.setImageColor(1.0f, 0.5f, 0f);
        redSlider = new Slider(1500, 100, 300, 10, 0, 1, 1);
        greenSlider = new Slider(1500, 150, 300, 10, 0, 1, 1);
        blueSlider = new Slider(1500, 200, 300, 10, 0, 1, 0);
        redValue = new TextUI(20, "Red: ", "Square", Color.white, container.getWidth(), container.getHeight());
        greenValue = new TextUI(20, "Red: ", "Square", Color.white, container.getWidth(), container.getHeight());
        blueValue = new TextUI(20, "Red: ", "Square", Color.white, container.getWidth(), container.getHeight());
        background = new Image("res/images/Background_blurred.png");
        selection = new Image("res/images/Block.png");
        selection.setImageColor(1f, 1f, 0f, 0.75f);
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        background.draw(0, 0, container.getWidth(), container.getHeight());
        grid.drawBackground(g, container.getWidth(), container.getHeight());
        grid.draw(container.getWidth(), container.getHeight());
        back.draw();
        title.draw(g);

        // Draw rotation point if exists

        if (midX >= 0 && midY >= 0) {
            midpoint.draw(container.getWidth() / 1920f * (grid.startX + midX * grid.blockWidth), container.getHeight() / 1080f * (grid.startY + midY * grid.blockHeight), container.getWidth() / 1920f * grid.blockWidth, container.getHeight() / 1080f * grid.blockHeight);
        }
        // Draw selection image to mouse if on grid

        if (drawSelection) {
            selection.draw(container.getWidth() / 1920f * (grid.startX + selectX * grid.blockWidth), container.getHeight() / 1080f * (grid.startY + selectY * grid.blockHeight), container.getWidth() / 1920f * grid.blockWidth, container.getHeight() / 1080f * grid.blockHeight);
        }
        redSlider.draw(g, container.getWidth(), container.getHeight());
        greenSlider.draw(g, container.getWidth(), container.getHeight());
        blueSlider.draw(g, container.getWidth(), container.getHeight());
        redValue.draw(1250, 100);
        greenValue.draw(1250, 150);
        blueValue.draw(1250, 200);
        modeText.draw(1250, 300);
        save.draw();
    }


    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (!saving) {
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
        }
    }

    /*
     * Mode 0 (Structure): Adds or removes a block from the grid
     * Mode 1 (Rotation Point): Adds, changes, or removes the rotation point from the grid
     * Mode 2 (Get Color): changes the color of the block that will be added in mode 0 to the current clicked block
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        if (!saving) {
            back.playClicked();
            redSlider.updateSlider(x, y, true, screenWidth, screenHeight);
            greenSlider.updateSlider(x, y, true, screenWidth, screenHeight);
            blueSlider.updateSlider(x, y, true, screenWidth, screenHeight);
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

    /*
     * Adds text to the title text box if typing
     */
    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if (!saving) {
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
        if (!saving) {
            redSlider.updateSlider(newx, newy, true, screenWidth, screenHeight);
            greenSlider.updateSlider(newx, newy, true, screenWidth, screenHeight);
            blueSlider.updateSlider(newx, newy, true, screenWidth, screenHeight);
        }
    }

    /*
     * gets the position on the grid that the mouse on if it exists
     */
    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

        super.mouseMoved(oldx, oldy, newx, newy);
        if (!saving) {
            back.playHovered(newx, newy, screenWidth, screenHeight);
            save.playHovered(newx, newy, screenWidth, screenHeight);
            Integer relX = grid.getRelativeX(newx, screenWidth);
            Integer relY = grid.getRelativeY(newy, screenHeight);
            if (relX != null && relY != null) {
                selectX = relX;
                selectY = relY;
                drawSelection = true;
            } else {
                drawSelection = false;
            }
            redSlider.updateSlider(newx, newy, false, screenWidth, screenHeight);
            greenSlider.updateSlider(newx, newy, false, screenWidth, screenHeight);
            blueSlider.updateSlider(newx, newy, false, screenWidth, screenHeight);
        }
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);
    }


    private void toggleMode() throws SlickException {
        switch (mode) {
            case 0:
                selection = new Image("res/images/EndLoc_Block.png");
                modeText.setText("Selection Mode: Rotation Point");
                mode = 1;
                break;
            case 1:
                selection = new Image("res/images/EndLoc_Block.png");
                modeText.setText("Selection Mode: Get Color");
                mode = 2;

                break;
            case 2:
                selection = new Image("res/images/Block.png");
                modeText.setText("Selection Mode: Structure");
                mode = 0;
                break;
        }

    }

    /*
     * Saves the piece to a JSON file using the Block Json Object as an outline
     */
    private void savePiece() {
        try {
            saving = true;
            List<BlockJson> list = new ArrayList<>();
            for (Block[] b1 : grid.getBlockArr()) {
                for (Block b : b1) {
                    if (b != null) {
                        list.add(new BlockJson(b.getX(), b.getY(), b.getColor().r, b.getColor().g, b.getColor().b, midX == b.getX() && midY == b.getY()));
                    }
                }
            }
            if (!list.isEmpty()) {
                File file = new File("res/Pieces/" + title.getText() + ".json");

                file.createNewFile();
                FileWriter writer = new FileWriter("res/Pieces/" + title.getText() + ".json");


                Gson gson = new Gson();
                writer.write(gson.toJson(list));
                writer.close();
                System.out.println(JSONReader.toJsonString(file));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
