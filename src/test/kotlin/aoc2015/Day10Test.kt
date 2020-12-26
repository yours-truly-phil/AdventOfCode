package aoc2015

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day10Test {

    @Test
    fun test_next() {
        val day10 = Day10()
        assertEquals("11", day10.next("1"))
        assertEquals("21", day10.next("11"))
        assertEquals("1211", day10.next("21"))
        assertEquals("111221", day10.next("1211"))
        assertEquals("312211", day10.next("111221"))
    }
}