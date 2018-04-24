package ru.sharadze;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import ru.sharadze.Passages.Game;
import ru.sharadze.Passages.GameHelper;

import java.io.IOException;
import java.util.UUID;

public class CorbaGameClient implements IGameClient {

    private String gameId;
    private String playerId;
    private Game passages;

    @Override
    public boolean connect() {
        try {
            // Создание и инициализация ORB
            ORB orb = ORB.init(new String[]{}, null);

            // Получение корневого контекста именования
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // NamingContextExt является частью спецификации Interoperable
            // Naming Service (INS)
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Получение доступа к серверу по его имени
            String name = "Passages";
            passages = GameHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Получен доступ к объекту " + passages);

            return true;

        } catch (Exception e) {
            System.out.println("ОШИБКА : " + e);
            e.printStackTrace(System.out);
        }
        return false;
    }

    @Override
    public void startGame() {
        gameId = passages.startGame(id());
    }

    @Override
    public int onlineCount() {
        return passages.onlineCount(gameId);
    }

    @Override
    public Board get() {
        try {
            return Utils.deserialize(passages.get(gameId), Board.class);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addBorder(int x, int y, BorderType type) {
        return passages.addBorder(x, y, BorderType.convert(type), id(), gameId);
    }

    @Override
    public boolean isMyTurn() {
        return passages.isMyTurn(id(), gameId);
    }

    private String id() {
        if (this.playerId == null)
            playerId = UUID.randomUUID().toString();
        return playerId;
    }
}
