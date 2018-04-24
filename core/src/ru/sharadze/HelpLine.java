package ru.sharadze;

public class HelpLine {
    private int x;
    private int y;
    private BorderType borderType;

    public HelpLine(int x, int y, BorderType borderType) {
        this.x = x;
        this.y = y;
        this.borderType = borderType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BorderType getBorderType() {
        return borderType;
    }
}
