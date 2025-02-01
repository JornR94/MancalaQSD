package com.jornrigter.holes;

import com.jornrigter.Player;

/**
 * Abstract hole class, to extend functionality from.
 */
public abstract class Hole {

    /**
     * Number of seeds in this hole
     */
    private int seeds;

    /**
     * The next hole in the chain (moving counter-clockwise)
     */
    private Hole nextHole;

    protected final Player player;

    /**
     * The hole number of this hole.
     */
    protected final int holeNumber;

    public Hole(Player player, int holeNumber) {
        this.player = player;
        this.holeNumber = holeNumber;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * @return the number of seeds in this hole
     */
    public int getSeeds() {
        return seeds;
    }

    /**
     * Since Mancala always goes counter-clockwise, there's only one next hole on the board.
     * @return the next hole on the board.
     */
    public Hole getNextHole() {
        return nextHole;
    }

    public void setNextHole(Hole hole) {
        this.nextHole = hole;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    /**
     * Adds the given seed to this hole
     * @param seeds number of seeds to add
     */
    public void addSeeds(int seeds) {
        this.seeds += seeds;
    }

    /**
     * Clears this hole's seeds
     */
    public void clearSeeds() {
        this.seeds = 0;
    }

    /**
     * Collection holes can only accept seeds from one player.
     *
     * @param player player whose seed to accept or not
     * @return true if this hole can accept a seed from the given player, false otherwise
     */
    public abstract boolean canAcceptSeed(Player player);
}
