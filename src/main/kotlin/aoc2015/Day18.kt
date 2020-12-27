package aoc2015

import java.io.File

fun main() {
    Day18().also { println(it.part1(File("files/2015/day18.txt").readText())) }
}

class Day18 {
    fun part1(input: String): Int = parseInput(input)
        .also { grid ->
            repeat(100) {
                for (y in grid.indices) {
                    for (x in grid.indices) {
                        setNext(y, x, grid)
                    }
                }
                for (y in grid.indices) {
                    for (x in grid.indices) {
                        grid[y][x].on = grid[y][x].next
                    }
                }
            }
        }.map { row -> row.filter { light -> light.on }.count() }.sum()

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

    private fun parseInput(input: String): Array<Array<Light>> = input.lines().map { line ->
        line.map { c ->
            Light().apply {
                when (c) {
                    '#' -> on = true
                }
            }
        }.toTypedArray()
    }.toTypedArray()

    class Light {
        var on = false
        var next = false
    }
}