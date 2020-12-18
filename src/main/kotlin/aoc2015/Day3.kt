package aoc2015

import java.io.File

fun main() {
    val day3 = Day3("files/2015/day3.txt")
    println(day3.part1())
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
            houses.add(House(x,y))
        }
        return houses.size
    }

    data class House(var x: Int, var y: Int)
}