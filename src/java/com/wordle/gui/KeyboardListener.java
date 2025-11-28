package com.wordle.gui;

public interface KeyboardListener {
    void onKeyClicked(char key);
    void onEnterClicked();
    void onBackspaceClicked();
}
