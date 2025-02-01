package com.jornrigter;

import com.jornrigter.holes.CollectionHole;
import com.jornrigter.holes.Hole;
import com.jornrigter.holes.SelectableHole;

import java.util.ArrayList;
import java.util.List;

public class MancalaBoardFactory {

    /**
     * The number of selectable holes per player for the Mancala game.
     */
    private static final int SELECTABLE_HOLES_PER_PLAYER = 6;

    /**
     * The number of holes per player (selectable holes and collection hole)
     */
    public static final int NUMBER_OF_HOLES_PER_PLAYER = SELECTABLE_HOLES_PER_PLAYER + 1;

    /**
     * Creates the Mancala "board" (a list of 14 holes: 1 collection hole and 6 selectable holes per player)
     * @return the list of holes representing the mancala board
     */
    public static List<Hole> createBoard() {
        List<Hole> boardHoles = new ArrayList<>();
        CollectionHole collectionHoleP1 = new CollectionHole(Player.PLAYER_1, NUMBER_OF_HOLES_PER_PLAYER);
        CollectionHole collectionHoleP2 = new CollectionHole(Player.PLAYER_2, NUMBER_OF_HOLES_PER_PLAYER * 2);
        addHoles(boardHoles, Player.PLAYER_1, collectionHoleP1, collectionHoleP2);
        addHoles(boardHoles, Player.PLAYER_2, collectionHoleP2, collectionHoleP1);
        return boardHoles;
    }

    /**
     * Adds a player's holes to the toAddTo list, along with his/her collection hole
     * @param toAddTo list of holes to add to
     * @param player the player to make the holes for
     * @param collectionHoleOpponent the opponent's collection hole
     */
    private static void addHoles(List<Hole> toAddTo, Player player, Hole collectionHolePlayer, Hole collectionHoleOpponent) {
        Hole lastHole = null;
        // Start adding the selectable holes
        List<Hole> selectableHoles = new ArrayList<>();
        for (int hole = SELECTABLE_HOLES_PER_PLAYER; hole >= 1; hole--) {
            // The hole numbers are 1-7 (7 = collection hole) for player 1, and 8-14 (14 = collection hole) for player 2
            int holeNumber = player == Player.PLAYER_1 ? hole : hole + NUMBER_OF_HOLES_PER_PLAYER;
            Hole newHole = new SelectableHole(player, holeNumber);
            if (hole == SELECTABLE_HOLES_PER_PLAYER) {
                // Set next hole to player's collection hole if last hole, otherwise, set to last hole
                newHole.setNextHole(collectionHolePlayer);
            } else if (hole == 1) {
                // Set the opponent's collection hole to the first hole of this player
                collectionHoleOpponent.setNextHole(newHole);
                newHole.setNextHole(lastHole);
            } else {
                newHole.setNextHole(lastHole);
            }
            lastHole = newHole;
            selectableHoles.add(newHole);
        }
        // Reverse the order of the selectable holes, to add them to the board in the right order
        toAddTo.addAll(selectableHoles.reversed());
        // And after adding the selectable holes, add the player's collection hole
        toAddTo.add(collectionHolePlayer);
    }
}
