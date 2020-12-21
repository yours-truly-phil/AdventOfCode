package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14Test {

    @Test
    fun part1() {
        val input = """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0""".trimIndent()
        assertEquals(day14part1(input.lines()), 165)
    }

    @Test
    fun part2() {
        val input = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1""".trimIndent()
        assertEquals(day14part2(input.lines()), 208)
    }
}