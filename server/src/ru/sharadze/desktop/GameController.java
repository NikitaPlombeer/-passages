package ru.sharadze.desktop;

import ru.sharadze.Board;
import ru.sharadze.BorderType;

public interface GameController {
    String startGame(String playerId);
    int onlineCount(String gameId);
    Board getBoard(String gameId);
    boolean isMyTurn(String id, String gameId);
    boolean addBorder(int x, int y, BorderType type, String playerId, String gameId);
}
