package com.meshesha.tetrisremake.sates;

import com.meshesha.tetrisremake.drawables.Button;
import com.meshesha.tetrisremake.drawables.PieceHolder;
import com.meshesha.tetrisremake.drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.drawables.Pieces.Block;
import com.meshesha.tetrisremake.drawables.TextUI;
import com.meshesha.tetrisremake.fileio.JSONReader;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import com.meshesha.tetrisremake.main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*
 * State for selecting custom pieces to be used by the Game state
 */
public class PieceSelector extends TetrisState {
    public static int ID = 5;

    @Override
    public int getID() {
        return ID;
    }

    private List<BasicPiece> pieces;
    private boolean isDoneLoading = false;
    private boolean isSelecting = false;
    private PieceHolder[] drawablePieces = new PieceHolder[8];
    private int page = 1;
    private int pieceSpacing = 50;
    private int blockSize = 50;
    private float screenWidth;
    private float screenHeight;
    private Button nextPage;
    private Button prevPage;
    private Button createNewPiece;
    private Button back;
    private Button selectPieces;

    private List<BasicPiece> selectedPieces;
    private TextUI loadingText;

    /*
     * On enter, it finds all of the .json files in the pieces folder and attempts to convert them into Pieces
     */
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        isSelecting = false;
        isDoneLoading = false;
        selectedPieces = new ArrayList<>();
        File pieceDirectory = new File("res/Pieces");
        File[] files = pieceDirectory.listFiles();
        pieces = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                try {
                    BasicPiece p = JSONReader.toPiece(file);
                    p.init(false);
                    int minX = 4;
                    int minY = 0;
                    for (Block b : p.getStructure()) {
                        if (b.getX() < minX) {
                            minX = b.getX();
                        }
                        if (b.getY() < minY) {
                            minY = b.getY();
                        }
                    }

                    p.moveDown(-minY);
                    p.setName(file.getName());
                    p.setSelected(false);
                    pieces.add(p);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        isDoneLoading = true;
        updateDrawablePieces();

    }



    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        back = new Button(800, 900, 300, 70, 5);
        screenWidth = container.getWidth();
        screenHeight = container.getHeight();
        back.setText("Back", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        back.setClickedAction(() -> game.enterState(MainMenu.ID, new PauseTransition(100), new EmptyTransition()));
        nextPage = new Button(1000, 800, 300, 70, 5);
        nextPage.setText("Next Page", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        nextPage.setClickedAction(() -> {
            page++;
            updateDrawablePieces();

        });
        prevPage = new Button(600, 800, 300, 70, 5);
        prevPage.setText("Previous Page", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        prevPage.setClickedAction(() -> {
            page--;
            updateDrawablePieces();

        });
        createNewPiece = new Button(100, 25, 300, 70, 5);
        createNewPiece.setText("Create New Piece", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        createNewPiece.setClickedAction(() -> game.enterState(PieceCreator.ID, new PauseTransition(100), new EmptyTransition()));
        selectPieces = new Button(400, 25, 300, 70, 5);
        selectPieces.setText("Select Pieces", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        selectPieces.setClickedAction(() -> {
            isSelecting = true;
            selectPieces.setText("Finish Selecting", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        }, () -> {
            isSelecting = false;
            selectPieces.setText("Select Pieces", 40, "AldoTheApache", Color.white, screenWidth, screenHeight);
        });
        loadingText = new TextUI(100, "Loading...", "FORCED SQUARE", Color.white, screenWidth, screenHeight);
        background = new Image("res/images/Background_blurred.png");
    }


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        background.draw(0, 0, screenWidth, screenHeight);

        if (!isDoneLoading) {
            loadingText.draw(100, 200);
            System.out.println("Loading...");
        } else {
            for (int i = 0; i < drawablePieces.length; i++) {
                int pieceY = 150;
                if (i > 3) {
                    pieceY += pieceSpacing + blockSize * 5;
                }
                if (drawablePieces[i] != null) {
                    drawablePieces[i].drawBackground(g);

                    //Makes piece background yellow when selected
                    if (drawablePieces[i].getPiece().isSelected()) {
                        Color rectColor = new Color(1f, 1f, 0f, 0.5f);
                        Rectangle rect = new Rectangle(screenWidth / 1920f * (200 + i % 4 * (blockSize * 5 + pieceSpacing)), screenHeight / 1080f * pieceY, screenWidth / 1920f * blockSize * 5, screenHeight / 1080f * blockSize * 5);
                        g.setColor(Color.black);
                        g.setLineWidth(5);
                        g.fill(rect, new GradientFill(0, 0, rectColor, screenWidth, screenHeight, rectColor));
                        g.draw(rect);
                        g.setColor(Color.white);
                        g.setLineWidth(1);
                    }

                    drawablePieces[i].draw();

                }


            }
            back.draw();
            //Shouldn't allow this to be shown on page 1 because there will never be page lower
            if (page != 1) {
                prevPage.draw();
            }
            //Shouldn't allow this to be shown on last page because there isn't a next page after a last page by definition
            if (page < Math.ceil(pieces.size() / 8f)) {
                nextPage.draw();
            }
            createNewPiece.draw();
            selectPieces.draw();

        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {

    }

    /*
     * Changes which pieces are displayed based on page
     */
    private void updateDrawablePieces() {
        drawablePieces = new PieceHolder[8];
        for (int i = 0; i < pieces.size() - (page - 1) * 8 && i < 8; i++) {
            int pieceY = 150;
            BasicPiece p = pieces.get((page - 1) * 8 + i);
            if (i > 3) {
                pieceY += pieceSpacing + 5 * blockSize;
            }
            drawablePieces[i] = new PieceHolder(200 + i % 4 * (blockSize * 5 + pieceSpacing), pieceY, blockSize, blockSize, p.getName(), screenWidth, screenHeight);
            drawablePieces[i].setPiece(pieces.get((page - 1) * 8 + i));
        }
    }


    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        back.playClicked();
        createNewPiece.playClicked();
        selectPieces.playClicked();
        //Shouldn't allow this to be clicked on page 1 because there will never be page lower
        if (page != 1) {
            prevPage.playClicked();
        }
        //Shouldn't allow this to be  clicked on last page because there isn't a next page after a last page by definition
        if (page < Math.ceil(pieces.size() / 8f)) {
            nextPage.playClicked();
        }
        if (isSelecting) {
            for (int i = 0; i < drawablePieces.length; i++) {
                int pieceY = 100;
                if (i > 3) {
                    pieceY += pieceSpacing + 5 * blockSize;
                }
                if (x >= screenWidth / 1920f * (200 + i % 4 * (blockSize * 5 + pieceSpacing)) && x <= screenWidth / 1920f * (200 + i % 4 * ((5 * blockSize) + pieceSpacing) + 5 * blockSize)
                        && y >= screenHeight / 1080f * (pieceY) && y <= screenHeight / 1080f * (pieceY + 5 * blockSize)) {
                    drawablePieces[i].getPiece().setSelected(!drawablePieces[i].getPiece().isSelected());
                    if (drawablePieces[i].getPiece().isSelected()) {
                        selectedPieces.add(drawablePieces[i].getPiece());
                    } else {
                        selectedPieces.remove(drawablePieces[i].getPiece());
                    }

                }
            }
        }
    }


    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);
        back.playHovered(newx, newy, screenWidth, screenHeight);
        prevPage.playHovered(newx, newy, screenWidth, screenHeight);
        createNewPiece.playHovered(newx, newy, screenWidth, screenHeight);
        selectPieces.playHovered(newx, newy, screenWidth, screenHeight);
        nextPage.playHovered(newx, newy, screenWidth, screenHeight);

    }

    /*
     * On Exit, it saves the selected pieces into an array to be used by the game state
     */
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);
        if (!selectedPieces.isEmpty()) {
            BasicPiece[] arr = new BasicPiece[selectedPieces.size()];
            arr = selectedPieces.toArray(arr);
            //This isn't the greatest way of storing variables between states but it works
            Main.selectedPieces = arr;
        } else {
            Main.selectedPieces = null;
        }
    }
}
