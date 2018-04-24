package ru.sharadze.desktop;

import ru.sharadze.Board;
import ru.sharadze.BorderType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final GameController gameController;

    ClientHandler(Socket clientSocket, GameController gameController) {
        this.clientSocket = clientSocket;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        System.out.println("Got a client !");

        ObjectInputStream in;
        try {
            in = new ObjectInputStream(this.clientSocket.getInputStream());
            Object input = in.readObject();
            if (input != null) {
                System.out.println("Request from client received!");
                if (input.equals("startGame")) {
                    String id = gameController.startGame((String) in.readObject());
                    this.write(id);
                } else if (input.equals("onlineCount")) {
                    int count = gameController.onlineCount((String) in.readObject());
                    this.write(count);
                } else if (input.equals("getBoard")) {
                    Board board = gameController.getBoard((String) in.readObject());
                    this.write(board);
                } else if (input.equals("addBorder")) {
                    int x = (int) in.readObject();
                    int y = (int) in.readObject();
                    BorderType type = (BorderType) in.readObject();
                    String playerId = (String) in.readObject();
                    String gameId = (String) in.readObject();
                    boolean can = gameController.addBorder(x, y, type, playerId, gameId);
                    this.write(can);
                } else if (input.equals("isMyTurn")) {
                    boolean can = gameController.isMyTurn((String) in.readObject(), (String) in.readObject());
                    this.write(can);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void write(Object o) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(o);
        out.flush();
    }
}