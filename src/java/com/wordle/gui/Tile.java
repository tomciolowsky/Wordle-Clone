package com.wordle.gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;


public class Tile extends javax.swing.JLabel {
    private char character;
    private int colorCode; // 0=grey, 1=yellow, 2=green

    public Tile(){
        super();
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBorder(BorderFactory.createLineBorder(MyColor.WORDLE_LIGHTGRAY, 2));
        this.setFont(new Font("Arial", Font.BOLD, 30));
        this.setBackground(MyColor.WORDLE_WHITE);
        this.setForeground(MyColor.WORDLE_BLACK);
    }

    public void setContent(char c, int colorc){
    
        switch (colorc) {
            case 0:
                this.setBackground(MyColor.WORDLE_DARKGRAY);
                this.setForeground(MyColor.WORDLE_WHITE);
                break;
            case 1:
                this.setBackground(MyColor.WORDLE_YELLOW);
                this.setForeground(MyColor.WORDLE_WHITE);
                break;
            case 2:
                this.setBackground(MyColor.WORDLE_GREEN);
                this.setForeground(MyColor.WORDLE_WHITE);
                break;
            default:
                this.setBackground(MyColor.WORDLE_WHITE);
                this.setForeground(MyColor.WORDLE_BLACK);
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
}
