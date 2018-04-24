package ru.sharadze;

import java.io.Serializable;

public class Cell implements Serializable {

    public enum TYPE {
        EMPTY, CROSS, ZERO;

    }
    private TYPE value;
    private Border border[];
    public Cell() {
        this(TYPE.EMPTY);
    }

    public Cell(TYPE value) {
        this.value = value;
        border = new Border[4];
        for (int i = 0; i < border.length; i++) {
            border[i] = new Border();
        }
    }

    public void addBorder(int value, Border.PlayerType player) {
        border[value].setActive(true);
        border[value].setPlayer(player);
    }

    public Border getTopBorder() {
        return border[BorderType.TOP.getValue()];
    }

    public Border getBottomBorder() {
        return border[BorderType.BOTTOM.getValue()];
    }

    public Border getLeftBorder() {
        return border[BorderType.LEFT.getValue()];
    }

    public Border getRightBorder() {
        return border[BorderType.RIGHT.getValue()];
    }

    public boolean fullOf(Border.PlayerType player) {
        boolean full = true;
        for (Border b : border) {
            if(!b.isActive() || !b.getPlayer().equals(player)) {
                full = false;
            }
        }
        return full;
    }

    public void setValue(TYPE value) {
        this.value = value;
    }

    public TYPE getValue() {
        return value;
    }

    public Border[] getBorder() {
        return border;
    }
}
