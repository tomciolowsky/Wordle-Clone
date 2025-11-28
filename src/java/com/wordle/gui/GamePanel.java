package com.wordle.gui;

public abstract class GamePanel extends javax.swing.JPanel {
    
    public GamePanel(java.awt.Color color, Boolean isVisible){
        super.setBackground(color);
        super.setVisible(isVisible);
    }

    public abstract void resetView();
}
