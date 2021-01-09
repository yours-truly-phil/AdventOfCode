package aoc2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18 {

    fun noSaveTiles(input: String, row: Int): Int {
        var cur = input
        var count = input.filter { it == '.' }.length
        repeat(row - 1) {
            cur = nextRow(cur)
            count += cur.filter { it == '.' }.length
        }
        return count
    }

    fun nextRow(row: String): String {
        val trapPatterns = arrayOf("^^.", ".^^", "^..", "..^")
        val tr = ".$row."
        val sb = StringBuilder()
        for (i in 1 until tr.length - 1) {
            if (tr.substring(i - 1, i + 2) in trapPatterns) {
                sb.append("^")
            } else {
                sb.append(".")
            }
        }
        return sb.toString()
    }

    @Test
    fun part1() {
        val input =
            "......^.^^.....^^^^^^^^^...^.^..^^.^^^..^.^..^.^^^.^^^^..^^.^.^.....^^^^^..^..^^^..^^.^.^..^^..^^^.."
        assertEquals(1963, noSaveTiles(input, 40))
    }

    @Test
    fun part2() {
        val input =
            "......^.^^.....^^^^^^^^^...^.^..^^.^^^..^.^..^.^^^.^^^^..^^.^.^.....^^^^^..^..^^^..^^.^.^..^^..^^^.."
        assertEquals(20009568, noSaveTiles(input, 400000))
    }

    @Test
    fun samples() {
        assertEquals(6, noSaveTiles("..^^.", 3))
        assertEquals(38, noSaveTiles(".^^.^.^^^^", 10))
    }
}