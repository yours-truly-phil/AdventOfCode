package aoc2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class Day16Test {

    @Test
    fun part1() {
        val content = File("files/2020/day16.txt").readText()
        val day16 = Day16(content)
        assertEquals(day16.part1(), 28882)
    }

    @Test
    fun part2() {
        val content = File("files/2020/day16.txt").readText()
        val day16 = Day16(content)
        assertEquals(day16.part2(), 1429779530273)
    }
}