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

    fun checksumEvenlyDivisables(input: String): Int {
        return input.lines()
            .sumBy { evenlyDivide(it.split("\\s+".toRegex()).map { s -> s.toInt() }.toIntArray()) }
    }

    fun evenlyDivide(nums: IntArray): Int {
        nums.forEach { i -> nums.forEach { j -> if (i != j && i % j == 0) return i / j } }
        throw Exception("no numbers found")
    }

    @Test
    fun sample() {
        assertEquals(18, checksum("5 1 9 5\n" +
                "7 5 3\n" +
                "2 4 6 8"))
    }

    @Test
    fun `part2 sample`() {
        assertEquals(9, checksumEvenlyDivisables("5 9 2 8\n" +
                "9 4 7 3\n" +
                "3 8 6 5"))
    }

    @Test
    fun part1() {
        assertEquals(37923, checksum(File("files/2017/day2.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(263, checksumEvenlyDivisables(File("files/2017/day2.txt").readText()))
    }
}