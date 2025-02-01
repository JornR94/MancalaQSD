package com.jornrigter;

public enum Player {

    PLAYER_1,
    PLAYER_2;

    /**
     * @return this player's opponent
     */
    public Player getOpponent() {
        return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
    }

    @Override
    public String toString() {
        return "Player " + (this == PLAYER_1 ? "1" : "2");
    }

    /**
     * @return the holes that this player can select in the CLI
     */
    public String getPlayableHoles() {
        return this == PLAYER_1 ? "1-6" : "8-13";
    }
}
