package com.jornrigter;

import com.jornrigter.holes.Hole;
import com.jornrigter.holes.HoleSelector;
import com.jornrigter.moves.InvalidMoveException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HoleSelectorTests {

    private static List<Hole> board;

    /**
     * Set up the board once, for all tests to use.
     */
    @BeforeAll
    public static void setUpBeforeAll() {
        board = MancalaBoardFactory.createBoard();
    }

    @Test
    void testOppositeHole1() throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(1, board);
        assertEquals(13, HoleSelector.getOppositeHole(hole, board).getHoleNumber());
    }

    @Test
    void testOppositeHole2() throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(2, board);
        assertEquals(12, HoleSelector.getOppositeHole(hole, board).getHoleNumber());
    }

    @Test
    void testOppositeHole6() throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(6, board);
        assertEquals(8, HoleSelector.getOppositeHole(hole, board).getHoleNumber());
    }

    @Test
    void testOppositeHole8() throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(8, board);
        assertEquals(6, HoleSelector.getOppositeHole(hole, board).getHoleNumber());
    }

    @Test
    void testOppositeHole9() throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(9, board);
        assertEquals(5, HoleSelector.getOppositeHole(hole, board).getHoleNumber());
    }

    @Test
    void testOppositeHole13() throws InvalidMoveException {
        Hole hole = HoleSelector.getHole(13, board);
        assertEquals(1, HoleSelector.getOppositeHole(hole, board).getHoleNumber());
    }
}
