package com.jornrigter.moves;

import com.jornrigter.MancalaBoard;
import com.jornrigter.Player;
import com.jornrigter.holes.CollectionHole;
import com.jornrigter.holes.Hole;
import com.jornrigter.holes.HoleSelector;
import com.jornrigter.holes.SelectableHole;

import java.util.List;
import java.util.stream.Stream;

public class MoveMaker {

    /**
     * @param hole the selected hole to make the move with
     * @param player the player whose turn it is
     * @param board the Mancala board representation
     * @return the player who's turn it is after making the move
     */
    public static Player makeMove(Hole hole, Player player, List<Hole> board, MancalaBoard.OnGameEndedListener gameEndedListener) {
        Hole currentHole = hole;
        // Take as many steps as the number of seeds in the selected hole allows.
        for (int seeds = hole.getSeeds(); seeds > 0; seeds--) {
            // Move to the next hole and add a seed
            currentHole = currentHole.getNextHole();
            if (!currentHole.canAcceptSeed(player)) {
                System.out.println("Hole " + currentHole.getHoleNumber() + " cant accept seeds from " + player);
                // Can't accept current player's seeds: increase seeds by one to end in the right hole
                seeds++;
            } else {
                currentHole.addSeeds(1);
            }
        }
        hole.clearSeeds();
        return checkEndState(currentHole, player, board, gameEndedListener);
    }

    /**
     * Checks the end state based on the end hole and performs the appropriate actions based on that (i.e. checks for
     * another turn by the same player through ending in the own player's collection hole, and checks if end hole is
     * empty and from own player, taking all the seeds from the opponent's opposite hole)
     *
     * @param endHole the hole this turn ends in
     * @param player current player
     * @param board mancala board representation
     * @param gameEndedListener listener for if game ended
     *
     * @return the next player whose turn it is
     */
    private static Player checkEndState(Hole endHole, Player player, List<Hole> board,
                                        MancalaBoard.OnGameEndedListener gameEndedListener) {
        // Check if current player ended game by removing all seeds from their board
        if (!checkGameEnded(player, board, gameEndedListener)) {
            if (endHole instanceof CollectionHole && endHole.getPlayer() == player) {
                // If ended up in player's collection hole, play again
                return player;
            } else if (endHole instanceof SelectableHole && endHole.getPlayer() == player && endHole.getSeeds() == 1) {
                // If ended in own player's empty selectable hole (which would contain one seed now), the player wins all of
                // the seeds from the opposite player if that hole has seeds
                Hole oppositeHole = HoleSelector.getOppositeHole(endHole, board);
                if (oppositeHole.getSeeds() != 0) {
                    Hole collectionHole = HoleSelector.getCollectionHole(player, board);
                    // Take the seeds and move them
                    collectionHole.addSeeds(oppositeHole.getSeeds());
                    collectionHole.addSeeds(endHole.getSeeds());
                    oppositeHole.clearSeeds();
                    endHole.clearSeeds();
                }
            }

            // Switch player's turn in other case, and check if new player can actually make moves or not
            player = player.getOpponent();
            checkGameEnded(player, board, gameEndedListener);
        }
        return player;
    }

    /**
     * Checks whether the game ended, i.e. if the given player can make a move or not.
     * @param player player whose turn it is now
     * @param board Mancala board
     * @param gameEndedListener listener for when the game ended.
     *
     * @return true if game ended
     */
    private static boolean checkGameEnded(Player player, List<Hole> board,
                                          MancalaBoard.OnGameEndedListener gameEndedListener) {
        // If player cannot make a move, add all seeds to collection hole opponent
        if (!MoveValidator.canPlayerMakeMove(player, board)) {
            Hole opponentCollectionHole = HoleSelector.getCollectionHole(player.getOpponent(), board);
            List<Hole> playerHoles = HoleSelector.getSelectableHoles(player, board);
            List<Hole> opponentHoles = HoleSelector.getSelectableHoles(player.getOpponent(), board);
            // Add all remaining seeds on the board to the opponent player's collection hole
            for (Hole hole : Stream.concat(playerHoles.stream(), opponentHoles.stream()).toList()) {
                opponentCollectionHole.addSeeds(hole.getSeeds());
                hole.clearSeeds();
            }
            // Game ended when we get here (see https://brainking.com/nl/GameRules?tp=103)
            gameEndedListener.gameEnded();
            return true;
        }
        return false;
    }
}
