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
}
