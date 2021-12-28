package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private fun solveCaptcha(input: String): Int {
        val nums = parse(input)
        return nums.indices
            .filter { nums[it] == nums[(it + 1) % nums.size] }
            .sumOf { nums[it] }
    }

    private fun parse(input: String) = input.map { it.toString().toInt() }.toIntArray()

    private fun solveNewCaptcha(input: String): Int {
        val nums = parse(input)
        return nums.indices
            .filter { nums[it] == nums[(it + nums.size / 2) % nums.size] }
            .sumOf { nums[it] }
    }

    @Test
    fun samples() {
        assertEquals(3, solveCaptcha("1122"))
        assertEquals(4, solveCaptcha("1111"))
        assertEquals(0, solveCaptcha("1234"))
        assertEquals(9, solveCaptcha("91212129"))
    }

    @Test
    fun samplesPart2() {
        assertEquals(6, solveNewCaptcha("1212"))
        assertEquals(0, solveNewCaptcha("1221"))
        assertEquals(4, solveNewCaptcha("123425"))
        assertEquals(12, solveNewCaptcha("123123"))
        assertEquals(4, solveNewCaptcha("12131415"))
    }

    @Test
    fun part2() {
        assertEquals(1292, solveNewCaptcha(File("files/2017/day1.txt").readText()))
    }

    @Test
    fun part1() {
        assertEquals(1393, solveCaptcha(File("files/2017/day1.txt").readText()))
    }
}