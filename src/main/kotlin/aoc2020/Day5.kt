package aoc2020

import micros
import java.io.File

fun main() {
    runDay5()
}

fun runDay5() {
    val lines = File("files/2020/day5.txt").readLines()

    println("day5part1=${micros { day5part1(lines) }}")
    println("day5part2=${micros { day5part2(lines, 12, 858) }}")
}

fun day5part1(lines: List<String>): Int {
    return lines.maxOf { seatIdOf(it) }
}

fun day5part2(lines: List<String>, low: Int, high: Int): Int {
    val sorted = lines.map { seatIdOf(it) }.sorted()
    for (it in low..high) when {
        sorted[it - low] != it -> return it
    }
    return -1
}

fun seatIdOf(line: String): Int {
    val row = biSearch(line.substring(0, 7), 'F', 'B')
    val col = biSearch(line.substring(7), 'L', 'R')
    return row * 8 + col
}

fun biSearch(trace: String, low: Char, high: Char): Int {
    val steps = trace.length
    var lower = 0
    var upper = (1 shl steps) - 1
    for (i in 0 until steps) when {
        trace[i] == low -> upper = lower + (upper - lower) / 2
        trace[i] == high -> lower = upper - (upper - lower) / 2
    }
    return lower
}
