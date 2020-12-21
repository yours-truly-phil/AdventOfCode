package aoc2015

import java.io.File

fun main() {
    val day2 = Day2("files/2015/day2.txt")
    println(day2.part1())
    println(day2.part2())
}

class Day2(path: String) {
    val lines = File(path).readLines()

    fun part1(): Int {
        return lines.map { Box(it).calcWrappingAmount() }.sum()
    }

    fun part2(): Int {
        return lines.map { Box(it).ribbon() }.sum()
    }

    class Box(line: String) {
        private val dims = line.split("x")
                .map { it.toInt() }
                .sorted()

        fun calcWrappingAmount(): Int {
            return 3 * dims[0] * dims[1] +
                    2 * dims[1] * dims[2] +
                    2 * dims[0] * dims[2]
        }

        fun ribbon(): Int {
            return dims[0] * dims[1] * dims[2] +
                    2 * dims[0] + 2 * dims[1]
        }
    }
}