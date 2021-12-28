package aoc2017

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Day17 {
    private fun circularBufVal(steps: Int): Int {
        var idx = 0
        val buf = LinkedList<Int>().apply { add(0) }
        repeat(2017) {
            idx = (idx + steps) % buf.size + 1
            buf.add(idx, it + 1)
        }
        return buf[(idx + 1) % buf.size]
    }

    @Suppress("SameParameterValue")
    private fun valueAfterZero(steps: Int): Int {
        var idx = 0
        var size = 1
        var idxZero = 0
        var numAfterZero = 0
        repeat(50_000_000) {
            idx = (idx + steps) % size + 1
            if (idx <= idxZero) idxZero++
            else if (idx == idxZero + 1) numAfterZero = it + 1
            size++
        }
        return numAfterZero
    }

    @Test
    fun sample() {
        assertEquals(638, circularBufVal(3))
    }

    @Test
    fun part1() {
        assertEquals(419, circularBufVal(386))
    }

    @Test
    fun part2() {
        assertEquals(46038988, valueAfterZero(386))
    }
}