package com.wordle.gui;

import java.awt.Color;

public enum GameColor {
    GREEN(new Color(110, 170, 100)),
    YELLOW(new Color(200, 180, 90)),
    GREY(new Color(120, 125, 130)),
    LIGHT_GRAY(new Color(210, 215, 220)),
    WHITE(Color.WHITE),
    BLACK(Color.BLACK);

    private final Color color;

    GameColor(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return color;
    }
}