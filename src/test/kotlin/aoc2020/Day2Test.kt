package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day2Test {

    @Test
    fun day2part1() {
        val input = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent().lines()
        assertEquals(day2part1(input), 2)
    }

    @Test
    fun day2part2() {
        val input = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent().lines()
        assertEquals(day2part2(input), 1)
    }
}