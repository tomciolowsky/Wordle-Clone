package com.wordle;

import java.util.Arrays;

import com.wordle.bridge.GameBridge;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("      Starting Wordle Project     ");
        System.out.println("==================================");

        // Create the bridge
        GameBridge bridge = new GameBridge();

        // FOR DEBUG
        // Call the C++ function
        // System.out.println("[Java] Calling C++ now...");
        // bridge.testConnection();
        
        // 1.Start Game
        bridge.startGame();

        // 2.Test guess
        String myGuess = "APPLE";
        System.out.println("Guessing: " + myGuess);

        int[] result = bridge.checkGuess(myGuess);

        System.out.println("Result: " + Arrays.toString(result));
        System.out.println("(2=Green, 1=Yellow, 0=Grey)");

        // System.out.println("[Java] Back in main. Exiting.");
    }
}