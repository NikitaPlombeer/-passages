package ru.sharadze;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient {
    private static SocketClient instance;
    private static final String host = "localhost";
    private static final int port = 2550;
//    private final ExecutorService serverProcessingPool = Executors.newFixedThreadPool(10);

    private SocketClient() {
    }

    public static SocketClient getInstance() {
        SocketClient localInstance = instance;
        if (localInstance == null) {
            synchronized (SocketClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SocketClient();
                }
            }
        }
        return localInstance;
    }


    public synchronized <T> T invoke(String method, Class<T> r, Object... args) {
        // Opening Connection based on the port number 80(HTTP) and 443(HTTPS)
        Socket clientSocket = null;
        ObjectOutputStream request = null;
        ObjectInputStream response = null;
        try {
            clientSocket = new Socket(host, port);
            // Declare a writer to this url
            request = new ObjectOutputStream(clientSocket.getOutputStream());
            request.writeObject(method);
            for (Object arg : args) {
                request.writeObject(arg);
            }
            request.flush();
            response = new ObjectInputStream(clientSocket.getInputStream());
            return r.cast(response.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (request != null) request.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
