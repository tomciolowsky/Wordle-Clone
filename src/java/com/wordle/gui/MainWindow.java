package com.wordle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import com.wordle.bridge.GameBridge;

public class MainWindow implements KeyboardListener {
    private JFrame frame;
    private int windowWidth=600;
    private int windowHeight=850;

    private KeyboardPanel keyboard;
    private BoardPanel board;
    private GameBridge bridge;

    private StringBuilder currentGuess;

    public MainWindow() {
        this.currentGuess = new StringBuilder();
        this.bridge = new GameBridge();
        this.bridge.startGame();

        initializeWindow();
    }

    public void initializeWindow(){
        frame = new JFrame();
        this.frame.setTitle("Wordle Clone");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(windowWidth, windowHeight);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setLayout(new BorderLayout());

        this.board = new BoardPanel(MyColor.WORDLE_WHITE, true);
        this.keyboard = new KeyboardPanel(MyColor.WORDLE_WHITE, true, this);

        this.frame.add(board, BorderLayout.CENTER);
        this.frame.add(keyboard, BorderLayout.SOUTH);
    }

    public void resetGame(){
        // C++ side
        bridge.startGame();

        // Java side
        board.resetView();
        keyboard.resetView();
        currentGuess.setLength(0);
    }

    public void ShowGameOverDialog(String message){
        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public void onKeyClicked(char key){
        if (currentGuess.length() < 5){
            currentGuess.append(key);
            updateBoardVisuals();
        }
    }
    @Override
    public void onEnterClicked(){
        if (currentGuess.length()==5){
            submitGuess();
        }
    }
    @Override
    public void onBackspaceClicked(){
        if (currentGuess.length() > 0){
            currentGuess.deleteCharAt(currentGuess.length()-1);
            updateBoardVisuals();
        }
    }

    private void updateBoardVisuals(){
        int row = board.getCurrentRowIndex();
        char[] letters = new char[5];
        int[] colors = new int[5];

        for (int i=0; i<5; i++){
            if (i<currentGuess.length()) {
                letters[i]=currentGuess.charAt(i);
            }
            else{
                letters[i]=' ';
            }
            colors[i]= -1;
        }
        board.updateRow(row, letters, colors);
    }

    private void submitGuess(){
        String guessStr = currentGuess.toString();

        int[] results = bridge.processGuess(guessStr);

        if (results[0]==-1) {
            JOptionPane.showMessageDialog(frame, "Invalid Characters!");
            return;
        }
        if (results[0]==-2) {
            // ROW ANIMATION
            final int originalX = frame.getLocation().x;
            final int originalY = frame.getLocation().y;

            Timer wiggleTimer = new Timer(50, new ActionListener(){
                int count=0;
                int offset=5;

                @Override
                public void actionPerformed(ActionEvent e){
                    offset *= -1;
                    frame.setLocation(originalX+offset, originalY);
                    count++;
                    if (count >= 6){
                        ((Timer)e.getSource()).stop();
                        frame.setLocation(originalX,originalY);
                    }
                }
            });

            wiggleTimer.start();
            
            JOptionPane.showMessageDialog(frame, "Word not in dictionary!");
            return;
        }

        // REVEAL ANIMATION
        int row = board.getCurrentRowIndex();
        char[] letters = guessStr.toCharArray();
        // board.updateRow(row, letters, results);
        board.animateRow(row, letters, results);
        for(int i=0; i<5; i++){
            char c = letters[i];
            int colorCode = results[i];

            Color color;
            switch (colorCode) {
                case 1:
                    color = MyColor.WORDLE_YELLOW;
                    break;
                case 2:
                    color = MyColor.WORDLE_GREEN;
                    break;
                default:
                    color = MyColor.WORDLE_DARKGRAY;
                    break;
            }
            keyboard.setKeyColor(c, color);

        }

        boolean isWin = true;
        for (int r : results) {
            if (r != 2) {
                isWin = false;
            }
        }

        if (isWin) {
            ShowGameOverDialog("YOU WON!");
            handleGameOver();
        }
        else if (board.getCurrentRowIndex() >= 5) {
            ShowGameOverDialog("GAME OVER!");
            handleGameOver();
        }
        else{
            board.nextRow();
            currentGuess.setLength(0);
        }
    }

    private void handleGameOver(){

        String stats = bridge.getStatistics();
        int option = JOptionPane.showConfirmDialog(
            frame,
            stats + "\n\nDo you want to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION){
            resetGame();
        }
        else{
            System.exit(0);
        }
    }
}
