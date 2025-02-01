package com.jornrigter.holes;
import com.jornrigter.Player;


public class SelectableHole extends Hole {

    private final int DEFAULT_START_SEEDS = 4;

    public SelectableHole(Player player, int holeNumber) {
        super(player, holeNumber);
        this.addSeeds(DEFAULT_START_SEEDS);
    }

    @Override
    public boolean canAcceptSeed(Player player) {
        return true;
    }
}
