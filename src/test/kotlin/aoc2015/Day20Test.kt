package aoc2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day20Test {

    @Test
    fun part1() {
        Day20().part1("150").also { assertEquals(8, it) }
        Day20().part1("119").also { assertEquals(6, it) }
        Day20().part1("180").also { assertEquals(10, it) }
    }
}