package aoc2015

import java.io.File

fun main() {
    val day1 = Day1("files/2015/day1.txt")
    println(day1.part1())
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
}