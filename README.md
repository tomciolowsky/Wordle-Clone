# Wordle Clone (Java + C++)

A Wordle clone created for my Object Oriented Programming course.
It uses **Java (Swing)** for the GUI and **C++** for the game logic, connected via **JNI**.

## Project Structure
* `src/java`: GUI and JNI Bridge
* `src/cpp`: Game Logic, Statistics, and Word Dictionary
* `assets`: Dictionary file

## Prerequisites
1.  **Java JDK** (version 8 or higher)
2.  **G++ Compiler** (MinGW or similar)
3.  Ensure the `JAVA_HOME` environment variable is set to your JDK path.

## How to Run (Windows)
1.  Double-click `build.bat` to compile the project.
2.  Double-click `run.bat` to start the game.

## Features
* Full Wordle game logic (Green/Yellow/Grey).
* Statistics tracking (Wins/Losses) saved to file.
* "Play Again" functionality.