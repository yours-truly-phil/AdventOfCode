package aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24Test {

    @Test
    fun part1() {
        Day24().also { assertEquals(99, (it.part1("1\n2\n3\n4\n5\n7\n8\n9\n10\n11"))) }
    }
}