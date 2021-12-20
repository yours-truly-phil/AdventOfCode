package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day20 {
    private fun solve(input: String, steps: Int): Int {
        val (firstLine, img) = input.split("\n\n")
        val algo = firstLine.map { convertToInt(it) }.toIntArray()
        var cur = img.lines().map { it.map { c -> convertToInt(c) }.toIntArray() }.toTypedArray()
        repeat(steps) {
            val next = Array(cur.size + 2) { IntArray(cur[0].size + 2) }
            for (y in next.indices) {
                for (x in next[y].indices) {
                    val out = if (algo[0] == 1) it % 2 else 0

                    val tl = if (y - 2 in cur.indices && x - 2 in cur[y - 2].indices) cur[y - 2][x - 2] else out
                    val t = if (y - 2 in cur.indices && x - 1 in cur[y - 2].indices) cur[y - 2][x - 1] else out
                    val tr = if (y - 2 in cur.indices && x in cur[y - 2].indices) cur[y - 2][x] else out
                    val cl = if (y - 1 in cur.indices && x - 2 in cur[y - 1].indices) cur[y - 1][x - 2] else out
                    val c = if (y - 1 in cur.indices && x - 1 in cur[y - 1].indices) cur[y - 1][x - 1] else out
                    val cr = if (y - 1 in cur.indices && x in cur[y - 1].indices) cur[y - 1][x] else out
                    val bl = if (y in cur.indices && x - 2 in cur[y].indices) cur[y][x - 2] else out
                    val b = if (y in cur.indices && x - 1 in cur[y].indices) cur[y][x - 1] else out
                    val br = if (y in cur.indices && x in cur[y].indices) cur[y][x] else out
                    val enhanceString = "$tl$t$tr$cl$c$cr$bl$b$br".toInt(2)
                    next[y][x] = algo[enhanceString]
                }
            }
            cur = next
        }
        return cur.sumBy { it.count { p -> p == 1 } }
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