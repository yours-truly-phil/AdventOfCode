package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23Test {

    @Test
    fun part1() {
        val day23Other = Day23("389125467", 9)
        assertEquals("92658374", day23Other.part1(10))
        val day232 = Day23("389125467", 9)
        assertEquals("67384529", day232.part1(100))
    }

    @Test
    fun part2() {
        val day23 = Day23("389125467", 1_000_000)
        assertEquals(149245887792, day23.part2(10_000_000))
    }
}