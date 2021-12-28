package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private fun resultingFrequency(input: String): Int = input.lines().sumOf { it.toInt() }

    private fun firstRepeatedFrequency(input: String): Int {
        val nums = input.lines().map { it.toInt() }.toIntArray()
        val memo = HashSet<Int>()
        var sum = 0
        while (true) {
            for (num in nums) {
                if (memo.contains(sum)) {
                    return sum
                }
                memo.add(sum)
                sum += num
            }
        }
    }

    @Test
    fun part2Sample() {
        assertEquals(0, firstRepeatedFrequency("+1\n-1"))
        assertEquals(10, firstRepeatedFrequency("+3\n+3\n+4\n-2\n-4"))
    }

    @Test
    fun part1() {
        assertEquals(536, resultingFrequency(File("files/2018/day1.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(75108, firstRepeatedFrequency(File("files/2018/day1.txt").readText()))
    }
}