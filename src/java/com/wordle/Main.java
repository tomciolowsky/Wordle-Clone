package com.wordle;

import javax.swing.SwingUtilities;
import com.wordle.gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("      Starting Wordle Project     ");
        System.out.println("==================================");

        // Create MainWindow
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MainWindow();
            }
        });
    }
}