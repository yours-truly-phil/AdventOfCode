package aoc2020

import aoc2020.SeatType.*
import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay11Part2()
}

fun runDay11Part2() {
    val lines = File("files/2020/day11.txt").readLines()

    val day11part2 = Day11Part2()
    println("day11part1=${micros(Callable { day11part2.day11part2(lines) })}")
}

class Day11Part2 {
    private val dirs = arrayOf(
        Vec2i(-1, -1), Vec2i(0, -1), Vec2i(1, -1),
        Vec2i(-1, 0), Vec2i(1, 0),
        Vec2i(-1, 1), Vec2i(0, 1), Vec2i(1, 1)
    )

    fun day11part2(lines: List<String>): Int {
        val grid = parseSeats(lines)

        var hasChanged: Boolean
        do {
            hasChanged = false
            grid.indices.forEach { y ->
                grid[y].indices.forEach { x ->
                    val noFulls = countFullSeats(x, y, grid)
                    val cur = grid[y][x]
                    when {
                        noFulls >= 5 && cur.type == FULL -> {
                            cur.next = EMPTY
                        }
                        noFulls == 0 && cur.type == EMPTY -> {
                            cur.next = FULL
                        }
                        else -> {
                            cur.next = cur.type
                        }
                    }
                }
            }
            grid.forEach { row ->
                row.forEach {
                    if (it.next != it.type) {
                        hasChanged = true
                        it.type = it.next
                    }
                }
            }
        } while (hasChanged)

        var count = 0
        grid.forEach { row ->
            repeat(
                row.filter { it.type == FULL }.size
            ) { count++ }
        }
        return count
    }

    private fun countFullSeats(x: Int, y: Int, grid: Array<Array<Seat>>): Int {
        return dirs
            .map { getTypeInDirection(x, y, grid, it) }
            .filter { it == FULL }
            .count()
    }

    private fun getTypeInDirection(xx: Int, yy: Int, grid: Array<Array<Seat>>, dir: Vec2i): SeatType {
        var x = xx
        var y = yy
        while (true) {
            x += dir.x
            y += dir.y
            when {
                x >= 0 && x < grid[0].size && y >= 0 && y < grid.size -> {
                    val cur = grid[y][x].type
                    if (cur != FLOOR) return cur
                }
                else -> return FLOOR
            }
        }
    }

    private fun parseSeats(lines: List<String>): Array<Array<Seat>> {
        val grid = Array(lines.size) { Array(lines[0].length) { Seat(EMPTY, EMPTY) } }
        lines.indices.forEach { i ->
            val charr = lines[i].toCharArray()
            charr.indices.forEach { j ->
                when (charr[j]) {
                    '.' -> grid[i][j].type = FLOOR
                    'L' -> grid[i][j].type = EMPTY
                    '#' -> grid[i][j].type = FULL
                }
            }
        }
        return grid
    }
}

enum class SeatType {
    EMPTY, FULL, FLOOR
}

data class Seat(var type: SeatType, var next: SeatType) {
    override fun toString(): String {
        return when (type) {
            FULL -> "#"
            EMPTY -> "L"
            FLOOR -> "."
        }
    }
}

data class Vec2i(var x: Int, var y: Int)