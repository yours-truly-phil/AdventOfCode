package aoc2020

import aoc2020.Day11.SeatType.*
import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay11()
}

fun runDay11() {
    val lines = File("aoc2020/day11.txt").readLines()

    val day11 = Day11()
    println("day11part1=${micros(Callable { day11.day11part1(lines) })}")
}

class Day11 {

    fun day11part1(lines: List<String>):Int {
        val grid = parseGrid(lines)
        var hasChanged: Boolean
        do {
            hasChanged = false
            for (row in grid) {
                for (seat in row) {
                    val seatChanged = seat.setNext()
                    if (seatChanged) {
                        hasChanged = true
                    }
                }
            }
            for (row in grid) {
                for (seat in row) {
                    seat.toNext()
                }
            }
        } while (hasChanged)

        var count = 0
        for(row in grid) {
            for(seat in row) {
                if(seat.type == FULL) {
                    count ++
                }
            }
        }
        return count
    }

    fun parseGrid(lines: List<String>): Array<Array<Seat>> {
        val grid = Array(lines.size) { Array(lines[0].length) { Seat(FLOOR) } }

        lines.indices.forEach { i ->
            val charr = lines[i].toCharArray()
            charr.indices.forEach { j ->
                when (charr[j]) {
                    '.' -> grid[i][j] = Seat(FLOOR)
                    'L' -> grid[i][j] = Seat(EMPTY)
                    '#' -> grid[i][j] = Seat(FULL)
                }
            }
        }

        grid.indices.forEach { i ->
            grid[i].indices.forEach { j ->
                val seat = grid[i][j]
                if (i > 0 && j > 0)
                    seat.neighbors.add(grid[i - 1][j - 1])
                if (i > 0)
                    seat.neighbors.add(grid[i - 1][j])
                if (i > 0 && j < grid[i - 1].size - 1)
                    seat.neighbors.add(grid[i - 1][j + 1])
                if (j > 0)
                    seat.neighbors.add(grid[i][j - 1])
                if (j < grid[i].size - 1)
                    seat.neighbors.add(grid[i][j + 1])
                if (i < grid.size - 1 && j > 0)
                    seat.neighbors.add(grid[i + 1][j - 1])
                if (i < grid.size - 1)
                    seat.neighbors.add(grid[i + 1][j])
                if (i < grid.size - 1 && j < grid[i + 1].size - 1)
                    seat.neighbors.add(grid[i + 1][j + 1])
            }
        }
        return grid
    }

    enum class SeatType {
        EMPTY, FULL, FLOOR
    }

    data class Seat(var type: SeatType) {

        var next = FLOOR
        val neighbors = ArrayList<Seat>()

        fun setNext(): Boolean {
            var hasChange = false
            if (type == EMPTY
                && neighbors.stream()
                    .noneMatch { it.type == FULL }
            ) {
                next = FULL
                hasChange = true
            } else if (type == FULL
                && neighbors.stream()
                    .filter { it.type == FULL }
                    .count() >= 4
            ) {
                next = EMPTY
                hasChange = true
            }
            return hasChange
        }

        fun toNext() {
            if (next == FULL || next == EMPTY) {
                type = next
            }
        }
    }
}
