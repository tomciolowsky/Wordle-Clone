package com.wordle.gui;

import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class Tile extends javax.swing.JLabel {
    private char character;
    private int colorCode;

    public Tile(){
        super();
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBorder(BorderFactory.createLineBorder(GameColor.LIGHT_GRAY.getColor(), 2));
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setBackground(GameColor.WHITE.getColor());
        this.setForeground(GameColor.BLACK.getColor());
    }

    public void setContent(char c, int colorc){
        switch (colorc) {
            case 0:
                this.setBackground(GameColor.GREY.getColor());
                this.setForeground(GameColor.WHITE.getColor());
                break;
            case 1:
                this.setBackground(GameColor.YELLOW.getColor());
                this.setForeground(GameColor.WHITE.getColor());
                break;
            case 2:
                this.setBackground(GameColor.GREEN.getColor());
                this.setForeground(GameColor.WHITE.getColor());
                break;
            default:
                this.setBackground(GameColor.WHITE.getColor());
                this.setForeground(GameColor.BLACK.getColor());
        }

        this.setText(String.valueOf(c));
        this.character = c;
        this.colorCode = colorc;
    }

    public char getCharacter(){
        return this.character;
    }
    public int getColorCode(){
        return this.colorCode;
    }

    // UNIT TEST
    public static void main(String[] args) {
        System.out.println("Running Unit Test for Tile...");
        
        Tile testTile = new Tile();
        testTile.setContent('A', 2);
        
        if (testTile.getCharacter() == 'A' && testTile.getBackground().equals(GameColor.GREEN.getColor())) {
            System.out.println("[PASS] Tile content set correctly.");
        } else {
            System.err.println("[FAIL] Tile content mismatch.");
        }
    }
}
