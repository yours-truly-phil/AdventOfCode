package aoc2019

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    fun sumFuelRequirements(input: String): Int =
        input.lines()
            .map { it.toInt() }
            .map { it / 3 - 2 }.sum()

    @Test
    fun part1() {
        assertEquals(3497399, sumFuelRequirements(File("files/2019/day1.txt").readText()))
    }
}