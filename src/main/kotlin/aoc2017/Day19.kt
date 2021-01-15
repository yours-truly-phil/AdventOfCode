package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day19 {
    private fun lettersInPath(input: String): String {
        val grid = input.lines().map { it.toCharArray() }.toTypedArray()

//        val dbg = Array(grid.size) { CharArray(grid.maxOf { arr -> arr.size }) }
//        for (y in dbg.indices) for (x in dbg[y].indices) dbg[y][x] = ' '

        val p = Pos(grid[0].indexOf('|'), 0)
        val dir = Pos(0, 1)
        val sb = StringBuilder()
        while (true) {
            p += dir
            val c = grid[p.y][p.x]
//            dbg[p.y][p.x] = c
            if (c in "ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
                sb.append(c)
                if (c == 'Z') return sb.toString()
            } else if (c == '+') {
//                dbg.forEach { println(it.joinToString("")) }
                updateDir(dir, p, grid)
            }
        }
    }

    private fun countSteps(input: String): Int {
        val grid = input.lines().map { it.toCharArray() }.toTypedArray()
        val p = Pos(grid[0].indexOf('|'), 0)
        val dir = Pos(0, 1)
        var count = 1
        while (true) {
            count++
            p += dir
            val c = grid[p.y][p.x]
            if (c in "ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
                if (c == 'Z') return count
            } else if (c == '+') {
                updateDir(dir, p, grid)
            }
        }
    }

    private fun updateDir(dir: Pos, p: Pos, grid: Array<CharArray>) {
        if (dir.y != 0) {
            if (grid[p.y][p.x - 1] in "+-ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
                dir.left()
            } else {
                dir.right()
            }
        } else if (dir.x != 0) {
            if (grid[p.y - 1][p.x] in "+|ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
                dir.up()
            } else {
                dir.down()
            }
        }
    }

    class Pos(var x: Int, var y: Int) {
        fun add(o: Pos) {
            x += o.x
            y += o.y
        }

        fun up() {
            x = 0
            y = -1
        }

        fun down() {
            x = 0
            y = 1
        }

        fun left() {
            x = -1
            y = 0
        }

        fun right() {
            x = 1
            y = 0
        }
    }

    operator fun Pos.plusAssign(o: Pos) {
        this.x += o.x
        this.y += o.y
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(38, countSteps("     |          \n" +
                "     |  +--+    \n" +
                "     A  |  C    \n" +
                " Z---|----E|--+ \n" +
                "     |  |  |  D \n" +
                "     +B-+  +--+ \n"))
    }

    @Test
    fun sample() {
        assertEquals("ABCDEZ", lettersInPath("     |          \n" +
                "     |  +--+    \n" +
                "     A  |  C    \n" +
                " Z---|----E|--+ \n" +
                "     |  |  |  D \n" +
                "     +B-+  +--+ \n"))
    }

    @Test
    fun part1() {
        assertEquals("HATBMQJYZ", lettersInPath(File("files/2017/day19.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(16332, countSteps(File("files/2017/day19.txt").readText()))
    }
}