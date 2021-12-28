package aoc2020

import micros
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun main() {
    runDay12()
}

fun runDay12() {
    val lines = File("files/2020/day12.txt").readLines()
    val day12 = Day12()
    println("day12part1=${micros { day12.part1(lines) }}")
    val day12part2 = Day12Part2()
    println("day12part2=${micros { day12part2.part2(lines) }}")
}

class Day12 {
    fun part1(lines: List<String>): Int {
        val ship = Ship()
        moveShip(lines, ship)
        return ship.pos.manhattenDistance()
    }

    private fun moveShip(lines: List<String>, ship: Ship) {
        lines.forEach {
            val value = it.substring(1).toInt()
            when (it[0]) {
                'N' -> ship.pos.y += value
                'S' -> ship.pos.y -= value
                'E' -> ship.pos.x += value
                'W' -> ship.pos.x -= value
                'L' -> ship.rotate(-value)
                'R' -> ship.rotate(value)
                'F' -> ship.move(value)
            }
        }
    }

    class Vec2(var x: Int, var y: Int) {
        fun manhattenDistance(): Int {
            return x.absoluteValue + y.absoluteValue
        }
    }

    class Ship {
        val pos = Vec2(0, 0)
        private var angle = 90

        fun rotate(degrees: Int) {
            angle += degrees
            angle %= 360
        }

        fun move(value: Int) {
            val radians = Math.toRadians(angle.toDouble())
            pos.y += cos(radians).toInt() * value
            pos.x += sin(radians).toInt() * value
        }
    }
}

class Day12Part2 {
    fun part2(lines: List<String>): Int {
        val ship = Ship()
        moveShip(lines, ship)
        return ship.pos.manhattanDistance()
    }

    private fun moveShip(lines: List<String>, ship: Ship) {
        lines.forEach {
            val value = it.substring(1).toInt()
            when (it[0]) {
                'N' -> ship.wp.y += value
                'S' -> ship.wp.y -= value
                'E' -> ship.wp.x += value
                'W' -> ship.wp.x -= value
                'L' -> ship.wp.rotate(value)
                'R' -> ship.wp.rotate(-value)
                'F' -> ship.move(value)
            }
        }
    }

    class Vec2(var x: Int, var y: Int) {
        fun rotate(degrees: Int) {
            val angle = Math.toRadians(degrees.toDouble())
            val rx = (x * cos(angle)) - (y * sin(angle))
            val ry = (x * sin(angle)) + (y * cos(angle))
            x = rx.roundToInt()
            y = ry.roundToInt()
        }

        fun manhattanDistance(): Int {
            return x.absoluteValue + y.absoluteValue
        }
    }

    class Ship {
        val pos = Vec2(0, 0)
        val wp = Vec2(10, 1)

        fun move(value: Int) {
            pos.x += wp.x * value
            pos.y += wp.y * value
        }
    }
}