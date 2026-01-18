package com.wordle.bridge;

/**
 * The JNI (Java Native Interface) Bridge.
 * This class acts as the intermediary between the Java GUI and the C++ Game Logic.
 */
public class GameBridge {

    static {
        System.out.println("[Java] Bridge: Attempting to load 'WordleLogic.dll'...");
        try {
            System.loadLibrary("WordleLogic");
            System.out.println("[Java] Bridge: Library loaded successfully!");
        }
        catch (UnsatisfiedLinkError e) {
            System.err.println("[Java] CRITICAL ERROR: Could not load library.");
            System.err.println("Make sure 'WordleLogic.dll' is in the 'bin' folder.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Initializes the C++ game engine with a specific dictionary file.
     * @param dictionaryPath Relative path to the .txt file (e.g., "assets/dictionary_en.txt")
     */
    public native void startGame(String dictionaryPath);

    /**
     * Sends a user's guess to C++ for evaluation.
     * @param guess The 5-letter word string.
     * @return int[] Array of 5 integers (0=Grey, 1=Yellow, 2=Green, -1=Invalid, -2=NotInDict)
     */
    public native int[] processGuess(String guess);

    /**
     * Retrieves game statistics (Wins, Losses, Streak) from C++.
     * @return Formatted String ready for display.
     */
    public native String getStatistics();
}