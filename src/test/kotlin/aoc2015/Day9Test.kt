package aoc2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day9Test {

    @Test
    fun part1() {
        val input = "London to Dublin = 464\n" +
                "London to Belfast = 518\n" +
                "Dublin to Belfast = 141"
        assertEquals(605, Day9().part1(input))
    }
}