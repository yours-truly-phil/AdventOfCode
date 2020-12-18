package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18Test {

    val day18 = Day18()

    @Test
    fun calculate() {
        val day18 = Day18()
        assertEquals(
            day18.calculate("8 * 7 + 4 * 5 + 8 + 9"),
            (((((8 * 7) + 4) * 5) + 8) + 9)
        )
    }

    @Test
    fun moreBrackets() {
        assertEquals(day18.calculate("1 + (1 + 1) + 1"), 4)
    }

    @Test
    fun twoBracketPairs() {
        assertEquals(day18.calculate("(1 + 1) + (1 + 1)"), 4)
    }

    @Test
    fun bracketsOperatorMix() {
        assertEquals( //              012345678910121416182022242628
            day18.calculate("(9 + (8 * 7 + 4 * 5 + 8 + 9))"),
            9 + (((((8 * 7) + 4) * 5) + 8) + 9)
        )
    }

    @Test
    fun multipleBraces() {
        assertEquals(
            day18.calculate("1 + (1 + (1 + 1) * 3 * (1 + 1) + 1)"),
            1 + (9 * 2 + 1)
        )
    }
}