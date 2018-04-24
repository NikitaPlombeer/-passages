package ru.sharadze.desktop;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import ru.sharadze.Passages.GameHelper;
import ru.sharadze.desktop.corba.GameImpl;
import ru.sharadze.Passages.Game;

import java.util.Arrays;

public class Server {
	public static void main (String[] arg) {
		GameController gameController = new GameControllerImpl();
		if(arg.length == 0 || arg[0].equals("SOCKET")) {
			socket(gameController);
		} else {
			corba(Arrays.copyOfRange(arg, 1, arg.length), gameController);
		}
	}

	public static void socket(GameController gameController) {
		GameServer gameServer = new GameServer(gameController);
		gameServer.startServer();
	}

	public static void corba(String[] args, GameController gameController) {
		try {
			// Создаем и инициализируем экземпляр ORB
			ORB orb = ORB.init(args, null);

			// Получаем доступ к Root POA и активируем POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// Создаем объект сервант и регистрируем в нем объект ORB
			GameImpl helloImpl = new GameImpl(gameController);
			helloImpl.setORB(orb);

			// Получаем доступ к объекту серванта
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
			Game href = GameHelper.narrow(ref);

			// Получаем корневой контекст именования
			org.omg.CORBA.Object objRef =
					orb.resolve_initial_references("NameService");
			// NamingContextExt является частью спецификации Interoperable
			// Naming Service (INS)
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// Связывание идентификатора "Passages" и объекта серванта
			String name = "Passages";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);

			System.out.println("GameServer готов и ждет обращений ...");

			// Ожидание обращений клиентов
			orb.run();
		} catch (Exception e) {
			System.err.println("ОШИБКА: " + e);    // Выводим сообщение об ошибке
			e.printStackTrace(System.out);    // Выводим содержимое стека вызовов
		}

		System.out.println("GameServer работу завершил ...");
	}
}
