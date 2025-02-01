package com.jornrigter.holes;

import com.jornrigter.MancalaBoardFactory;
import com.jornrigter.Player;
import com.jornrigter.moves.InvalidMoveException;

import java.util.List;

public class HoleSelector {

    /**
     *
     * @param holeNumber the hole number
     * @param board the mancala "board" (list of holes)
     * @return the hole for the given hole number
     * @throws InvalidMoveException
     */
    public static Hole getHole(int holeNumber, List<Hole> board) throws InvalidMoveException {
        if (holeNumber < 1 || holeNumber > 14) {
            throw new InvalidMoveException(InvalidMoveException.Type.INVALID_HOLE);
        }
        // List starts at index 0.
        int holeNumberForList = holeNumber - 1;
        return board.get(holeNumberForList);
    }

    /**
     * @param player the player to get the collection hole for
     * @param board Mancala board representation
     * @return the collection hole for the given player
     */
    public static Hole getCollectionHole(Player player, List<Hole> board) {
        for (Hole hole : board) {
            if (hole instanceof CollectionHole && hole.getPlayer() == player) {
                return hole;
            }
        }
        throw new IllegalArgumentException("Collection hole could not be found for player " + player);
    }

    public static List<Hole> getSelectableHoles(Player player, List<Hole> board) {
        return board.stream()
                .filter(hole -> hole instanceof SelectableHole)
                .filter(hole -> hole.getPlayer().equals(player))
                .toList();
    }

    /**
     * @param hole hole to get the opposite hole for
     * @param board mancala board representation
     * @return the hole opposite to the given hole
     */
    public static Hole getOppositeHole(Hole hole, List<Hole> board) throws IllegalArgumentException {
        int holeNumber = hole.holeNumber;
        // For player 1: For hole 1, the opposite hole is 13. For hole 2, it's 12, hole 3 it's 11, etc until hole 6 with
        // opposite 8. What we can do is reverse the selectable holes for player 2, and select the right opposite one
        // based on the number in the list
        Player player = hole.getPlayer();
        List<Hole> opponentSelectableHoles = getSelectableHoles(player.getOpponent(), board);
        List<Hole> reversedSelectableHoles = opponentSelectableHoles.reversed();
        // Adjusted hole number for getting the hole in the list
        int adjustedHoleNumber = holeNumber - (player == Player.PLAYER_2 ? MancalaBoardFactory.NUMBER_OF_HOLES_PER_PLAYER : 0) - 1;
        return reversedSelectableHoles.get(adjustedHoleNumber);
    }
}
