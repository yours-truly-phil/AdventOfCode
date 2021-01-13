package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

class Day11 {
    fun minSteps(input: String): Int {
        val path = input.split(",").map {
            when (it) {
                "n" -> Pair(2, 0)
                "ne" -> Pair(1, 1)
                "se" -> Pair(-1, 1)
                "s" -> Pair(-2, 0)
                "sw" -> Pair(-1, -1)
                else -> Pair(1, -1)
            }
        }
        var x = 0
        var y = 0
        path.forEach {
            x += it.first
            y += it.second
        }

        return (abs(x) + abs(y)) / 2
    }

    @Test
    fun part1() {
        assertEquals(810, minSteps(File("files/2017/day11.txt").readText()))
    }
}