package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day20 {
    private fun findLowestValid(input: String): Long {
        val invalidRanges = input.lines()
            .map { Range(it.split("-")[0].toLong(), it.split("-")[1].toLong()) }
            .toMutableList()

        for (i in 0L..4294967295L) {
            var valid = true
            val toIgnore = ArrayList<Range>()
            for (range in invalidRanges) {
                if (range.inRange(i)) {
                    valid = false
                    break
                } else if (range.high < i) {
                    toIgnore.add(range)
                }
            }
            invalidRanges.removeAll(toIgnore)
            if (valid) return i
        }
        return -1
    }

    data class Range(var low: Long, var high: Long) {
        fun inRange(num: Long): Boolean {
            return num in low..high
        }
    }

    @Test
    fun part1() {
        assertEquals(14975795, findLowestValid(File("files/2016/day20.txt").readText()))
    }

    @Test
    fun sample() {
        assertEquals(3, findLowestValid("5-8\n" +
                "0-2\n" +
                "4-7"))
    }
}