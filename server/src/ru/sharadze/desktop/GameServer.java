package ru.sharadze.desktop;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private static final int STANDARD_PORT = 2550;
    private static final int STANDARD_POOL_SIZE = 10;
    private final GameController gameController;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;

    public GameServer(GameController shapeController) {
        this.gameController = shapeController;
    }

    public void startServer() {
        startServer(STANDARD_PORT, STANDARD_POOL_SIZE);
    }

    public void startServer(int port, int poolSize) {
        openServerSocket(port);
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(poolSize);
        while(!isStopped()){
            Socket clientSocket;
            try {
                System.out.println("Waiting for clients to connect...");
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            clientProcessingPool.submit(new ClientHandler(clientSocket, gameController));
        }
        System.out.println("Server Stopped.") ;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + port, e);
        }
    }

}



