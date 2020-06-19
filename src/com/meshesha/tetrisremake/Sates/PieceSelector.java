package com.meshesha.tetrisremake.Sates;

import com.meshesha.tetrisremake.Drawables.Button;
import com.meshesha.tetrisremake.Drawables.PieceHolder;
import com.meshesha.tetrisremake.Drawables.Pieces.BasicPiece;
import com.meshesha.tetrisremake.Drawables.Pieces.Block;
import com.meshesha.tetrisremake.Drawables.TextUI;
import com.meshesha.tetrisremake.FileIO.JSONReader;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import com.meshesha.tetrisremake.Main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PieceSelector extends TetrisState {
    public static int ID = 7;
    @Override
    public int getID() {
        return ID;
    }
    List<BasicPiece> pieces;
    boolean isDoneLoading = false;
    boolean isSelecting = false;
    PieceHolder[] drawablePieces = new PieceHolder[8];
    int page = 1;
    int pieceSpacing = 50;
    int blockSize = 50;
    Button nextPage;
    Button prevPage;
    Button createNewPiece;
    Button back;
    Button selectPieces;

    List<BasicPiece> selectedPieces;
    TextUI loadingText;
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);
        isSelecting = false;
        isDoneLoading = false;
        selectedPieces = new ArrayList<>();
        File pieceDirectory = new File("res/Pieces");
        File[] files = pieceDirectory.listFiles();
        pieces = new ArrayList<>();
        for(int i = 0; i < files.length; i++){
            if(files[i].getName().endsWith(".json")){
                try {
                    BasicPiece p = JSONReader.toPiece(files[i]);
                    p.init(false);
                    int minX =4;
                    int minY = 0;
                    for(Block b : p.getStructure() ) {
                        if (b.getX() < minX) {
                            minX = b.getX();
                        }
                        if (b.getY() < minY) {
                            minY = b.getY();
                        }
                    }

                    p.moveDown(-minY);
                    p.setName(files[i].getName());
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
        back.setText("Back", 40, "AldoTheApache", Color.white);
        back.setClickedAction(() -> {
            game.enterState(MainMenu.ID, new PauseTransition(100), new EmptyTransition());
        });
        nextPage = new Button(1000, 800, 300, 70, 5);
        nextPage.setText("Next Page", 40, "AldoTheApache", Color.white);
        nextPage.setClickedAction(() -> {
            page++;
            updateDrawablePieces();

        });
        prevPage = new Button(600, 800, 300, 70, 5);
        prevPage.setText("Previous Page", 40, "AldoTheApache", Color.white);
        prevPage.setClickedAction(() -> {
            page--;
            updateDrawablePieces();

        });
        createNewPiece = new Button(100, 25, 300, 70, 5);
        createNewPiece.setText("Create New Piece", 40, "AldoTheApache", Color.white);
        createNewPiece.setClickedAction(() -> {
            game.enterState(PieceCreator.ID, new PauseTransition(100), new EmptyTransition());
        });
        selectPieces = new Button(400, 25, 300, 70, 5);
        selectPieces.setText("Select Pieces", 40, "AldoTheApache", Color.white);
        selectPieces.setClickedAction(() -> {
            isSelecting = true;
            selectPieces.setText("Finish Selecting", 40, "AldoTheApache", Color.white);
        }, ()->{
            isSelecting = false;
            selectPieces.setText("Select Pieces", 40, "AldoTheApache", Color.white);
        });
        loadingText = new TextUI(100, "Loading...", "FORCED SQUARE", Color.white);
        background = new Image("res/images/Background2_blurred.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        background.draw(0, 0, Main.getScreenWidth(), Main.getScreenHeight());
        if(!isDoneLoading){
            loadingText.draw(100, 200);
            System.out.println("Loading...");
        }else {
            for (int i = 0; i < drawablePieces.length; i++) {
                int pieceY = 150;
                if (i > 3) {
                    pieceY += pieceSpacing + blockSize*5;
                }
                if(drawablePieces[i] != null){
                    drawablePieces[i].drawBackground(g);

                    if(drawablePieces[i].getPiece().isSelected()){
                        Color rectColor = new Color(1f, 1f, 0f, 0.5f);
                        Rectangle rect = new Rectangle(200 + i%4*(blockSize*5+pieceSpacing), pieceY, blockSize*5, blockSize*5);
                        g.setColor(Color.black);
                        g.setLineWidth(5);
                        g.fill(rect, new GradientFill(0, 0, rectColor, 1920, 1080, rectColor));
                        g.draw(rect);
                        g.setColor(Color.white);
                        g.setLineWidth(1);
                    }

                    drawablePieces[i].draw(g);

                }


            }
            back.draw(g);
            if(page != 1){
                prevPage.draw(g);
            }
            if(page < Math.ceil(pieces.size()/8f)){
                nextPage.draw(g);
            }
            createNewPiece.draw(g);
            selectPieces.draw(g);

        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

    }
    public void updateDrawablePieces(){
        drawablePieces  = new PieceHolder[8];
        for(int i = 0; i < pieces.size()-(page-1)*8 && i < 8; i++){
            int pieceY = 150;
            BasicPiece p = pieces.get((page-1)*8 +i);
            if (i > 3) {
                pieceY += pieceSpacing + 5*blockSize;
            }
            drawablePieces[i] = new PieceHolder(200 + i%4*(blockSize*5+pieceSpacing), pieceY, blockSize, blockSize, p.getName());
            drawablePieces[i].setPiece(pieces.get((page-1)*8 +i));
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        super.mouseClicked(button, x, y, clickCount);
        back.playClicked();
        createNewPiece.playClicked();
        selectPieces.playClicked();
        if(page != 1) {
            prevPage.playClicked();
        }
        if(page < Math.ceil(pieces.size()/8f)) {
            nextPage.playClicked();
        }
        if(isSelecting){
            for (int i = 0; i < drawablePieces.length; i++){
                int pieceY = 100;
                if (i > 3) {
                    pieceY += pieceSpacing + 5*blockSize;
                }
                if(x >= 200 + i%4*(blockSize*5+pieceSpacing) && x<= 200 + i%4*((5*blockSize)+pieceSpacing) + 5* blockSize
                    &&  y >= pieceY && y <= pieceY + 5*blockSize){
                    drawablePieces[i].getPiece().setSelected(!drawablePieces[i].getPiece().isSelected());
                    if(drawablePieces[i].getPiece().isSelected()){
                        selectedPieces.add(drawablePieces[i].getPiece());
                    }else{
                        selectedPieces.remove(drawablePieces[i]);
                    }

                }
            }
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        super.mouseMoved(oldx, oldy, newx, newy);
        back.playHovered(newx, newy);
        prevPage.playHovered(newx, newy);
        createNewPiece.playHovered(newx, newy);
        selectPieces.playHovered(newx, newy);
        nextPage.playHovered(newx, newy);

    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        super.leave(container, game);
        init(container, game);
        if(!selectedPieces.isEmpty()) {
            BasicPiece[] arr = new BasicPiece[selectedPieces.size()];
            arr =  selectedPieces.toArray(arr);
            Main.selectedPieces = arr;
            System.out.println(selectedPieces.size());
        }else{
            Main.selectedPieces = null;
        }
    }
}
