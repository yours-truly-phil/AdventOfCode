package aoc2019

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private fun sumFuelRequirements(input: String): Int =
        input.lines().sumOf { it.toInt() / 3 - 2 }

    private fun fuelReqsPart2(input: String): Int =
        input.lines().sumOf { necessaryFuel(it.toInt()) }

    private fun necessaryFuel(mass: Int): Int {
        var count = 0
        var cur = mass
        while (cur > 0) {
            cur = cur / 3 - 2
            if (cur > 0) count += cur
        }
        return count
    }

    @Test
    fun part1() {
        assertEquals(3497399, sumFuelRequirements(File("files/2019/day1.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(5243207, fuelReqsPart2(File("files/2019/day1.txt").readText()))
    }
}
