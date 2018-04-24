package ru.sharadze.desktop;

import ru.sharadze.Board;
import ru.sharadze.BorderType;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameControllerImpl implements GameController {

    private ConcurrentHashMap<String, Game> gameTable = new ConcurrentHashMap<>();

    @Override
    public String startGame(String playerId) {
        System.out.println("Start game by " + playerId);
        for (String key : gameTable.keySet()) {
            Game game = gameTable.get(key);
            if (game.onlineCount() == 1) {
                game.setPlayer2(playerId);
                System.out.println("Find game [" + key + "] for " + playerId);
                return key;
            }
        }
        String gameId = UUID.randomUUID().toString();
        gameTable.put(gameId, new Game(playerId));
        System.out.println("Create new game [" + gameId + "] for " + playerId);
        return gameId;
    }

    @Override
    public int onlineCount(String gameId) {
        int onlineCount = gameTable.get(gameId).onlineCount();
        System.out.println("Online count: " + onlineCount);
        return onlineCount;
    }

    @Override
    public Board getBoard(String gameId) {
        return gameTable.get(gameId).getBoard();
    }

    @Override
    public boolean isMyTurn(String id, String gameId) {
        boolean myTurn = gameTable.get(gameId).isMyTurn(id);
        System.out.println(
                String.format("Is my turn asked by [%s] for game [%s] result[%b]", id, gameId, myTurn));
        return myTurn;
    }

    @Override
    public boolean addBorder(int x, int y, BorderType type, String id, String gameId) {
        return gameTable.get(gameId).addBorder(x, y, type, id);
    }
}
