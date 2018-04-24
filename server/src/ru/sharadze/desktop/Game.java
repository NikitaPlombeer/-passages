package ru.sharadze.desktop;

import ru.sharadze.Board;
import ru.sharadze.BorderType;

public class Game {
    private Board board;
    private String player1;
    private String player2;
    private boolean player1Turn;

    public Game(String playerId) {
        this.player1 = playerId;
        board = new Board(10);
        player1Turn = true;
    }

    public Board getBoard() {
        return board;
    }

    public int onlineCount() {
        int p1 = player1 != null ? 1 : 0;
        int p2 = player2 != null ? 1 : 0;
        return p1 + p2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public boolean isMyTurn(String id) {
        return (player1.equals(id) && player1Turn) ||
                (player2.equals(id) && !player1Turn);
    }

    public boolean addBorder(int x, int y, BorderType type, String player) {
        if (player1Turn && player.equals(player1)) {
            board.addBorder(x, y, type, true);
            player1Turn = false;
        } else if (!player1Turn && player.equals(player2)) {
            board.addBorder(x, y, type, false);
            player1Turn = true;
        }
        return false;
    }
}
