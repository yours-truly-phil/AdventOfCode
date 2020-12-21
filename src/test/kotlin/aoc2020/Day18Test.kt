package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18Test {

    private val day18 = Day18()
    private val day18Part2 = Day18Part2()

    @Test
    fun calculate() {
        val day18 = Day18()
        assertEquals(
            (((((8 * 7) + 4) * 5) + 8) + 9),
            day18.calculate("8 * 7 + 4 * 5 + 8 + 9")
        )
    }

    @Test
    fun moreBrackets() {
        assertEquals(4, day18.calculate("1 + (1 + 1) + 1"))
    }

    @Test
    fun twoBracketPairs() {
        assertEquals(4, day18.calculate("(1 + 1) + (1 + 1)"))
    }

    @Test
    fun bracketsOperatorMix() {
        assertEquals(
            9 + (((((8 * 7) + 4) * 5) + 8) + 9),
            day18.calculate("(9 + (8 * 7 + 4 * 5 + 8 + 9))")
        )
    }

    @Test
    fun multipleBraces() {
        assertEquals(
            1 + (9 * 2 + 1),
            day18.calculate("1 + (1 + (1 + 1) * 3 * (1 + 1) + 1)")
        )
    }

    @Test
    fun addBracketsOnPlusLeft() {
        assertEquals(0, day18Part2.idxNewBracketLeft("1 + 1".toList(), 2))
        assertEquals(0, day18Part2.idxNewBracketLeft("(1 * 1) + 1".toList(), 8))
        assertEquals(4, day18Part2.idxNewBracketLeft("1 * (1 * 1) + 1".toList(), 12))
    }

    @Test
    fun calculatePart2() {
        assertEquals(2, day18Part2.calculate("1 + 1"))
        assertEquals(2, day18Part2.calculate("(1 * 1) + 1"))
        assertEquals(2, day18Part2.calculate("1 * (1 * 1) + 1"))
    }
}