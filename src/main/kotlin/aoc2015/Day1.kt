package aoc2015

import java.io.File

fun main() {
    val day1 = Day1("files/2015/day1.txt")
    println(day1.part1())
    println(day1.part2())
}

class Day1(path: String) {
    val line: String = File(path).readText()

    fun part1(): Int {
        var floor = 0
        line.forEach {
            when (it) {
                '(' -> floor++
                ')' -> floor--
            }
        }
        return floor
    }

    fun part2(): Int {
        var floor = 0
        for (i in line.indices) {
            when (line[i]) {
                '(' -> floor++
                ')' -> floor--
            }
            if (floor < 0) {
                return i + 1
            }
        }
        return -1
    }
}