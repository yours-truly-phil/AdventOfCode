package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day20 {
    private fun findLowestValid(input: String): Long {
        val invalidRanges = input.lines()
            .map { Range(it.split("-")[0].toLong(), it.split("-")[1].toLong()) }
            .sorted()

        var lower = 0L
        for (range in invalidRanges) {
            if(range.inRange(lower)) {
                lower = range.high + 1
            }
        }
        return lower
    }

    private fun countValids(input: String): Long {
        val invalidRanges = input.lines()
            .map { Range(it.split("-")[0].toLong(), it.split("-")[1].toLong()) }
            .sorted()
            .toMutableList()

        var count = 0L
        var lower = 0L
        while(lower <= 4294967295L) {
            for (range in invalidRanges) {
                if (range.inRange(lower)) {
                    lower = range.high + 1
                }
            }
            if(lower <= 4294967295L) count++
            lower++
        }
        return count
    }

    data class Range(var low: Long, var high: Long) : Comparable<Range> {
        fun inRange(num: Long): Boolean {
            return num in low..high
        }

        override fun compareTo(other: Range): Int {
            return low.compareTo(other.low)
        }
    }

    @Test
    fun part1() {
        assertEquals(14975795, findLowestValid(File("files/2016/day20.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(101, countValids(File("files/2016/day20.txt").readText()))
    }

    @Test
    fun sample() {
        assertEquals(3, findLowestValid("5-8\n" +
                "0-2\n" +
                "4-7"))
    }
}