package com.jornrigter;

import com.jornrigter.holes.CollectionHole;
import com.jornrigter.holes.Hole;
import com.jornrigter.holes.HoleSelector;
import com.jornrigter.moves.InvalidMoveException;
import com.jornrigter.moves.MoveMaker;
import com.jornrigter.moves.MoveValidator;

import java.util.List;

public class MancalaBoard {

    public interface OnGameEndedListener {

        void gameEnded();
    }

    /**
     * Mancala board (a list of holes). See {@link MoveValidator} for an overview of the legal holes and their numbers
     */
    private final List<Hole> board;

    /**
     * The player whose turn it currently is.
     */
    private Player currentPlayer;

    private final OnGameEndedListener onGameEndedListener;

    public MancalaBoard(OnGameEndedListener onGameEndedListener) {
        board = MancalaBoardFactory.createBoard();
        this.onGameEndedListener = onGameEndedListener;
        currentPlayer = Player.PLAYER_1;
    }

    /**
     * Attempts to make the move for the given hole number.
     *
     * @param holeNumber the hole number chosen by one of the players
     * @throws InvalidMoveException thrown if an invalid hole is chosen
     */
    public void makeMove(int holeNumber) throws InvalidMoveException {
        MoveValidator.validateMove(holeNumber, currentPlayer, board);
        Hole selectedHole = HoleSelector.getHole(holeNumber, board);
        currentPlayer = MoveMaker.makeMove(selectedHole, currentPlayer, board, onGameEndedListener);
    }

    /**
     * @param holeNumber hole number for which to get the hole
     * @return the hole belonging to the given holeNumber
     */
    public Hole getHole(int holeNumber) throws InvalidMoveException {
        return HoleSelector.getHole(holeNumber, board);
    }

    /**
     * @return whether the current player can make a move with the current board configuration
     */
    public boolean canPlayerMakeMove() {
        return MoveValidator.canPlayerMakeMove(currentPlayer, board);
    }

    /**
     * Prints this Mancala board in pretty format on System.out.
     */
    public void printPretty() {
        Hole player1CollHole = HoleSelector.getCollectionHole(Player.PLAYER_1, board);
        Hole player2CollHole = HoleSelector.getCollectionHole(Player.PLAYER_2, board);
        List<Hole> player1SelectableHoles = HoleSelector.getSelectableHoles(Player.PLAYER_1, board);
        List<Hole> player2SelectableHoles = HoleSelector.getSelectableHoles(Player.PLAYER_2, board);
        player2SelectableHoles = player2SelectableHoles.reversed();
        System.out.println("Current game status:\n");
        // Print player 1 selectable holes status on top
        printPlayerSelectableHoles(player2SelectableHoles);
        System.out.println("\n");
        // Print collection holes
        System.out.print(player2CollHole.getHoleNumber() + "                                                 " +
                player1CollHole.getHoleNumber());
        System.out.println();
        System.out.print(player2CollHole.getSeeds() + "p                                                 " +
                player1CollHole.getSeeds() + "p");
        System.out.println("\n");
        // Print player 2 selectable holes status on top
        printPlayerSelectableHoles(player1SelectableHoles);
        System.out.println("\n\n" + currentPlayer + "'s turn (holes " + currentPlayer.getPlayableHoles() + "). Which hole would you like to pick, to make your next move? (Pick 0 to end the game)\n");
    }

    private void printPlayerSelectableHoles(List<Hole> selectableHoles) {
        System.out.print("        ");
        for (Hole hole : selectableHoles) {
            System.out.print(" " + hole.getHoleNumber());
            int spaces = hole.getHoleNumber() > 10 ? 3 : 4;
            for (int i = 0; i < spaces; i++) {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.print("        ");
        for (Hole hole : selectableHoles) {
            System.out.print(" " + hole.getSeeds() + "p");
            int spaces = hole.getSeeds() > 10 ? 2 : 3;
            for (int i = 0; i < spaces; i++) {
                System.out.print(" ");
            }
        }
    }

    public void showEndScore() {
        Hole player1CollHole = HoleSelector.getCollectionHole(Player.PLAYER_1, board);
        Hole player2CollHole = HoleSelector.getCollectionHole(Player.PLAYER_2, board);
        System.out.println("End score:");
        System.out.println("Player 1 score: " + player1CollHole.getSeeds());
        System.out.println("Player 2 score: " + player2CollHole.getSeeds());
    }
}
