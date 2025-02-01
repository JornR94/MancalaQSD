package com.jornrigter;

public class MancalaBoard {

    /**
     * Attempts to make the move for the given hole number.
     *
     * @param holeNumber the hole number chosen by one of the players
     * @throws InvalidMoveException thrown if an invalid hole is chosen
     */
    public void makeMove(int holeNumber) throws InvalidMoveException {

    }

    /**
     * @param holeNumber hole number for which to get the hole
     * @return the hole belonging to the given holeNumber
     */
    public Hole getHole(int holeNumber) {
        throw new IllegalArgumentException("Not implemented yet");
    }

    public boolean canPlayerMakeMove() {
        throw new IllegalArgumentException("Not implemented yet");
    }
}
