package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day5 {
    private fun countStepsToExit(input: String): Int {
        val instructions = input.lines().map { it.toInt() }.toIntArray()
        var idx = 0
        var steps = 0
        while (idx >= 0 && idx < instructions.size) {
            val inst = instructions[idx]
            instructions[idx]++
            idx += inst
            steps++
        }
        return steps
    }

    private fun countStepsStrangerToExit(input: String): Int {
        val instructions = input.lines().map { it.toInt() }.toIntArray()
        var idx = 0
        var steps = 0
        while (idx >= 0 && idx < instructions.size) {
            val inst = instructions[idx]
            when {
                inst >= 3 -> instructions[idx]--
                else -> instructions[idx]++
            }
            idx += inst
            steps++
        }
        return steps
    }

    @Test
    fun sample() {
        assertEquals(5, countStepsToExit("0\n3\n0\n1\n-3"))
    }

    @Test
    fun part2Sample() {
        assertEquals(10, countStepsStrangerToExit("0\n3\n0\n1\n-3"))
    }

    @Test
    fun part1() {
        assertEquals(318883, countStepsToExit(File("files/2017/day5.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(23948711, countStepsStrangerToExit(File("files/2017/day5.txt").readText()))
    }
}