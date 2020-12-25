package aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day6Test {

    @Test
    fun part1() {
        val day6 = Day6()
        assertEquals(1, day6.part1("turn on 5,5 through 5,5", 10, 10))
        assertEquals(4, day6.part1("turn on 0,0 through 1,1", 2, 2))
        assertEquals(
            2, day6.part1(
                "turn on 0,0 through 1,1\n" +
                        "turn off 1,0 through 1,1", 2, 2
            )
        )
        assertEquals(4, day6.part1("turn on 0,0 through 1,1", 4, 4))
        assertEquals(
            12, day6.part1(
                "turn on 0,0 through 1,1\n" +
                        "toggle 0,0 through 3,3\n" +
                        "turn off 0,0 through 1,0", 4, 4
            )
        )
    }
}