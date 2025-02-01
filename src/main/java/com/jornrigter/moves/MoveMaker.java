package com.jornrigter.moves;

import com.jornrigter.Player;
import com.jornrigter.holes.CollectionHole;
import com.jornrigter.holes.Hole;
import com.jornrigter.holes.HoleSelector;
import com.jornrigter.holes.SelectableHole;

import java.util.List;

public class MoveMaker {

    /**
     * @param hole the selected hole to make the move with
     * @param player the player whose turn it is
     * @param board the Mancala board representation
     * @return the player who's turn it is after making the move
     */
    public static Player makeMove(Hole hole, Player player, List<Hole> board) {
        Hole currentHole = hole;
        // Take as many steps as the number of seeds in the selected hole allows.
        for (int seeds = hole.getSeeds(); seeds > 0; seeds--) {
            if (!currentHole.canAcceptSeed(player)) {
                // Can't accept current player's seeds: increase seeds by one to end in the right hole
                seeds++;
            } else {
                // Move to the next hole and add a seed
                currentHole = currentHole.getNextHole();
                currentHole.addSeeds(1);
            }
        }
        hole.clearSeeds();
        return checkEndState(currentHole, player, board);
    }

    /**
     * Checks the end state based on the end hole and performs the appropriate actions based on that (i.e. checks for
     * another turn by the same player through ending in the own player's collection hole, and checks if end hole is
     * empty and from own player, taking all the seeds from the opponent's opposite hole)
     *
     * @param endHole the hole this turn ends in
     * @param player current player
     * @param board mancala board representation
     *
     * @return the next player whose turn it is
     */
    private static Player checkEndState(Hole endHole, Player player, List<Hole> board) {
        if (endHole instanceof CollectionHole && endHole.getPlayer() == player) {
            // If ended up in player's collection hole, play again
            return player;
        } else if (endHole instanceof SelectableHole && endHole.getPlayer() == player && endHole.getSeeds() == 1) {
            // If ended in own player's empty selectable hole (which would contain one seed now), the player wins all of
            // the seeds from the opposite player
            Hole oppositeHole = HoleSelector.getOppositeHole(endHole, board);
            Hole collectionHole = HoleSelector.getCollectionHole(player, board);
            // Take the seeds and move them
            collectionHole.addSeeds(oppositeHole.getSeeds());
            collectionHole.addSeeds(endHole.getSeeds());
            oppositeHole.clearSeeds();
            endHole.clearSeeds();
        }
        return player.getOpponent();
    }
}
