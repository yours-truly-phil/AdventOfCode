package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class Day21Test {

    @Test
    fun part1() {
        val day21 = Day21(File("files/2020/day21Test.txt").readLines())
        assertEquals(5, day21.part1())
    }

    @Test
    fun part2() {
        val day21 = Day21(File("files/2020/day21Test.txt").readLines())
        assertEquals("mxmxvkd,sqjhc,fvjkl", day21.part2())
    }
}