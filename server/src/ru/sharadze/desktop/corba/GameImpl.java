package ru.sharadze.desktop.corba;

import org.omg.CORBA.ORB;
import ru.sharadze.Passages.BorderType;
import ru.sharadze.Passages.GamePOA;
import ru.sharadze.Utils;
import ru.sharadze.desktop.GameController;

import java.io.IOException;

public class GameImpl extends GamePOA {
    private GameController gameController;
    private org.omg.CORBA.ORB ORB;

    public GameImpl(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public String startGame(String id) {
        return gameController.startGame(id);
    }

    @Override
    public int onlineCount(String gameId) {
        return gameController.onlineCount(gameId);
    }

    @Override
    public int[] get(String gameId) {
        try {
            return Utils.serialize(gameController.getBoard(gameId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isMyTurn(String id, String gameId) {
        return gameController.isMyTurn(id, gameId);
    }

    @Override
    public boolean addBorder(int x, int y, BorderType type, String id, String gameId) {
        return gameController.addBorder(x, y, ru.sharadze.BorderType.convert(type), id, gameId);
    }

    public void setORB(ORB ORB) {
        this.ORB = ORB;
    }

    public ORB getORB() {
        return ORB;
    }
}
