package aoc2021

import getAdjacentFromGridOrDefault
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day20 {
    private fun solve(input: String, steps: Int): Int {
        val (firstLine, img) = input.split("\n\n")
        val algo = firstLine.map { convertToInt(it) }.toIntArray()
        var cur = img.lines().map { it.map { c -> convertToInt(c) }.toTypedArray() }.toTypedArray()
        repeat(steps) {
            val next = Array(cur.size + 2) { Array(cur[0].size + 2) { 0 } }
            for (y in next.indices) {
                for (x in next[y].indices) {
                    val out = if (algo[0] == 1) it % 2 else 0
                    val adjacent = getAdjacentFromGridOrDefault(cur, x - 1, y - 1, out)
                    val enhanceString = adjacent.joinToString("").toInt(2)
                    next[y][x] = algo[enhanceString]
                }
            }
            cur = next
        }
        return cur.sumOf { it.count { p -> p == 1 } }
    }

    private fun convertToInt(c: Char) = when (c) {
        '.' -> 0
        '#' -> 1
        else -> throw IllegalArgumentException("Unknown char: $c")
    }

    val sample = """
..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

#..#.
#....
##..#
..#..
..###""".trimIndent()

    @Test
    fun part1() {
        assertEquals(35, solve(sample, 2))
        assertEquals(5179, solve(File("files/2021/day20.txt").readText(), 2))
    }

    @Test
    fun part2() {
        assertEquals(3351, solve(sample, 50))
        assertEquals(16112, solve(File("files/2021/day20.txt").readText(), 50))
    }
}