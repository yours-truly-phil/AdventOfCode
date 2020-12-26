package aoc2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day11Test {

    @Test
    fun part1() {
        val day11 = Day11()
        assertEquals("abcdffaa", day11.part1("abcdfezz"))
        assertEquals("abcdffaa", day11.part1("abcdefgh"))
        assertEquals("ghjaabcc", day11.part1("ghijklmn"))
    }
}