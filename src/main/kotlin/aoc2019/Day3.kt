package aoc2019

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

class Day3 {
    private fun solvePart1(input: String): Int {
        val (wire0, wire1) = calcWires(input)
        val intersections = wire0.intersect(wire1).filterNot { it == Vec2i(0, 0) }
        return intersections.minOf { it.x.absoluteValue + it.y.absoluteValue }
    }

    private fun solvePart2(input: String): Int {
        val (wire0, wire1) = calcWires(input)
        return wire0.intersect(wire1).filterNot { it == Vec2i(0, 0) }
            .minOf { wire0.find { w0 -> w0 == it }!!.dist + wire1.find { w1 -> w1 == it }!!.dist }
    }

    private fun calcWires(input: String): Pair<MutableSet<Vec2i>, MutableSet<Vec2i>> {
        val (first, second) = input.lines().map { it.split(",").map(::Inst) }
        var pos = Vec2i(0, 0)
        val wire0 = mutableSetOf<Vec2i>()
        for (inst in first) {
            pos = processInstruction(inst, pos, wire0)
        }
        pos = Vec2i(0, 0)
        val wire1 = mutableSetOf<Vec2i>()
        for (inst in second) {
            pos = processInstruction(inst, pos, wire1)
        }
        return Pair(wire0, wire1)
    }

    private fun processInstruction(inst: Inst, pos: Vec2i, wire: MutableSet<Vec2i>): Vec2i {
        var resPos = pos
        repeat(inst.len) {
            when (inst.dir) {
                "R" -> resPos = Vec2i(resPos.x + 1, resPos.y)
                "L" -> resPos = Vec2i(resPos.x - 1, resPos.y)
                "U" -> resPos = Vec2i(resPos.x, resPos.y + 1)
                "D" -> resPos = Vec2i(resPos.x, resPos.y - 1)
            }
            if (!wire.contains(resPos)) {
                resPos.dist = pos.dist + it + 1
                wire.add(resPos)
            }
        }
        return resPos
    }

    class Vec2i(val x: Int, val y: Int, var dist: Int = 0) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Vec2i

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    class Inst(input: String) {
        val dir: String
        val len: Int

        init {
            dir = input.substring(0, 1)
            len = input.substring(1).toInt()
        }
    }

    @Test
    fun part1() {
        assertEquals(
            6, solvePart1(
                """
R8,U5,L5,D3
U7,R6,D4,L4""".trimIndent()
            )
        )
        assertEquals(
            159, solvePart1(
                """
R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83""".trimIndent()
            )
        )
        assertEquals(
            135, solvePart1(
                """
R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
U98,R91,D20,R16,D67,R40,U7,R15,U6,R7""".trimIndent()
            )
        )
        assertEquals(2050, solvePart1(File("files/2019/day3.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(
            30, solvePart2(
                """
R8,U5,L5,D3
U7,R6,D4,L4""".trimIndent()
            )
        )
        assertEquals(
            610, solvePart2(
                """
R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83""".trimIndent()
            )
        )
        assertEquals(
            410, solvePart2(
                """
R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
U98,R91,D20,R16,D67,R40,U7,R15,U6,R7""".trimIndent()
            )
        )
        assertEquals(21666, solvePart2(File("files/2019/day3.txt").readText()))
    }
}