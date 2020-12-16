package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay2()
}

fun runDay2() {
    val lines = File("aoc2020/day2.txt").readLines()

    println("day2part1=${micros(Callable { day2part1(lines) })}")
    println("day2part2=${micros(Callable { day2part2(lines) })}")
}

fun day2part1(lines: List<String>): Int {
    var count = 0
    for (l in lines) {
        val line = Line(l)
        val noChars = line.pw.filter { c -> c == line.char }.count()
        if (noChars >= line.lower && noChars <= line.upper) {
            count++
        }
    }
    return count
}

fun day2part2(lines: List<String>):Int {
    var count = 0
    for (l in lines) {
        val line = Line(l)
        val c1 = line.pw[line.lower - 1]
        val c2 = line.pw[line.upper - 1]
        if(c1 != c2 && (c1 == line.char || c2 == line.char)) {
            count++
        }
    }
    return count
}

class Line(line: String) {
    val upper: Int
    val lower: Int
    val char: Char
    val pw: String

    init {
        val parts = line.split(" ")
        val nums = parts[0].split("-")
        lower = nums[0].toInt()
        upper = nums[1].toInt()
        char = parts[1].substringBefore(":").single()
        pw = parts[2]
    }
}
