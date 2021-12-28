package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day3 {
    @Suppress("SameParameterValue")
    private fun numSquareInchesWithinTwoOrMoreClaims(input: String, dimension: Int): Int {
        val fabric = Array(dimension) { IntArray(dimension) }
        input.lines().forEach {
            val line = ParsedLine(it)
            for (y in line.top until line.height + line.top) {
                for (x in line.left until line.width + line.left) {
                    fabric[y][x] = fabric[y][x] + 1
                }
            }
        }
        return fabric.sumOf { it.count { t -> t >= 2 } }
    }

    class ParsedLine(line: String) {
        val id: Int
        val left: Int
        val top: Int
        val width: Int
        val height: Int

        init {
            val parts = line.split(" ")
            id = parts[0].substring(1).toInt()
            val border = parts[2]
            val size = parts[3]
            left = border.substring(0, border.indexOf(",")).toInt()
            top = border.substring(border.indexOf(",") + 1, border.indexOf(":")).toInt()
            val sizes = size.split("x")
            width = sizes[0].toInt()
            height = sizes[1].toInt()
        }
    }

    private fun claimThatDoesntOverlap(input: String, dimension: Int): Int {
        val fabric = Array(dimension) { IntArray(dimension) }
        val notOverriden = HashSet<Int>()
        input.lines().forEach {
            val line = ParsedLine(it)
            var override = false
            for (y in line.top until line.height + line.top) {
                for (x in line.left until line.width + line.left) {
                    if (fabric[y][x] != 0) {
                        notOverriden.remove(fabric[y][x])
                        override = true
                    }
                    fabric[y][x] = line.id
                }
            }
            if (!override) {
                notOverriden.add(line.id)
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