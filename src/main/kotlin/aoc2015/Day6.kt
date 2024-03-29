package aoc2015

import aoc2015.Day6.State.OFF
import aoc2015.Day6.State.ON
import java.io.File

fun main() {
    Day6().also { println(it.part1(File("files/2015/day6.txt").readText(), 1000, 1000)) }
        .also { println(it.part2(File("files/2015/day6.txt").readText(), 1000, 1000)) }
}

class Day6 {
    fun part1(input: String, width: Int, height: Int): Int {
        return Array(height) { Array(width) { OFF } }.also { grid ->
                input.lines().map { it.split(" ") }.forEach {
                        executeInstructions(it, grid)
                    }
            }.sumOf { it.count { b -> b == ON } }
    }

    fun part2(input: String, width: Int, height: Int): Int {
        return Array(height) { Array(width) { 0 } }.also { grid ->
                input.lines().map { it.split(" ") }.forEach {
                        executeInstructions2(it, grid)
                    }
            }.sumOf { it.sum() }
    }

    private fun executeInstructions2(parts: List<String>, grid: Array<Array<Int>>) {
        when {
            parts[0] == "turn" -> when {
                parts[1] == "on" -> parseNums(2, parts).also {
                        apply2(it.first[0], it.first[1], it.second[0], it.second[1], grid) { i -> i + 1 }
                    }
                parts[1] == "off" -> parseNums(2, parts).also {
                        apply2(it.first[0], it.first[1], it.second[0], it.second[1], grid) { i -> maxOf(i - 1, 0) }
                    }
            }
            parts[0] == "toggle" -> parseNums(1, parts).also {
                    apply2(it.first[0], it.first[1], it.second[0], it.second[1], grid) { i -> i + 2 }
                }
        }
    }

    private fun executeInstructions(parts: List<String>, grid: Array<Array<State>>) {
        when {
            parts[0] == "turn" -> when {
                parts[1] == "on" -> parseNums(2, parts).also {
                        apply(
                            it.first[0],
                            it.first[1],
                            it.second[0],
                            it.second[1],
                            grid
                        ) { ON }
                    }
                parts[1] == "off" -> parseNums(2, parts).also {
                        apply(
                            it.first[0],
                            it.first[1],
                            it.second[0],
                            it.second[1],
                            grid
                        ) { OFF }
                    }
            }
            parts[0] == "toggle" -> parseNums(1, parts).also {
                    apply(it.first[0], it.first[1], it.second[0], it.second[1], grid) { value ->
                        when (value) {
                            ON -> OFF
                            OFF -> ON
                        }
                    }
                }
        }
    }

    private fun parseNums(idx: Int, it: List<String>): Pair<List<Int>, List<Int>> {
        val lower = it[idx].split(",").map { num -> num.toInt() }
        val upper = it[idx + 2].split(",").map { num -> num.toInt() }
        return Pair(lower, upper)
    }

    private fun apply(y: Int, x: Int, y2: Int, x2: Int, grid: Array<Array<State>>, func: (State) -> State) {
        for (col in y..y2) for (row in x..x2) grid[col][row] = func(grid[col][row])
    }

    private fun apply2(y: Int, x: Int, y2: Int, x2: Int, grid: Array<Array<Int>>, func: (Int) -> Int) {
        for (col in y..y2) for (row in x..x2) grid[col][row] = func(grid[col][row])
    }

    enum class State {
        ON, OFF
    }
}
