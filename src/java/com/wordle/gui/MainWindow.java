package com.wordle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
    private String currentDictPath;

    public MainWindow(String dictPath) {

        if (!validateDictionaryFile(dictPath)) {
            System.err.println("Dictionary file not found: " + dictPath);
            System.exit(1);
        }

        this.currentDictPath = dictPath;
        this.currentGuess = new StringBuilder();
        this.bridge = new GameBridge();
        this.bridge.startGame(this.currentDictPath);

        initializeWindow();
    }

    private boolean validateDictionaryFile(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }

    public void initializeWindow(){
        frame = new JFrame();
        this.frame.setTitle("Wordle Clone");

        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                shutdown();
            }
        });

        this.frame.setSize(windowWidth, windowHeight);
        this.frame.setLocationRelativeTo(null);
        // this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setLayout(new BorderLayout());

        this.board = new BoardPanel(GameColor.WHITE.getColor(), true);
        this.keyboard = new KeyboardPanel(GameColor.WHITE.getColor(), true, this);

        this.frame.add(board, BorderLayout.CENTER);
        this.frame.add(keyboard, BorderLayout.SOUTH);
    }

    public void shutdown() {
        System.out.println("Shutting down application...");
        frame.dispose();
        System.exit(0);
    }

    public void resetGame(){
        // C++ side
        bridge.startGame(this.currentDictPath);

        // Java side
        board.resetView();
        keyboard.resetView();
        currentGuess.setLength(0);
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
        // ROW ANIMATION
        if (results[0]==-2) {
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
        board.animateRow(row, letters, results);
        for(int i=0; i<5; i++){
            Color c = GameColor.GREY.getColor();
            if (results[i] == 1) c = GameColor.YELLOW.getColor();
            if (results[i] == 2) c = GameColor.GREEN.getColor();
            
            keyboard.setKeyColor(letters[i], c);
        }

        boolean isWin = true;
        for (int r : results) {
            if (r != 2) {
                isWin = false;
            }
        }

        if (isWin) {
            handleGameOver("YOU WON!");
        }
        else if (board.getCurrentRowIndex() >= 5) {
            handleGameOver("GAME OVER!");
        }
        else{
            board.nextRow();
            currentGuess.setLength(0);
        }
    }

    private void handleGameOver(String msg){
        String stats = bridge.getStatistics();
        int option = JOptionPane.showConfirmDialog(
            frame,
            msg + "\n\n" + stats + "\n\nDo you want to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION){
            resetGame();
        }
        else{
            shutdown();
        }
    }
}
