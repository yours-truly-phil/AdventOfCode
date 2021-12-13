package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day13 {
    private fun solve(input: String, isPart1: Boolean): Int {
        val (points, instructions) = input.split("\n\n")
        val vPoints = points.lines().map {
            val (x, y) = it.split(",").map { i -> i.toInt() }
            Vec2i(x, y)
        }.toHashSet()

        val map = Map(vPoints)
//        println("$map\n")
        instructions.lines().forEach {
            val (axis, numString) = it.split(" ").last().split("=")
            val num = numString.toInt()
            if (axis == "y") {
                map.foldY(num)
            } else {
                map.foldX(num)
            }
            println("Number of points: ${map.map.size}")
            if (isPart1) {
                return map.map.size
            }
        }
        println("$map\n")
        return -1
    }

    class Map(val map: MutableSet<Vec2i>) {
        fun foldY(axis: Int) {
            val pointsToFold = map.filter { it.y > axis }
            pointsToFold.forEach {
                map.remove(it)
                map.add(Vec2i(it.x, axis - (it.y - axis)))
            }
        }

        fun foldX(axis: Int) {
            val pointsToFold = map.filter { it.x > axis }
            pointsToFold.forEach {
                map.remove(it)
                map.add(Vec2i(axis - (it.x - axis), it.y))
            }
        }

        override fun toString(): String {
            val maxX = map.maxByOrNull { it.x }!!.x
            val maxY = map.maxByOrNull { it.y }!!.y
            val sb = StringBuilder()
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    if (map.contains(Vec2i(x, y))) {
                        sb.append("#")
                    } else {
                        sb.append(".")
                    }
                }
                sb.append("\n")
            }
            return sb.toString()
        }
    }

    data class Vec2i(val x: Int, val y: Int)

    @Test
    fun part1() {
        assertEquals(
            17, solve(
                """
6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5""".trimIndent(), true
            )
        )
        assertEquals(669, solve(File("files/2021/day13.txt").readText(), true))
    }

    @Test
    fun part2() {
        //#..#.####.####.####..##..#..#..##....##
        //#..#.#....#.......#.#..#.#..#.#..#....#
        //#..#.###..###....#..#....#..#.#.......#
        //#..#.#....#.....#...#....#..#.#.......#
        //#..#.#....#....#....#..#.#..#.#..#.#..#
        //.##..####.#....####..##...##...##...##.
        assertEquals(-1, solve(File("files/2021/day13.txt").readText(), false))
    }
}