package com.jornrigter.holes;

import com.jornrigter.Player;

public class CollectionHole extends Hole {

    public CollectionHole(Player player, int holeNumber) {
        super(player, holeNumber);
    }

    @Override
    public boolean canAcceptSeed(Player player) {
        return this.player == player;
    }
}
