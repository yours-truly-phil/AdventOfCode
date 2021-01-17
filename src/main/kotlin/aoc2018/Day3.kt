package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day3 {
    private fun numSquareInchesWithinTwoOrMoreClaims(input: String): Int {
        val fabric = Array(1000) { IntArray(1000) }
        input.lines().forEach {
            val parts = it.split(" ")
            val border = parts[2]
            val size = parts[3]
            val left = border.substring(0, border.indexOf(",")).toInt()
            val top = border.substring(border.indexOf(",") + 1, border.indexOf(":")).toInt()
            val sizes = size.split("x")
            val width = sizes[0].toInt()
            val height = sizes[1].toInt()
            for (y in top until height + top) {
                for (x in left until width + left) {
                    fabric[y][x] = fabric[y][x] + 1
                }
            }
        }
        return fabric.map { it.count { t -> t >= 2 } }.sum()
    }

    @Test
    fun part1() {
        assertEquals(105231, numSquareInchesWithinTwoOrMoreClaims(File("files/2018/day3.txt").readText()))
    }
}