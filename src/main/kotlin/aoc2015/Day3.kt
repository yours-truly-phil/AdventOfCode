package aoc2015

import java.io.File

fun main() {
    val day3 = Day3("files/2015/day3.txt")
    println(day3.part1())
    println(day3.part2())
}

class Day3(path: String) {
    private val text = File(path).readText()

    fun part1(): Int {
        var x = 0
        var y = 0
        val houses = HashSet<House>()
        text.forEach {
            when (it) {
                '>' -> x++
                '<' -> x--
                '^' -> y++
                'v' -> y--
            }
            houses.add(House(x, y))
        }
        return houses.size
    }

    fun part2(): Int {
        val santa = Santa(0, 0)
        val robot = Santa(0, 0)
        val houses = HashSet<House>()
        houses.add(House(santa.x, santa.y))
        text.forEachIndexed { i, c ->
            var cur = santa
            if (i % 2 == 1) cur = robot
            when (c) {
                '>' -> cur.x++
                '<' -> cur.x--
                '^' -> cur.y++
                'v' -> cur.y--
            }
            houses.add(House(cur.x, cur.y))
        }
        return houses.size
    }

    data class House(var x: Int, var y: Int)

    class Santa(var x: Int, var y: Int)
}