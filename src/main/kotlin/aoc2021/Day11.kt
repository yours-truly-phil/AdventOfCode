package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day11 {
    private fun solvePart1(input: List<String>, steps: Int): Int {
        val grid = input.map { it.map { c -> c.toString().toInt() }.toIntArray() }.toTypedArray()
//        println(grid.joinToString("\n", "Before any steps:\n", "\n") { it.joinToString("") })
        var flashes = 0
        repeat(steps) {
            var roundDone = false
            grid.indices.forEach { y -> grid[y].indices.forEach { x -> grid[y][x] += 1 } }
            while (!roundDone) {
                roundDone = true
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] > 9) {
                            roundDone = false
                            flashes++
                            flash(grid, x, y)
                        }
                    }
                }
            }
//            println(grid.joinToString("\n", "After step ${step}:\n", "\n") { it.joinToString("") })
        }
        return flashes
    }

    private fun solvePart2(input: List<String>): Int {
        val grid = input.map { it.map { c -> c.toString().toInt() }.toIntArray() }.toTypedArray()
        repeat(Int.MAX_VALUE) { step ->
            val hasFlashed = Array(grid.size) { BooleanArray(grid[0].size) { false } }
            var roundDone = false
            grid.indices.forEach { y -> grid[y].indices.forEach { x -> grid[y][x] += 1 } }
            while (!roundDone) {
                roundDone = true
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] > 9) {
                            roundDone = false
                            flash(grid, x, y)
                            hasFlashed[y][x] = true
                        }
                    }
                }
            }
            if (hasFlashed.all { row -> row.all { it } }) return step + 1
        }
        throw IllegalStateException("Should not reach here")
    }

    private fun flash(grid: Array<IntArray>, x: Int, y: Int) {
        grid[y][x] = 0

        // top left
        if (x > 0 && y > 0 && grid[y - 1][x - 1] > 0) grid[y - 1][x - 1] += 1
        // top
        if (y > 0 && grid[y - 1][x] > 0) grid[y - 1][x] += 1
        // top right
        if (x < grid[y].lastIndex && y > 0 && grid[y - 1][x + 1] > 0) grid[y - 1][x + 1] += 1
        // left
        if (x > 0 && grid[y][x - 1] > 0) grid[y][x - 1] += 1
        // right
        if (x < grid[y].lastIndex && grid[y][x + 1] > 0) grid[y][x + 1] += 1
        // bottom left
        if (x > 0 && y < grid.lastIndex && grid[y + 1][x - 1] > 0) grid[y + 1][x - 1] += 1
        // bottom
        if (y < grid.lastIndex && grid[y + 1][x] > 0) grid[y + 1][x] += 1
        // bottom right
        if (x < grid[y].lastIndex && y < grid.lastIndex && grid[y + 1][x + 1] > 0) grid[y + 1][x + 1] += 1
    }

    @Test
    fun part1() {
        assertEquals(
            9, solvePart1(
                """
11111
19991
19191
19991
11111""".trimIndent().lines(), 2
            )
        )
        assertEquals(
            1656, solvePart1(
                """
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526""".trimIndent().lines(), 100
            )
        )
        assertEquals(1585, solvePart1(File("files/2021/day11.txt").readLines(), 100))
    }

    @Test
    fun part2() {
        assertEquals(
            195, solvePart2(
                """
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526""".trimIndent().lines()
            )
        )
        assertEquals(382, solvePart2(File("files/2021/day11.txt").readLines()))
    }
}