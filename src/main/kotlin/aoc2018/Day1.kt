package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    fun resultingFrequency(input: String): Int = input.lines().map { it.toInt() }.sum()

    @Test
    fun part1() {
        assertEquals(536, resultingFrequency(File("files/2018/day1.txt").readText()))
    }
}