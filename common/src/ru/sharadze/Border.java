package ru.sharadze;

import java.io.Serializable;

public class Border implements Serializable{
    public enum PlayerType {
        PLAYER_1, PLAYER_2, COMMON
    }

    private boolean isActive = false;
    private PlayerType player = null;

    public Border(boolean isActive) {
        this.isActive = isActive;
    }

    public Border() {
    }

    public boolean isActive() {
        return isActive;
    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public PlayerType getPlayer() {
        return player;
    }

    public void setPlayer(PlayerType player) {
        this.player = player;
    }
}
