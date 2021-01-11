package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private fun solveCaptcha(input: String): Int {
        val nums = input.map { it.toString().toInt() }
            .toIntArray()
        return nums.indices
            .filter { nums[it] == nums[(it + 1) % nums.size] }
            .sumBy { nums[it] }
    }

    @Test
    fun samples() {
        assertEquals(3, solveCaptcha("1122"))
        assertEquals(4, solveCaptcha("1111"))
        assertEquals(0, solveCaptcha("1234"))
        assertEquals(9, solveCaptcha("91212129"))
    }

    @Test
    fun part1() {
        assertEquals(1393, solveCaptcha(File("files/2017/day1.txt").readText()))
    }
}