package aoc2016

import aoc2016.Day11.Move
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class Day11Test {

    @Test
    fun isFloorValid() {
        assertEquals(true, Day11().isFloorValid(intArrayOf(1, 0, 1, 0, 1, 0, 1), 0))
        assertEquals(true, Day11().isFloorValid(intArrayOf(1, 0, 0, 0, 0, 0, 0), 0))
        assertEquals(true, Day11().isFloorValid(intArrayOf(1, 0, 1, 1, 1, 1, 1), 0))
        assertEquals(true, Day11().isFloorValid(intArrayOf(1, 0, 1, 0, 0, 0, 0), 0))
        assertEquals(false, Day11().isFloorValid(intArrayOf(1, 0, 1, 1, 1), 1))
        assertEquals(false, Day11().isFloorValid(intArrayOf(1, 0, 1, 1, 0), 1))
        assertEquals(false, Day11().isFloorValid(intArrayOf(1, 0, 1, 1, 0), 0))
        assertEquals(true, Day11().isFloorValid(intArrayOf(1, 0, 1, 1, 0), 2))
    }

    @Test
    fun allInFloor() {
        assertEquals(true, Day11().allInFloor(Move(intArrayOf(3, 3, 3, 3, 3, 3, 3), 0), 3))
        assertEquals(false, Day11().allInFloor(Move(intArrayOf(3, 2, 3, 3, 3, 3, 3), 0), 3))
        assertEquals(false, Day11().allInFloor(Move(intArrayOf(3, 3, 2, 3, 3, 3, 3), 0), 3))
        assertEquals(false, Day11().allInFloor(Move(intArrayOf(3, 3, 2, 3, 3, 3, 3), 0), 0))
    }

    @Test
    fun isValidState() {
        assertEquals(true, Day11().isValidState(Move(intArrayOf(0, 1, 1, 2, 2, 3, 3), 0)))
        assertEquals(false, Day11().isValidState(Move(intArrayOf(0, 1, 2, 2, 1, 3, 3), 0)))
    }

    @Test
    fun possibleMoves() {
        var memo = HashSet<Move>()
        var moves = Day11().possibleMoves(Move(intArrayOf(0, 0, 0, 0, 0), 0), memo)
        val expected = hashSetOf(
            Move(intArrayOf(1, 0, 1, 0, 0), 1),
            Move(intArrayOf(1, 0, 0, 0, 1), 1),
            Move(intArrayOf(1, 1, 1, 0, 0), 1),
            Move(intArrayOf(1, 1, 0, 1, 0), 1),
            Move(intArrayOf(1, 0, 1, 0, 1), 1),
            Move(intArrayOf(1, 0, 0, 1, 1), 1)
        )
        assertTrue(expected.containsAll(moves))
        assertTrue(moves.containsAll(expected))
        assertTrue(memo.containsAll(moves))

        memo = HashSet()
        moves = Day11().possibleMoves(Move(intArrayOf(0, 1, 0, 2, 0), 0), memo)
        assertTrue(moves.contains(Move(intArrayOf(1, 1, 1, 2, 0), 1)))
        moves = Day11().possibleMoves(Move(intArrayOf(1, 1, 1, 2, 0), 1), memo)
        assertTrue(moves.contains(Move(intArrayOf(2, 2, 2, 2, 0), 2)))
        moves = Day11().possibleMoves(Move(intArrayOf(2, 2, 2, 2, 0), 2), memo)
        assertTrue(moves.contains(Move(intArrayOf(1, 2, 1, 2, 0), 3)))
        moves = Day11().possibleMoves(Move(intArrayOf(1, 2, 1, 2, 0), 3), memo)
        assertTrue(moves.contains(Move(intArrayOf(0, 2, 0, 2, 0), 4)))
        moves = Day11().possibleMoves(Move(intArrayOf(0, 2, 0, 2, 0), 4), memo)
        assertTrue(moves.contains(Move(intArrayOf(1, 2, 1, 2, 1), 5)))
        moves = Day11().possibleMoves(Move(intArrayOf(1, 2, 1, 2, 1), 5), memo)
        assertTrue(moves.contains(Move(intArrayOf(2, 2, 2, 2, 2), 6)))
        moves = Day11().possibleMoves(Move(intArrayOf(2, 2, 2, 2, 2), 6), memo)
        assertTrue(moves.contains(Move(intArrayOf(3, 2, 3, 2, 3), 7)))
        moves = Day11().possibleMoves(Move(intArrayOf(3, 2, 3, 2, 3), 7), memo)
        assertTrue(moves.contains(Move(intArrayOf(2, 2, 2, 2, 3), 8)))
        moves = Day11().possibleMoves(Move(intArrayOf(2, 2, 2, 2, 3), 8), memo)
        assertTrue(moves.contains(Move(intArrayOf(3, 3, 2, 3, 3), 9)))
        moves = Day11().possibleMoves(Move(intArrayOf(3, 3, 2, 3, 3), 9), memo)
        assertTrue(moves.contains(Move(intArrayOf(2, 3, 2, 3, 2), 10)))
        moves = Day11().possibleMoves(Move(intArrayOf(2, 3, 2, 3, 2), 10), memo)
        assertTrue(moves.contains(Move(intArrayOf(3, 3, 3, 3, 3), 11)))
    }

    @Test
    fun part1() {
        assertEquals(11, Day11().part1(Move(intArrayOf(0, 1, 0, 2, 0), 0)))
    }
}