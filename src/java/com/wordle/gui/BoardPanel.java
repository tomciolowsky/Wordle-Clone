package com.wordle.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BoardPanel extends GamePanel {
    private Tile[][] tiles;
    private int currentRowIndex;

    public BoardPanel(java.awt.Color color, Boolean isVisible){
        super(color, isVisible);
        this.setLayout(new GridLayout(6, 5, 5, 5));
        this.tiles = new Tile[6][5];
        this.currentRowIndex = 0;

        for (int row=0; row<6; row++){
            for (int col=0; col<5; col++){
                Tile newTile = new Tile();
                this.tiles[row][col] = newTile;
                this.add(newTile);
            }
        }

    }

    public void updateRow(int row, char[] letters, int[] colors){
        for (int col=0; col<5; col++){
            this.tiles[row][col].setContent(letters[col], colors[col]);
        }
    }

    public void animateRow(int row, char[] letters, int[] colors){
        
        Timer revealTimer = new Timer(200, new ActionListener() {
            int curTile=0;
            @Override
            public void actionPerformed(ActionEvent e){
                tiles[row][curTile].setContent(letters[curTile], colors[curTile]);
                curTile++;
                if (curTile >= 5){
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        revealTimer.start();
    }

    public int getCurrentRowIndex(){
        return this.currentRowIndex;
    }

    public void nextRow(){
        this.currentRowIndex++;
    }

    @Override
    public void resetView(){
        for (int row=0; row<6; row++){
            for (int col=0; col<5; col++){
                this.tiles[row][col].setContent(' ', -1);
            }
        }
        this.currentRowIndex = 0;
    }
}
