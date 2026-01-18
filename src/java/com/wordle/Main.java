package com.wordle;

import javax.swing.SwingUtilities;
import com.wordle.gui.LanguageDialog;
import com.wordle.gui.MainWindow;


public class Main {
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("      Starting Wordle Project     ");
        System.out.println("==================================");

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                LanguageDialog launcher = new LanguageDialog();
                launcher.setVisible(true); 
            
                String dictPath = launcher.getSelectedDictionary();

                if (dictPath == null) {
                    System.out.println("No language selected. Exiting.");
                    System.exit(0);
                }
                
                new MainWindow(dictPath);
            }
        });
    }
}