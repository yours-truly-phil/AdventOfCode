package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day5 {
    private fun numIntersectionsHorizontal(input: List<String>): Int {
        val grid = Grid(input.map(::Line).filterNot(Line::isDiagonal))
        return grid.numIntersections()
    }

    private fun numIntersections(input: List<String>): Int {
        val grid = Grid(input.map(::Line))
        return grid.numIntersections()
    }

    class Grid(lines: List<Line>) {
        val grid = Array(lines.maxOf { maxOf(it.from.y, it.to.y) } + 1) {
            IntArray(lines.maxOf { maxOf(it.from.x, it.to.x) } + 1) { 0 }
        }

        init {
            lines.forEach {
                when {
                    it.from.x == it.to.x ->
                        (minOf(it.from.y, it.to.y)..maxOf(it.from.y, it.to.y))
                            .forEach { y -> grid[y][it.to.x] += 1 }
                    it.from.y == it.to.y ->
                        (minOf(it.from.x, it.to.x)..maxOf(it.from.x, it.to.x))
                            .forEach { x -> grid[it.to.y][x] += 1 }
                    else -> {
                        val from = if (it.from.x < it.to.x) it.from else it.to
                        val to = if (it.from.x < it.to.x) it.to else it.from
                        (0..to.x - from.x).forEach { i ->
                            if (to.y < from.y) grid[from.y - i][from.x + i] += 1
                            else grid[from.y + i][from.x + i] += 1
                        }
                    }
                }
            }
        }

        fun numIntersections(): Int = grid.sumOf { it.count { num -> num > 1 } }

        override fun toString(): String = grid.joinToString("\n") { row ->
            row.map { if (it == 0) '.' else it.toString() }.joinToString("")
        }
    }

    class Vec2i(val x: Int, val y: Int)
    class Line(input: String) {
        val from: Vec2i
        val to: Vec2i

        init {
            val parts = input.split(" -> ")
            val fromParts = parts[0].split(",")
            val toParts = parts[1].split(",")
            from = Vec2i(fromParts[0].toInt(), fromParts[1].toInt())
            to = Vec2i(toParts[0].toInt(), toParts[1].toInt())
        }

        fun isDiagonal(): Boolean = from.x != to.x && from.y != to.y
    }

    @Test
    fun part1() {
        val lines = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2""".trimIndent().lines()
        assertEquals(5, numIntersectionsHorizontal(lines))
        assertEquals(
            """
.......1..
..1....1..
..1....1..
.......1..
.112111211
..........
..........
..........
..........
222111....""".trimIndent(), Grid(lines.map(::Line).filterNot(Line::isDiagonal)).toString()
        )
        assertEquals(6841, numIntersectionsHorizontal(File("files/2021/day5.txt").readLines()))
    }

    @Test
    fun part2() {
        val lines = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2""".trimIndent().lines()
        assertEquals(
            """
1.1....11.
.111...2..
..2.1.111.
...1.2.2..
.112313211
...1.2....
..1...1...
.1.....1..
1.......1.
222111....""".trimIndent(), Grid(lines.map(::Line)).toString()
        )
        assertEquals(19258, numIntersections(File("files/2021/day5.txt").readLines()))
    }
}