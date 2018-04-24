package ru.sharadze;

import java.io.Serializable;

public class Board implements Serializable {


    private Cell pole[][];

    public Board(int size) {
        size += 2;
        pole = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pole[i][j] = new Cell();
            }
        }

    }

    public Cell[][] getPole() {
        return pole;
    }

    public void set(int x, int y, Cell.TYPE value) {
        pole[x][y].setValue(value);
    }

    public void addBorder(int x, int y, BorderType type, boolean player1) {
        Cell cell = pole[x][y];
        Border.PlayerType player = player1 ? Border.PlayerType.PLAYER_1 : Border.PlayerType.PLAYER_2;
        if(cell.getBorder()[type.getValue()].isActive())
            return;
        cell.addBorder(type.getValue(), player);
        if (type.equals(BorderType.TOP)) {
            pole[x][y + 1].addBorder((type.getValue() + 2) % 4, player);
        } else if (type.equals(BorderType.BOTTOM)) {
            pole[x][y - 1].addBorder((type.getValue() + 2) % 4, player);
        } else if (type.equals(BorderType.LEFT)) {
            pole[x - 1][y].addBorder((type.getValue() + 2) % 4, player);
        } else if (type.equals(BorderType.RIGHT)) {
            pole[x + 1][y].addBorder((type.getValue() + 2) % 4, player);
        }
        this.check(x, y, player);
    }

    public void check(int x, int y, Border.PlayerType player) {
        Cell.TYPE cell = Border.PlayerType.PLAYER_1.equals(player)?Cell.TYPE.CROSS :Cell.TYPE.ZERO;
        this.setSign(x, y, player, cell);
        this.setSign(x - 1, y, player, cell);
        this.setSign(x, y - 1, player, cell);
        this.setSign(x + 1, y, player, cell);
        this.setSign(x, y + 1, player, cell);
    }

    public void setSign(int x, int y, Border.PlayerType player, Cell.TYPE cell) {
        if (pole[x][y].fullOf(player)) {
            pole[x][y].setValue(cell);
        }
    }

    public int getSize() {
        return pole.length;
    }
}
