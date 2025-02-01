package com.jornrigter.moves;

import com.jornrigter.holes.HoleSelector;
import com.jornrigter.Player;
import com.jornrigter.holes.CollectionHole;
import com.jornrigter.holes.Hole;

import java.util.List;

public class MoveValidator {

    /**
     * Validates the selected move based on the hole number and the player's turn and throws an InvalidMoveException
     * if the desired move is invalid.
     *
     * @param holeNumber the chosen hole number
     * @param playersTurn the player who's turn it is
     * @param board the mancala board (list of holes)
     */
    public static void validateMove(int holeNumber, Player playersTurn, List<Hole> board) throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(holeNumber, board);
        if (hole instanceof CollectionHole) {
            throw new InvalidMoveException(InvalidMoveException.Type.CANT_PICK_COLLECTION_HOLE);
        } else if (hole.getPlayer() != playersTurn) {
            throw new InvalidMoveException(InvalidMoveException.Type.CANT_PICK_HOLE_OTHER_PLAYER);
        } else if (hole.getSeeds() == 0) {
            throw new InvalidMoveException((InvalidMoveException.Type.CANT_PICK_EMPTY_HOLE));
        }
    }

    /**
     * @param player player to check for
     * @param board list of holes on the board
     * @return true whether the given player can make a move, based on the given board
     */
    public static boolean canPlayerMakeMove(Player player, List<Hole> board) {
        // Filter only SelectableHole instances that belong to the player
        List<Hole> playerSelectableHoles = HoleSelector.getSelectableHoles(player, board);
        for (Hole hole : playerSelectableHoles) {
            if (hole.getSeeds() > 0) {
                return true;
            }
        }
        // If we get to this line, none of the selectable holes from the player had a seed
        return false;
    }
}
