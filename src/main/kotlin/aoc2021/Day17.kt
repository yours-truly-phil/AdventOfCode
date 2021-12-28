package aoc2021

import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.test.assertEquals

class Day17 {
    private fun solvePart1(input: String): Int = parseInput(input).let { it.maxYVelocity * (it.maxYVelocity + 1) / 2 }

    class Probe(var x: Int, var y: Int, private var vX: Int, private var vY: Int) {
        fun next() {
            x += vX
            y += vY
            vY -= 1
            if (vX != 0) {
                vX += if (vX > 0) -1 else 1
            }
        }

        fun isCollision(x1: Int, x2: Int, y1: Int, y2: Int): Boolean = x in x1..x2 && y in y1..y2
    }

    class E(
        val x1: Int, val x2: Int, val y1: Int, val y2: Int, val maxYVelocity: Int, val maxXVelocity: Int,
        val minYVelocity: Int, val minXVelocity: Int
    )

    private fun parseInput(input: String): E {
        val parts = input.substring(13).split(", ")
        val (x1, x2) = parts[0].substring(2).split("..").map { it.toInt() }
        val (y1, y2) = parts[1].substring(2).split("..").map { it.toInt() }
        val maxYVelocity = y1.absoluteValue - 1
        val maxXVelocity = x2.absoluteValue
        val minXVelocity = quadr(-2.0 * y2.absoluteValue).roundToInt()
        return E(x1, x2, y1, y2, maxYVelocity, maxXVelocity, y1, minXVelocity)
    }

    private fun quadr(c: Double): Double = (-1.0 + sqrt(1 - 4 * c)) * 0.5

    private fun solvePart2(input: String): Int {
        val e = parseInput(input)
        var count = 0
        (e.minYVelocity..e.maxYVelocity).forEach { y ->
            (e.minXVelocity..e.maxXVelocity)
                .map { x -> Probe(0, 0, x, y) }
                .forEach {
                    while (it.x <= e.x2 && it.y >= e.y1) {
                        it.next()
                        if (it.isCollision(e.x1, e.x2, e.y1, e.y2)) {
                            count++
                            break
                        }
                    }
                }
        }
        return count
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