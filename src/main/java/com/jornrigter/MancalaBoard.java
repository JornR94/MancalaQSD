package com.jornrigter;

import com.jornrigter.holes.Hole;
import com.jornrigter.holes.HoleSelector;
import com.jornrigter.moves.InvalidMoveException;
import com.jornrigter.moves.MoveMaker;
import com.jornrigter.moves.MoveValidator;

import java.util.List;

public class MancalaBoard {

    /**
     * Mancala board (a list of holes). See {@link MoveValidator} for an overview of the legal holes and their numbers
     */
    private final List<Hole> board;

    /**
     * The player whose turn it currently is.
     */
    private Player currentPlayer;

    public MancalaBoard() {
        board = MancalaBoardFactory.createBoard();
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
        currentPlayer = MoveMaker.makeMove(selectedHole, currentPlayer, board);
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
}
