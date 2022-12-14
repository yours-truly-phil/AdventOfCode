package aoc2022

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {

    private fun solvePart1(input: String): Int = input.split("\n\n").maxOf { elv -> elv.lines().sumOf { it.toInt() } }


    private fun solvePart2(input: String): Int =
        input.split("\n\n").map { elv -> elv.lines().sumOf { it.toInt() } }.sortedDescending().take(3).sum()

    @Test
    fun part1() {
        assertEquals(24000, solvePart1("1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000"))
        assertEquals(70369, solvePart1(File("files/2022/day1.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(45000, solvePart2("1000\n2000\n3000\n\n4000\n\n5000\n6000\n\n7000\n8000\n9000\n\n10000"))
        assertEquals(203002, solvePart2(File("files/2022/day1.txt").readText()))
    }
}