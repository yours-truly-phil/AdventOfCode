package aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13Test {

    @Test
    fun part1() {
        val input = "Alice would gain 54 happiness units by sitting next to Bob.\n" +
                "Alice would lose 79 happiness units by sitting next to Carol.\n" +
                "Alice would lose 2 happiness units by sitting next to David.\n" +
                "Bob would gain 83 happiness units by sitting next to Alice.\n" +
                "Bob would lose 7 happiness units by sitting next to Carol.\n" +
                "Bob would lose 63 happiness units by sitting next to David.\n" +
                "Carol would lose 62 happiness units by sitting next to Alice.\n" +
                "Carol would gain 60 happiness units by sitting next to Bob.\n" +
                "Carol would gain 55 happiness units by sitting next to David.\n" +
                "David would gain 46 happiness units by sitting next to Alice.\n" +
                "David would lose 7 happiness units by sitting next to Bob.\n" +
                "David would gain 41 happiness units by sitting next to Carol."
        assertEquals(330, Day13().part1(input, false))
    }
}