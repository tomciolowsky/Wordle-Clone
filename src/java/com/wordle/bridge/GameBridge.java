package com.wordle.bridge;

public class GameBridge {

    // 1. Load the "WordleLogic.dll" that we will build
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
    // We will call this to test the connection
    public native void testConnection();

    public native void startGame();
    public native int[] checkGuess(String guess);
}