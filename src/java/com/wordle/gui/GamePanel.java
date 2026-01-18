package com.wordle.gui;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Font;

public abstract class GamePanel extends JPanel {
    
    public GamePanel(java.awt.Color color, Boolean isVisible){
        super.setBackground(color);
        super.setVisible(isVisible);
    }

    protected <T extends JComponent> void applyCommonStyle(T component) {
        component.setFocusable(false);
        component.setFont(new Font("Arial", Font.BOLD, 18));
    }

    public abstract void resetView();
}
