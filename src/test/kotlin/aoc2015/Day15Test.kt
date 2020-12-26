package aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15Test {

    @Test
    fun part1() {
        val input = "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n" +
                "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
        assertEquals(62842880, Day15().part1(input))
    }
}