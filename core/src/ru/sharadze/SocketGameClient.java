package ru.sharadze;

import java.util.UUID;

public class SocketGameClient implements IGameClient {

    private String gameId;
    private String playerId;

    public boolean connect() {
        return true;
    }

    public void startGame() {
        gameId = SocketClient.getInstance().invoke("startGame", String.class, id());
    }

    public int onlineCount() {
        return SocketClient.getInstance().invoke("onlineCount", Integer.class, gameId);
    }

    public Board get() {
        return SocketClient.getInstance().invoke("getBoard", Board.class, gameId);
    }

    public boolean addBorder(int x, int y, BorderType type) {
        return SocketClient.getInstance().invoke("addBorder", Boolean.class, x, y, type, id(), gameId);
    }

    public boolean isMyTurn() {
        return SocketClient.getInstance().invoke("isMyTurn", Boolean.class, id(), gameId);
    }

    private String id() {
        if (this.playerId == null)
            playerId = UUID.randomUUID().toString();
        return playerId;
    }
}
