package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private fun numTimesDepthIncreases(input: String): Int {
        val nums = parseInput(input)
        var res = 0
        for (i in 1 until nums.size) {
            if (nums[i] > nums[i - 1]) {
                res++
            }
        }
        return res
    }

    private fun numTimesSumTripletsIncreases(input: String): Int {
        val nums = parseInput(input)
        var res = 0
        for (i in 1 until nums.size - 2) {
            val first = nums[i - 1] + nums[i] + nums[i + 1]
            val second = nums[i] + nums[i + 1] + nums[i + 2]
            if (first < second) {
                res++
            }
        }
        return res
    }

    private fun parseInput(input: String) =
        input.lines().filter { it.isNotEmpty() }.map { it.toInt() }

    @Test
    fun numTimesDepthIncreases() {
        assertEquals(7, numTimesDepthIncreases("199\n200\n208\n210\n200\n207\n240\n269\n260\n263"))
        assertEquals(
            1791,
            numTimesDepthIncreases(File("files/2021/day1.txt").readText())
        )
    }

    @Test
    fun part2() {
        assertEquals(5, numTimesSumTripletsIncreases("199\n200\n208\n210\n200\n207\n240\n269\n260\n263"))
        assertEquals(1822, numTimesSumTripletsIncreases(File("files/2021/day1.txt").readText()))
    }
}
