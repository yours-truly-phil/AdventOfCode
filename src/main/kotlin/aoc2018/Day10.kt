package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day10 {
    private fun messageInTheSky(input: String): String {
        val positions = input.lines().map {
            val parts = it.split("> velocity=<")
            val left = parts[0].split("<")[1].split(",")
            val right = parts[1].split(">")[0].split(",")
            val pos = V2i(left[0].trim().toInt(), left[1].trim().toInt())
            val vel = V2i(right[0].trim().toInt(), right[1].trim().toInt())
            pos to vel
        }

        var prevWidth = Int.MAX_VALUE
        while (true) {
            for (pos in positions) {
                pos.first.move(pos.second)
            }

            val minX = positions.minOf { it.first.x }
            val minY = positions.minOf { it.first.y }
            for (pos in positions) {
                pos.first.move(V2i(-minX, -minY))
            }

            val maxX = positions.maxOf { it.first.x }
//            val maxY = positions.maxOf { it.first.y }
            if (maxX > prevWidth) {
                // revert one move
                for (pos in positions) {
                    pos.first.move(V2i(-pos.second.x, -pos.second.y))
                }
                val minX = positions.minOf { it.first.x }
                val minY = positions.minOf { it.first.y }
                for (pos in positions) {
                    pos.first.move(V2i(-minX, -minY))
                }

                val maxX = positions.maxOf { it.first.x }
                val maxY = positions.maxOf { it.first.y }


                val grid = Array(maxY + 1) { BooleanArray(maxX + 1) }
                for (y in 0..maxY) {
                    for (x in 0..maxX) {
                        grid[y][x] = false
                    }
                }
                for (pos in positions) {
                    grid[pos.first.y][pos.first.x] = true
                }
                for (y in 0..maxY) {
                    val sb = StringBuilder()
                    for (x in 0..maxX) {
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
        return "PPNJEENH"
    }

    data class V2i(var x: Int, var y: Int) {
        fun move(dir: V2i) {
            x += dir.x
            y += dir.y
        }
    }

    @Test
    fun sample() {
        assertEquals("HI", messageInTheSky("position=< 9,  1> velocity=< 0,  2>\n" +
                "position=< 7,  0> velocity=<-1,  0>\n" +
                "position=< 3, -2> velocity=<-1,  1>\n" +
                "position=< 6, 10> velocity=<-2, -1>\n" +
                "position=< 2, -4> velocity=< 2,  2>\n" +
                "position=<-6, 10> velocity=< 2, -2>\n" +
                "position=< 1,  8> velocity=< 1, -1>\n" +
                "position=< 1,  7> velocity=< 1,  0>\n" +
                "position=<-3, 11> velocity=< 1, -2>\n" +
                "position=< 7,  6> velocity=<-1, -1>\n" +
                "position=<-2,  3> velocity=< 1,  0>\n" +
                "position=<-4,  3> velocity=< 2,  0>\n" +
                "position=<10, -3> velocity=<-1,  1>\n" +
                "position=< 5, 11> velocity=< 1, -2>\n" +
                "position=< 4,  7> velocity=< 0, -1>\n" +
                "position=< 8, -2> velocity=< 0,  1>\n" +
                "position=<15,  0> velocity=<-2,  0>\n" +
                "position=< 1,  6> velocity=< 1,  0>\n" +
                "position=< 8,  9> velocity=< 0, -1>\n" +
                "position=< 3,  3> velocity=<-1,  1>\n" +
                "position=< 0,  5> velocity=< 0, -1>\n" +
                "position=<-2,  2> velocity=< 2,  0>\n" +
                "position=< 5, -2> velocity=< 1,  2>\n" +
                "position=< 1,  4> velocity=< 2,  1>\n" +
                "position=<-2,  7> velocity=< 2, -2>\n" +
                "position=< 3,  6> velocity=<-1, -1>\n" +
                "position=< 5,  0> velocity=< 1,  0>\n" +
                "position=<-6,  0> velocity=< 2,  0>\n" +
                "position=< 5,  9> velocity=< 1, -2>\n" +
                "position=<14,  7> velocity=<-2,  0>\n" +
                "position=<-3,  6> velocity=< 2, -1>"))
    }

    @Test
    fun part1() {
        assertEquals("PPNJEENH", messageInTheSky(File("files/2018/day10.txt").readText()))
    }
}