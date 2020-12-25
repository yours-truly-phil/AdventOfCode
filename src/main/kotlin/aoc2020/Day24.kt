package aoc2020

import aoc2020.Day24.Color.BLACK
import aoc2020.Day24.Color.WHITE
import java.io.File

fun main() {
    val day24 = Day24()
    println(day24.part1(File("files/2020/day24.txt").readText()))
    println(day24.part2(File("files/2020/day24.txt").readText()))
}

class Day24 {
    private val directions = hashMapOf(
        "ne" to Vec2i(1, 1),
        "e" to Vec2i(0, 2),
        "se" to Vec2i(-1, 1),
        "sw" to Vec2i(-1, -1),
        "w" to Vec2i(0, -2),
        "nw" to Vec2i(1, -1)
    )

    private val tiles = HashMap<Vec2i, Color>()

    fun part1(input: String): Int {
        val lines = input.lines()
        for (line in lines) {
            val target = findTargetCoordinates(line)
            tiles.computeIfPresent(target) { _, u ->
                when (u) {
                    WHITE -> BLACK
                    BLACK -> WHITE
                }
            }
            tiles.computeIfAbsent(target) { BLACK }
        }
        return tiles.filter { entry -> entry.value == BLACK }.count()
    }

    private fun findTargetCoordinates(input: String): Vec2i {
        val target = Vec2i(0, 0)
        var idx = 0
        while (idx < input.length) when {
            input.substring(idx).startsWith("ne") -> {
                target += directions["ne"]!!
                idx += 2
            }
            input.substring(idx).startsWith("e") -> {
                target += directions["e"]!!
                idx++
            }
            input.substring(idx).startsWith("se") -> {
                target += directions["se"]!!
                idx += 2
            }
            input.substring(idx).startsWith("sw") -> {
                target += directions["sw"]!!
                idx += 2
            }
            input.substring(idx).startsWith("w") -> {
                target += directions["w"]!!
                idx++
            }
            input.substring(idx).startsWith("nw") -> {
                target += directions["nw"]!!
                idx += 2
            }
        }
        return target
    }

    fun part2(input: String): Int {
        return -1
    }

    enum class Color {
        WHITE, BLACK
    }

    data class Vec2i(var y: Int, var x: Int)

    operator fun Vec2i.plusAssign(other: Vec2i) {
        this.y += other.y
        this.x += other.x
    }
}