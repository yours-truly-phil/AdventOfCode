package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private fun foo(input: String): Int {
        return -1
    }

    private fun bar(input: String): Int {
        return -1
    }

    @Test
    fun part1() {
        assertEquals(1, foo(File("files/2021/day1.txt").readText()))
    }

//    @Test
//    fun part2() {
//        assertEquals(1, bar(File("files/2021/day1.txt").readText()))
//    }
}
