package com.wordle.bridge;

public class GameBridge {

    // 1. Load the "WordleLogic.dll"
    // This code serves as loadLibrary(), using "static initialisation block" 
    static {
        System.out.println("[Java] Attempting to load library...");
        try {
            System.loadLibrary("WordleLogic");
            System.out.println("[Java] Library loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("[Java] CRITICAL ERROR: Could not load library.");
            System.err.println("Make sure 'WordleLogic.dll' is in the 'bin' folder.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    // 2. Declare a native method (implemented in C++)
    
    public native String getStatistics();
    public native void startGame();
    public native int[] processGuess(String guess);
}