package aoc2015

import java.io.File

fun main() {
    val day5 = Day5("files/2015/day5.txt")
    println(day5.part1())
    println(day5.part2())
}

class Day5(path: String) {
    val lines = File(path).readLines()

    private val vowelsMatcher = "(\\w*[aeuio]\\w*){3,}".toRegex()
    private val illegalStrings = arrayOf("ab", "cd", "pq", "xy")

    fun part1(): Int {
        return lines.filter { isValid(it) }.count()
    }

    private fun isValid(str: String): Boolean {
        if (!vowelsMatcher.matches(str)) return false
        for (illegal in illegalStrings) {
            if (str.contains(illegal)) return false
        }
        var lastChar = ' '
        str.forEach {
            if (it == lastChar) return true
            lastChar = it
        }
        return false
    }

    fun part2(): Int {
        return -1
    }
}