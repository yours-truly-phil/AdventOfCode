package aoc2016

import java.io.File
import kotlin.math.abs

fun main() {
    Day1().apply { println(part1(File("files/2016/day1.txt").readText())) }
        .apply { println(part2("R8, R4, R4, R8")) }
        .apply { println(part2(File("files/2016/day1.txt").readText())) }
}

class Day1 {
    fun part1(input: String): Int {
        var dir = 0
        var x = 0
        var y = 0
        input.split(", ").forEach {
            if (it.startsWith("R")) dir++ else dir += 3
            dir %= 4
            it.substring(1).toInt().apply {
                when {
                    dir % 4 == 0 -> y -= this
                    dir % 4 == 1 -> x += this
                    dir % 4 == 2 -> y += this
                    dir % 4 == 3 -> x -= this
                }
            }

        }
        return abs(x) + abs(y)
    }

    fun part2(input: String): Int {
        var dir = 0
        var x = 0
        var y = 0
        val visited = arrayListOf<Loc>()
        input.split(", ").forEach {
            if (it.startsWith("R")) dir++ else dir += 3
            dir %= 4
            it.substring(1).toInt().apply {
                when {
                    dir % 4 == 0 -> (y - 1 downTo y - this)
                        .forEach { yy ->
                            Loc(x, yy)
                                .apply { if (visited.contains(this)) return dist() }
                                .apply { visited.add(this) }
                        }.also { y -= this }
                    dir % 4 == 1 -> (x + 1..x + this)
                        .forEach { xx ->
                            Loc(xx, y)
                                .apply { if (visited.contains(this)) return dist() }
                                .apply { visited.add(this) }
                        }.also { x += this }
                    dir % 4 == 2 -> (y + 1..y + this)
                        .forEach { yy ->
                            Loc(x, yy)
                                .apply { if (visited.contains(this)) return dist() }
                                .apply { visited.add(this) }
                        }.also { y += this }
                    dir % 4 == 3 -> (x - 1 downTo x - this)
                        .forEach { xx ->
                            Loc(xx, y)
                                .apply { if (visited.contains(this)) return dist() }
                                .apply { visited.add(this) }
                        }.also { x -= this }
                }
            }
        }
        return -1
    }

    data class Loc(val x: Int, val y: Int) {
        fun dist(): Int = abs(x) + abs(y)
    }
}