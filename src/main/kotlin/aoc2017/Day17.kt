package aoc2017

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Day17 {
    fun circularBufVal(steps: Int): Int {
        var idx = 0
        val buf = LinkedList<Int>().apply { add(0) }
        repeat(2017) {
            idx = (idx + steps) % buf.size + 1
            buf.add(idx, it + 1)
        }
        return buf[(idx + 1) % buf.size]
    }

    @Test
    fun sample() {
        assertEquals(638, circularBufVal(3))
    }

    @Test
    fun part1() {
        assertEquals(419, circularBufVal(386))
    }
}