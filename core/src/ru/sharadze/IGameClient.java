package ru.sharadze;

import java.util.UUID;

public interface IGameClient {
    boolean connect();

    void startGame();

    int onlineCount();

    Board get();

    boolean addBorder(int x, int y, BorderType type);

    boolean isMyTurn();

}
