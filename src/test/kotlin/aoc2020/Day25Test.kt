package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25Test {

    @Test
    fun part1() {
        val day25 = Day25()
        assertEquals(14897079, day25.part1(5764801, 17807724))
    }
}