package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5Test {

    @Test
    fun search() {
        assertEquals(biSearch("FBFBBFF", 'F', 'B'), 44)
        assertEquals(biSearch("RLR", 'L', 'R'), 5)
    }

    @Test
    fun seatIdOf() {
        assertEquals(seatIdOf("BFFFBBFRRR"), 567)
        assertEquals(seatIdOf("FFFBBBFRRR"), 119)
        assertEquals(seatIdOf("BBFFBBFRLL"), 820)
        assertEquals(seatIdOf("FFBFBFFLRL"), 162)
    }

    @Test
    fun calculations() {
        assertEquals((1 shl 7) - 1, 127)
        assertEquals(127 / 2, 63)
        assertEquals(63 - 63 / 2, 32)
        assertEquals(32 + (63 - 32) / 2, 47)
    }
}