package aoc2022

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day2 {

    private fun solvePart1(input: String): Int = input.lines().map {
        val (op, me) = it.split(" ")
        Pair(
            when (op) {
                "A" -> 1
                "B" -> 2
                else -> 3
            },
            when (me) {
                "X" -> 1
                "Y" -> 2
                else -> 3
            }
        )
    }.sumOf {
        when (it.second - it.first) {
            0 -> 3
            1 -> 6
            -2 -> 6
            -1 -> 0
            2 -> 0
            else -> 0
        } + it.second
    }


    private fun solvePart2(input: String): Int = input.lines().sumOf {
        val (left, right) = it.split(" ")
        when (right) {
            "X" -> {
                // need to loose
                when (left) {
                    "A" -> 3
                    "B" -> 1
                    else -> 2
                }
            }
            "Y" -> {
                // end in draw
                when (left) {
                    "A" -> 1
                    "B" -> 2
                    else -> 3
                } + 3
            }
            else -> {
                // need to win
                when (left) {
                    "A" -> 2
                    "B" -> 3
                    else -> 1
                } + 6
            }
        }
    }

    @Test
    fun part1() {
        assertEquals(15, solvePart1("A Y\nB X\nC Z"))
        assertEquals(12794, solvePart1(File("files/2022/day2.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(12, solvePart2("A Y\nB X\nC Z"))
        assertEquals(14979, solvePart2(File("files/2022/day2.txt").readText()))
    }
}