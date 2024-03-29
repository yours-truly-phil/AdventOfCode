package aoc2020

import micros
import java.io.File

fun main() {
    runDay3()
}

fun runDay3() {
    val lines = File("files/2020/day3.txt").readLines()

    println("day3part1=${micros { day3part1(lines) }}")
    println("day3part2=${micros { day3part2(lines) }}")
}

fun day3part1(lines: List<String>): Int {
    var x = 0
    var count = 0
    for (i in 1 until lines.size) {
        val line = lines[i]
        x = (x + 3) % line.length
        if (line[x] == '#') {
            count++
        }
    }
    return count
}

fun day3part2(lines: List<String>): Long {
    val actors = arrayOf(
        Actor(1, 1), Actor(3, 1),
        Actor(5, 1), Actor(7, 1), Actor(1, 2)
    )
    for (i in 1 until lines.size) {
        val line = lines[i]
        for (a in actors) {
            if (i % a.y == 0) {
                a.pos = (a.pos + a.x) % line.length
                if (line[a.pos] == '#') {
                    a.count++
                }
            }
        }
    }
    return actors.map { it.count.toLong() }
        .reduce { acc, i -> acc * i }
}

class Actor(val x: Int, val y: Int) {
    var pos = 0
    var count = 0
}