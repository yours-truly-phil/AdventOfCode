package aoc2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15 {
    fun finalCount(genA: Long, genB: Long): Int {
        var a = genA
        var b = genB
        var count = 0
        repeat(40_000_000) {
            a = (a * 16807) % 2147483647
            b = (b * 48271) % 2147483647
            if (a and 0xffff == b and 0xffff) count++
        }
        return count
    }

    @Test
    fun sample() {
        assertEquals(588, finalCount(65, 8921))
    }

    @Test
    fun part1() {
        assertEquals(619, finalCount(591, 393))
    }
}