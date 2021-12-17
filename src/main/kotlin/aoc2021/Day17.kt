package aoc2021

import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.test.assertEquals

class Day17 {
    private fun solvePart1(input: String): Int {
        val parts = input.substring(13).split(", ")
        val (x1, x2) = parts[0].substring(2).split("..").map { it.toInt() }
        val (y1, y2) = parts[1].substring(2).split("..").map { it.toInt() }
        println("x1=$x1, x2=$x2, y1=$y1, y2=$y2")
        val maxYVelocity = y1.absoluteValue - 1
        return (maxYVelocity * (maxYVelocity + 1)) / 2
    }

    class Probe(var x: Int, var y: Int, var vX: Int, var vY: Int) {
        fun next() {
            x += vX
            y += vY
            vY -= 1
            if (vX != 0) {
                vX += if (vX > 0) -1 else 1
            }
        }

        fun isCollision(x1: Int, x2: Int, y1: Int, y2: Int): Boolean {
            return x in x1..x2 && y in y1..y2
        }
    }

    private fun solvePart2(input: String): Int {
        val parts = input.substring(13).split(", ")
        val (x1, x2) = parts[0].substring(2).split("..").map { it.toInt() }
        val (y1, y2) = parts[1].substring(2).split("..").map { it.toInt() }
        println("x1=$x1, x2=$x2, y1=$y1, y2=$y2")
        val maxYVelocity = y1.absoluteValue - 1
        val maxXVelocity = x2.absoluteValue
        val minXVelocity = quadr(1.0, 1.0, -2.0 * y2.absoluteValue)[0].roundToInt()
        println("maxYVelocity=$maxYVelocity,  maxXVelocity=$maxXVelocity, minXVelocity=$minXVelocity")
        var count = 0
        for (y in y1..maxYVelocity) {
            for (x in minXVelocity..maxXVelocity) {
                val probe = Probe(0, 0, x, y)
                while (probe.x <= x2 && probe.y >= y1) {
                    probe.next()
                    if (probe.isCollision(x1, x2, y1, y2)) {
                        count++
                        break
                    }
                }
            }
        }
        return count
    }

    private fun quadr(a: Double, b: Double, c: Double): Array<Double> {
        return arrayOf(
            (-b + sqrt(b * b - 4 * a * c)) / (2 * a),
            (-b - sqrt(b * b - 4 * a * c)) / (2 * a)
        )
    }

    @Test
    fun part1() {
        assertEquals(45, solvePart1("target area: x=20..30, y=-10..-5"))
        assertEquals(3160, solvePart1("target area: x=282..314, y=-80..-45"))
    }

    @Test
    fun part2() {
        assertEquals(112, solvePart2("target area: x=20..30, y=-10..-5"))
        assertEquals(1928, solvePart2("target area: x=282..314, y=-80..-45"))
    }
}