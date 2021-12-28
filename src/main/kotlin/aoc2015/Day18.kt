package aoc2015

import java.io.File

fun main() {
    Day18().also { println(it.part1(File("files/2015/day18.txt").readText(), false)) }
        .also { println(it.part1(File("files/2015/day18.txt").readText(), true)) }
}

class Day18 {
    fun part1(input: String, part2: Boolean): Int = parseInput(input, part2).also { grid ->
            repeat(100) {
                for (y in grid.indices) {
                    for (x in grid.indices) {
                        setNext(y, x, grid)
                        if (part2) setCornerOn(grid)
                    }
                }
                for (y in grid.indices) {
                    for (x in grid.indices) {
                        grid[y][x].on = grid[y][x].next
                    }
                }
            }
        }.sumOf { row -> row.count { light -> light.on } }

    private fun setNext(tileY: Int, tileX: Int, grid: Array<Array<Light>>) {
        var countNeighborsOn = 0
        for (y in maxOf(0, tileY - 1)..minOf(grid.size - 1, tileY + 1)) {
            for (x in maxOf(0, tileX - 1)..minOf(grid[y].size - 1, tileX + 1)) {
                if (y != tileY || x != tileX) {
                    if (grid[y][x].on) countNeighborsOn++
                }
            }
        }
        grid[tileY][tileX].also {
            if (it.on) it.next = countNeighborsOn == 2 || countNeighborsOn == 3
            else it.next = countNeighborsOn == 3
        }
    }

    private fun setCornerOn(grid: Array<Array<Light>>) {
        grid.first().first().also { it.on = true }.also { it.next = true }
        grid.first().last().also { it.on = true }.also { it.next = true }
        grid.last().first().also { it.on = true }.also { it.next = true }
        grid.last().last().also { it.on = true }.also { it.next = true }
    }

    private fun parseInput(input: String, part2: Boolean): Array<Array<Light>> = input.lines().map { line ->
        line.map { c ->
            Light().apply {
                when (c) {
                    '#' -> on = true
                }
            }
        }.toTypedArray()
    }.toTypedArray().also { if (part2) setCornerOn(it) }

    class Light {
        var on = false
        var next = false
    }
}