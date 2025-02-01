package com.jornrigter;

import com.jornrigter.moves.InvalidMoveException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MancalaTests {

    private static MancalaBoard mancalaBoard;

    /**
     * Set up the board once, for all consecutive tests to use.
     */
    @BeforeAll
    public static void setUpBeforeAll() {
        mancalaBoard = new MancalaBoard(new MancalaBoard.OnGameEndedListener() {
            @Override
            public void gameEnded() {
                // Do nothing.
            }
        });
    }

    // Start with a bunch of tests after the initial setup, testing for exceptions being thrown correctly.

    @Test
    @Order(1)
    void testPickCollectionPitException() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(7),
                "Expected makeMove to throw InvalidMoveException (CANT_PICK_COLLECTION_PIT), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.CANT_PICK_COLLECTION_HOLE, thrown.getType());
    }

    @Test
    @Order(2)
    void testPickOtherCollectionPitException() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(14),
                "Expected makeMove to throw InvalidMoveException (CANT_PICK_COLLECTION_PIT), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.CANT_PICK_COLLECTION_HOLE, thrown.getType());
    }

    @Test
    @Order(3)
    void testThrowOtherPlayerHoleException() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(8),
                "Expected makeMove to throw InvalidMoveException (CANT_PICK_HOLE_OTHER_PLAYER), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.CANT_PICK_HOLE_OTHER_PLAYER, thrown.getType());
    }

    @Test
    @Order(4)
    void testInvalidHole_0() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(0),
                "Expected makeMove to throw InvalidMoveException (INVALID_HOLE), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.INVALID_HOLE, thrown.getType());
    }

    @Test
    @Order(5)
    void testInvalidHole_15() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(15),
                "Expected makeMove to throw InvalidMoveException (INVALID_HOLE), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.INVALID_HOLE, thrown.getType());
    }

    /**
     * Check if player can make a move
     */
    @Order(6)
    void testCanPlayerMakeMove() {
        assertTrue(mancalaBoard.canPlayerMakeMove());
    }

    // Now start making the first actual valid move

    /**
     * Player 1: move hole 3 -- this should finish with one seed in the player's own collection pit, so it should be the
     * same player's turn again
     * @throws InvalidMoveException
     */
    @Test
    @Order(7)
    void testValidMove1() throws InvalidMoveException {
        mancalaBoard.makeMove(3);
        assertEquals(0, mancalaBoard.getHole(3).getSeeds());
        assertEquals(5, mancalaBoard.getHole(4).getSeeds());
        assertEquals(5, mancalaBoard.getHole(5).getSeeds());
        assertEquals(5, mancalaBoard.getHole(6).getSeeds());
        assertEquals(1, mancalaBoard.getHole(7).getSeeds());
    }

    /**
     * Check for InvalidMoveException when picking the empty hole that was just created.
     */
    @Test
    @Order(8)
    void testThrowCantPickEmptyHoleException() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(3),
                "Expected makeMove to throw InvalidMoveException (CANT_PICK_EMPTY_HOLE), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.CANT_PICK_EMPTY_HOLE, thrown.getType());
    }

    /**
     * Test if it's actually still the same player's turn. If it is, there should be an InvalidMoveException when picking
     * one of the opposite player's holes
     */
    @Test
    @Order(9)
    void testThrowOtherPlayerHoleExceptionAfterFirstMove() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(10),
                "Expected makeMove to throw InvalidMoveException (CANT_PICK_HOLE_OTHER_PLAYER), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.CANT_PICK_HOLE_OTHER_PLAYER, thrown.getType());
    }

    /**
     * Player 1: Move hole 2 as explained in <a href="https://brainking.com/nl/GameRules?tp=103">Mancala Rules</a>
     * @throws InvalidMoveException
     */
    @Test
    @Order(10)
    void testValidMove2() throws InvalidMoveException {
        mancalaBoard.makeMove(2);
        assertEquals(0, mancalaBoard.getHole(2).getSeeds());
        assertEquals(1, mancalaBoard.getHole(3).getSeeds());
        assertEquals(6, mancalaBoard.getHole(4).getSeeds());
        assertEquals(6, mancalaBoard.getHole(5).getSeeds());
        assertEquals(6, mancalaBoard.getHole(6).getSeeds());
        assertEquals(1, mancalaBoard.getHole(7).getSeeds());
    }

    /**
     * Now test if it's the other player's turn. We test this by trying to make a move on the first player's side: there
     * should be an InvalidMoveException when picking one of the those holes
     */
    @Test
    @Order(11)
    void testThrowOtherPlayerHoleExceptionAfterSecondMove() {
        InvalidMoveException thrown = assertThrows(
                InvalidMoveException.class,
                () -> mancalaBoard.makeMove(1),
                "Expected makeMove to throw InvalidMoveException (CANT_PICK_HOLE_OTHER_PLAYER), but it didn't"
        );
        assertEquals(InvalidMoveException.Type.CANT_PICK_HOLE_OTHER_PLAYER, thrown.getType());
    }

    // Continue the game for a while, making some valid moves and checking for exceptions in between

    /**
     * Player 2's move
     */
    @Test
    @Order(12)
    void testValidMove3() throws InvalidMoveException {
        mancalaBoard.makeMove(10); // Will end up in collection pit (hole 14)
        assertEquals(0, mancalaBoard.getHole(10).getSeeds());
        assertEquals(5, mancalaBoard.getHole(11).getSeeds());
        assertEquals(1, mancalaBoard.getHole(14).getSeeds());
    }

    /**
     * Player 2's move again
     */
    @Test
    @Order(13)
    void testValidMove4() throws InvalidMoveException {
        mancalaBoard.makeMove(13);
        assertEquals(2, mancalaBoard.getHole(14).getSeeds());
        assertEquals(5, mancalaBoard.getHole(1).getSeeds());
        assertEquals(1, mancalaBoard.getHole(2).getSeeds());
        assertEquals(2, mancalaBoard.getHole(3).getSeeds());
    }

    /**
     * Player 1's move
     */
    @Test
    @Order(14)
    void testValidMove5() throws InvalidMoveException {
        mancalaBoard.makeMove(5);
        assertEquals(7, mancalaBoard.getHole(6).getSeeds());
        assertEquals(2, mancalaBoard.getHole(7).getSeeds());
    }

    /**
     * Player 2's move.
     */
    @Test
    @Order(15)
    void testValidMoves6() throws InvalidMoveException {
        mancalaBoard.makeMove(9); // Should end up in collection pit: move again
        mancalaBoard.makeMove(13); // Should end up in collection pit: move again
        assertEquals(0, mancalaBoard.getHole(13).getSeeds());
        // Ends up in hole 13, which doesn't have any seeds, so should take the seeds from player 1's hole 1 (opposite)
        mancalaBoard.makeMove(8);
        assertEquals(0, mancalaBoard.getHole(1).getSeeds());
        // Collection pit player 2 should have 10 seeds now
        assertEquals(10, mancalaBoard.getHole(14).getSeeds());
    }

    /**
     * Player 1's move.
     */
    @Test
    @Order(16)
    void testValidMove7() throws InvalidMoveException {
        // Ends up in hole 5, which doesn't have any seeds, so should take the seeds from player 2's hole 9 (opposite)
        mancalaBoard.makeMove(3);
        // Collection pit player 1 should have 4 seeds now (2 extra from hole 9)
        assertEquals(4, mancalaBoard.getHole(7).getSeeds());
    }
}
