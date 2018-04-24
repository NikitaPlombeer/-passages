package ru.sharadze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	public enum Connection {
		CORBA, SOCKET
	}
	private Connection connection;

	public MyGdxGame(String connection) {
		this.connection = Connection.valueOf(connection);
	}

    public enum State {
		WAITING, GAME
	}
	public static int WIDTH;
	public static int HEIGHT;

	private SpriteBatch batch;
	private Sprite clockImage;
	private State state;
	private IGameClient gameClient;
	private Pole pole;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		Texture texture = new Texture("clock.png");
		int h = texture.getHeight();
		int w = texture.getWidth();
		clockImage = new Sprite(texture);
		float newWidth = WIDTH / 4f;
		clockImage.setSize(newWidth, newWidth * h / w);
		clockImage.setPosition(WIDTH / 2f - clockImage.getWidth() / 2f, HEIGHT / 2f - clockImage.getHeight() / 2f);


		if(connection.equals(Connection.CORBA)) {
			gameClient = new CorbaGameClient();
		} else {
			gameClient = new SocketGameClient();
		}
		if (!gameClient.connect()) {
			System.out.println("Подключение не удалось");
			Gdx.app.exit();
		}

		gameClient.startGame();
		state = State.WAITING;

		shapeRenderer = new ShapeRenderer();

		Gdx.input.setInputProcessor(this);

	}
	private float deltaSum;

	@Override
	public void render () {
		if(state == State.WAITING) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			int online = gameClient.onlineCount();
			if (online == 0 || online == 1) {
				clockImage.draw(batch);
			} else if (online == 2) {
				state = State.GAME;
				pole = new Pole(gameClient.get().getPole());
				deltaSum = 0;
			}
			batch.end();
		} else if (state == State.GAME) {
			Gdx.gl.glClearColor(234 / 255f, 234/ 255f, 234/ 255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			pole.draw(shapeRenderer);
			deltaSum += Gdx.graphics.getDeltaTime();
			if(deltaSum >= 0.3f) {
				deltaSum = 0;
				this.update();
			}
		}
	}

	private void update() {
		pole.setCells(gameClient.get().getPole());
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(state.equals(State.WAITING))
			return false;

		int x = screenX / (int) pole.SIDE;
		int y = (HEIGHT - screenY) / (int) pole.SIDE;

		float dx = screenX - x * pole.SIDE;
		float dy = (HEIGHT - screenY) - y * pole.SIDE;
		float md = pole.SIDE / 6f;

		boolean result = false;
		if(dx < md) {
			result = gameClient.addBorder(x, y, BorderType.LEFT);
		} else if(dy < md) {
			result = gameClient.addBorder(x, y, BorderType.BOTTOM);
		} else if(dx > pole.SIDE - md) {
			result = gameClient.addBorder(x, y, BorderType.RIGHT);
		}else if(dy > pole.SIDE - md) {
			result = gameClient.addBorder(x, y, BorderType.TOP);
		}
		if(result) {
			this.update();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		if(state.equals(State.WAITING))
			return false;
		int x = screenX / (int) pole.SIDE;
		int y = (HEIGHT - screenY) / (int) pole.SIDE;
		if(x == 0 || y == 0 || x == pole.getSize() - 1 || y == pole.getSize() - 1) {
			pole.setHelpLine(null);
			return false;
		}

		float dx = screenX - x * pole.SIDE;
		float dy = (HEIGHT - screenY) - y * pole.SIDE;
		float md = pole.SIDE / 6f;

		if(dx < md) {
			pole.setHelpLine(new HelpLine(x, y, BorderType.LEFT));
		} else if(dy < md) {
			pole.setHelpLine(new HelpLine(x, y, BorderType.BOTTOM));
		} else if(dx > pole.SIDE - md) {
			pole.setHelpLine(new HelpLine(x, y, BorderType.RIGHT));
		}else if(dy > pole.SIDE - md) {
			pole.setHelpLine(new HelpLine(x, y, BorderType.TOP));
		} else {
			pole.setHelpLine(null);
			return false;
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
