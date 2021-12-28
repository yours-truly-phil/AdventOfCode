package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day10 {
    private fun timeForMessageInTheSky(input: String): Int {
        val positions = input.lines().map {
            val parts = it.split("> velocity=<")
            val left = parts[0].split("<")[1].split(",")
            val right = parts[1].split(">")[0].split(",")
            val pos = V2i(left[0].trim().toInt(), left[1].trim().toInt())
            val vel = V2i(right[0].trim().toInt(), right[1].trim().toInt())
            pos to vel
        }

        var count = 0
        var prevWidth = Int.MAX_VALUE
        while (true) {
            for (pos in positions) {
                pos.first.move(pos.second)
            }

            moveToMin(positions)

            val maxX = positions.maxOf { it.first.x }

            if (maxX > prevWidth) {
                // revert one move
                for (pos in positions) {
                    pos.first.move(V2i(-pos.second.x, -pos.second.y))
                }

                moveToMin(positions)

                val maxX1 = positions.maxOf { it.first.x }
                val maxY1 = positions.maxOf { it.first.y }

                val grid = Array(maxY1 + 1) { BooleanArray(maxX1 + 1) }
                for (y in 0..maxY1) {
                    for (x in 0..maxX1) {
                        grid[y][x] = false
                    }
                }
                for (pos in positions) {
                    grid[pos.first.y][pos.first.x] = true
                }
                for (y in 0..maxY1) {
                    val sb = StringBuilder()
                    for (x in 0..maxX1) {
                        if (grid[y][x]) {
                            sb.append("#")
                        } else {
                            sb.append(".")
                        }
                    }
                    println(sb.toString())

                }
                println()
                break
            } else {
                prevWidth = maxX
                count++
            }
        }

        /*
        #####...#####...#....#.....###..######..######..#....#..#....#
        #....#..#....#..##...#......#...#.......#.......##...#..#....#
        #....#..#....#..##...#......#...#.......#.......##...#..#....#
        #....#..#....#..#.#..#......#...#.......#.......#.#..#..#....#
        #####...#####...#.#..#......#...#####...#####...#.#..#..######
        #.......#.......#..#.#......#...#.......#.......#..#.#..#....#
        #.......#.......#..#.#......#...#.......#.......#..#.#..#....#
        #.......#.......#...##..#...#...#.......#.......#...##..#....#
        #.......#.......#...##..#...#...#.......#.......#...##..#....#
        #.......#.......#....#...###....######..######..#....#..#....#
         */
        return count
    }

    private fun moveToMin(positions: List<Pair<V2i, V2i>>) {
        val minX1 = positions.minOf { it.first.x }
        val minY1 = positions.minOf { it.first.y }
        for (pos in positions) {
            pos.first.move(V2i(-minX1, -minY1))
        }
    }

    data class V2i(var x: Int, var y: Int) {
        fun move(dir: V2i) {
            x += dir.x
            y += dir.y
        }
    }

    @Test
    fun sample() {
        assertEquals(
            3, timeForMessageInTheSky(
                """position=< 9,  1> velocity=< 0,  2>
position=< 7,  0> velocity=<-1,  0>
position=< 3, -2> velocity=<-1,  1>
position=< 6, 10> velocity=<-2, -1>
position=< 2, -4> velocity=< 2,  2>
position=<-6, 10> velocity=< 2, -2>
position=< 1,  8> velocity=< 1, -1>
position=< 1,  7> velocity=< 1,  0>
position=<-3, 11> velocity=< 1, -2>
position=< 7,  6> velocity=<-1, -1>
position=<-2,  3> velocity=< 1,  0>
position=<-4,  3> velocity=< 2,  0>
position=<10, -3> velocity=<-1,  1>
position=< 5, 11> velocity=< 1, -2>
position=< 4,  7> velocity=< 0, -1>
position=< 8, -2> velocity=< 0,  1>
position=<15,  0> velocity=<-2,  0>
position=< 1,  6> velocity=< 1,  0>
position=< 8,  9> velocity=< 0, -1>
position=< 3,  3> velocity=<-1,  1>
position=< 0,  5> velocity=< 0, -1>
position=<-2,  2> velocity=< 2,  0>
position=< 5, -2> velocity=< 1,  2>
position=< 1,  4> velocity=< 2,  1>
position=<-2,  7> velocity=< 2, -2>
position=< 3,  6> velocity=<-1, -1>
position=< 5,  0> velocity=< 1,  0>
position=<-6,  0> velocity=< 2,  0>
position=< 5,  9> velocity=< 1, -2>
position=<14,  7> velocity=<-2,  0>
position=<-3,  6> velocity=< 2, -1>"""
            )
        )
    }

    @Test
    fun part1() {
        assertEquals(10375, timeForMessageInTheSky(File("files/2018/day10.txt").readText()))
    }
}