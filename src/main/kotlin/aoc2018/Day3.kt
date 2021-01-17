package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day3 {
    private fun numSquareInchesWithinTwoOrMoreClaims(input: String, dimension: Int): Int {
        val fabric = Array(dimension) { IntArray(dimension) }
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

    private fun claimThatDoesntOverlap(input: String, dimension: Int): Int {
        val fabric = Array(dimension) { IntArray(dimension) }
        val notOverriden = HashSet<Int>()
        input.lines().forEach {
            val parts = it.split(" ")
            val id = parts[0].substring(1).toInt()
            val border = parts[2]
            val size = parts[3]
            val left = border.substring(0, border.indexOf(",")).toInt()
            val top = border.substring(border.indexOf(",") + 1, border.indexOf(":")).toInt()
            val sizes = size.split("x")
            val width = sizes[0].toInt()
            val height = sizes[1].toInt()
            var override = false
            for (y in top until height + top) {
                for (x in left until width + left) {
                    if (fabric[y][x] != 0) {
                        notOverriden.remove(fabric[y][x])
                        override = true
                    }
                    fabric[y][x] = id
                }
            }
            if (!override) {
                notOverriden.add(id)
            }
        }
        return notOverriden.first()
    }

    @Test
    fun sample() {
        assertEquals(3, claimThatDoesntOverlap("#1 @ 1,3: 4x4\n" +
                "#2 @ 3,1: 4x4\n" +
                "#3 @ 5,5: 2x2", 8))
    }

    @Test
    fun part1() {
        assertEquals(105231, numSquareInchesWithinTwoOrMoreClaims(File("files/2018/day3.txt").readText(), 1000))
    }

    @Test
    fun part2() {
        assertEquals(164, claimThatDoesntOverlap(File("files/2018/day3.txt").readText(), 1000))
    }
}