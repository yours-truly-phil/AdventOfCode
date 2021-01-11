package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day2 {
    fun checksum(input: String): Int {
        return input.lines().sumBy {
            it.split("\\s+".toRegex()).maxOf { num -> num.toInt() } -
                    it.split("\\s+".toRegex()).minOf { num -> num.toInt() }
        }
    }

    @Test
    fun sample() {
        assertEquals(18, checksum("5 1 9 5\n" +
                "7 5 3\n" +
                "2 4 6 8"))
    }

    @Test
    fun part1() {
        assertEquals(37923, checksum(File("files/2017/day2.txt").readText()))
    }
}