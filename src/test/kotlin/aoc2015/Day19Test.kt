package aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19Test {

    @Test
    fun part1() {
        val input = "H => HO\n" +
                "H => OH\n" +
                "O => HH\n\n" +
                "HOH"
        Day19().part1(input).also { assertEquals(4, it) }
        val input2 = "H => HO\n" +
                "H => OH\n" +
                "O => HH\n\n" +
                "HOHOHO"
        Day19().part1(input2).also { assertEquals(7, it) }
    }

    @Test
    fun part2() {
        val input = "e => H\n" +
                "e => O\n" +
                "H => HO\n" +
                "H => OH\n" +
                "O => HH\n\n" +
                "HOH"
        Day19().part2(input).also { assertEquals(3, it) }
        val input2 = "e => H\n" +
                "e => O\n" +
                "H => HO\n" +
                "H => OH\n" +
                "O => HH\n\n" +
                "HOHOHO"
        Day19().part2(input2).also { assertEquals(6, it) }
    }
}