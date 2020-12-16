package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {

    @Test
    fun day3part2() {
        val input = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#""".trimIndent()
        assertEquals(day3part2(input.lines()), 336)
    }
}