package ru.sharadze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pole {
    private Cell[][] cells;
    public final float SIDE;
    public static final Color firstPlayer = new Color(66 / 255f, 27 / 255f, 179 / 255f, 1f);
    public static final Color secondPlayer = new Color(146 / 255f, 58 / 255f, 29 / 255f, 1f);
    private int size;
    private HelpLine helpLine;


    public Pole(Cell[][] cells) {
        this.cells = cells;
        this.size = cells.length;
        SIDE = MyGdxGame.WIDTH / (cells.length);
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void draw(ShapeRenderer sr) {
        Color lineColor = new Color(202 / 255f, 203 / 255f, 213 / 255f, 1f);
        sr.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(1);
        for (int i = 0; i < size - 1; i++) {
            sr.setColor(lineColor);
            sr.line(0, SIDE * (i + 1), MyGdxGame.WIDTH, SIDE * (i + 1));
            sr.line(SIDE * (i + 1), 0, SIDE * (i + 1), MyGdxGame.HEIGHT);
        }
        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(1);
        sr.setColor(Color.BLACK);
        sr.rect(SIDE, SIDE, MyGdxGame.WIDTH - SIDE * 2, MyGdxGame.HEIGHT - SIDE * 2);
        sr.end();


        sr.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(5);
        if (helpLine != null) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            int i = helpLine.getX();
            int j = helpLine.getY();
            Color b = Color.BLACK;
            b.a = 0.5f;
            sr.setColor(b);
            if (helpLine.getBorderType().equals(BorderType.TOP)) {
                sr.line(SIDE * i, SIDE * (j + 1), SIDE * (i + 1), SIDE * (j + 1));
            } else if (helpLine.getBorderType().equals(BorderType.BOTTOM)) {
                sr.line(SIDE * i, SIDE * j, SIDE * (i + 1), SIDE * j);
            } else if (helpLine.getBorderType().equals(BorderType.LEFT)) {
                sr.line(SIDE * i, SIDE * j, SIDE * i, SIDE * (j + 1));
            } else if (helpLine.getBorderType().equals(BorderType.RIGHT)) {
                sr.line(SIDE * (i + 1), SIDE * j, SIDE * (i + 1), SIDE * (j + 1));
            }
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(3);
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                Gdx.gl.glLineWidth(3);
                Cell cell = cells[i][j];
                if (cell.getValue().equals(Cell.TYPE.CROSS)) {
                    sr.setColor(firstPlayer);
                    sr.line(SIDE * (i + 0.1f), SIDE * (j + 0.1f), SIDE * (i + 0.9f), SIDE * (j + 0.9f));
                    sr.line(SIDE * (i + 0.9f), SIDE * (j + 0.1f), SIDE * (i + 0.1f), SIDE * (j + 0.9f));

                } else if (cell.getValue().equals(Cell.TYPE.ZERO)) {
                    sr.setColor(secondPlayer);
                    sr.circle(SIDE * (i + 0.5f), SIDE * (j + 0.5f), SIDE / 2.3f);
                }

                Gdx.gl.glLineWidth(5);
                Border topBorder = cell.getTopBorder();
                Border bottomBorder = cell.getBottomBorder();
                Border leftBorder = cell.getLeftBorder();
                Border rightBorder = cell.getRightBorder();
                if (topBorder.isActive()) {
                    this.setSRColor(sr, topBorder);
                    sr.line(SIDE * i, SIDE * (j + 1), SIDE * (i + 1), SIDE * (j + 1));
                }
                if (bottomBorder.isActive()) {
                    this.setSRColor(sr, bottomBorder);
                    sr.line(SIDE * i, SIDE * j, SIDE * (i + 1), SIDE * j);
                }
                if (leftBorder.isActive()) {
                    this.setSRColor(sr, leftBorder);
                    sr.line(SIDE * i, SIDE * j, SIDE * i, SIDE * (j + 1));
                }
                if (rightBorder.isActive()) {
                    this.setSRColor(sr, rightBorder);
                    sr.line(SIDE * (i + 1), SIDE * j, SIDE * (i + 1), SIDE * (j + 1));
                }
            }
        }
        sr.end();

    }

    public int getSize() {
        return size;
    }

    public void setSRColor(ShapeRenderer sr, Border border) {
        if (border.getPlayer().equals(Border.PlayerType.PLAYER_1)) {
            sr.setColor(firstPlayer);
        } else if (border.getPlayer().equals(Border.PlayerType.PLAYER_2)) {
            sr.setColor(secondPlayer);
        } else {
            sr.setColor(Color.BLACK);
        }
    }

    public void setHelpLine(HelpLine helpLine) {
        this.helpLine = helpLine;
    }

}
