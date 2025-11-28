package com.wordle.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

public class KeyboardPanel extends GamePanel {
    
    private Map<Character, JButton> keyMap = new HashMap<>();
    private KeyboardListener listener;

    public KeyboardPanel(java.awt.Color color, Boolean isVisible, KeyboardListener listener){
        super(color, isVisible);
        this.listener = listener;
        this.setLayout(new GridLayout(3,1));
        

        String row1Keys = "QWERTYUIOP";
        String row2Keys = "ASDFGHJKL";
        String row3Keys = "*ZXCVBNM#"; // *=ENTER, #=BACK
        
        this.add(createRowPanel(row1Keys));
        this.add(createRowPanel(row2Keys));
        this.add(createRowPanel(row3Keys));
    }

    // Helper method to create 3 "SubPanels"
    private JPanel createRowPanel(String keys){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for (int i=0; i<keys.length(); i++){
            char letter = keys.charAt(i);
            JButton button = createButton(letter);
            panel.add(button);
            if (letter != '*' && letter != '#'){
                keyMap.put(letter,button);
            }
        }
        panel.setBackground(MyColor.WORDLE_WHITE);
        return panel;
    }

    // Helper method to create a Button object
    private JButton createButton(char letter){
        JButton button;
        switch (letter) {
            case '*': // ENTER
                button = new JButton("ENTER");
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        listener.onEnterClicked();
                    }
                });
                break;

            case '#': // BACK
                button = new JButton("⌫");
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        listener.onBackspaceClicked();
                    }
                });
                break;
        
            default:
                button = new JButton(String.valueOf(letter));
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        listener.onKeyClicked(letter);
                    }
                });
                break;
        }
        button.setBackground(MyColor.WORDLE_LIGHTGRAY);
        button.setForeground(MyColor.WORDLE_BLACK);
        button.setFocusPainted(false);
        return button;
    }

    public void disableKey(char key){
        if (keyMap.containsKey(key)){
            keyMap.get(key).setEnabled(false);
        }
    }

    public void setKeyColor(char key, java.awt.Color color){
        if (keyMap.containsKey(key)){
            keyMap.get(key).setBackground(color);
            keyMap.get(key).setForeground(MyColor.WORDLE_WHITE);
            keyMap.get(key).setOpaque(true);
        }
    }

    @Override
    public void resetView(){
        for (JButton button : keyMap.values()){
            button.setEnabled(true);
            button.setBackground(MyColor.WORDLE_LIGHTGRAY);
            button.setForeground(MyColor.WORDLE_BLACK);
        }
    }
}
