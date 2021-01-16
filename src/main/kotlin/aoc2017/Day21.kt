package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day21 {
    fun countPixelsOn(input: String, steps: Int): Int {
        var square = Square(".#./..#/###")

        val rules = input.lines().map {
            val parts = it.split(" => ")
            Square(parts[0]) to Square(parts[1])
        }.groupBy { it.first.s.size }

        repeat(steps) {
            if (square.size() % 2 == 0) {
                val parts = divideIntoSquares(square, 2)
                val res = applyRules(parts, rules)
                square = mergeSquares(res)
            } else if (square.size() % 3 == 0) {
                val parts = divideIntoSquares(square, 3)
                val res = applyRules(parts, rules)
                square = mergeSquares(res)
            }
        }

        return square.countOn()
    }

    private fun mergeSquares(squares: ArrayList<ArrayList<Square>>): Square {
        val res = ArrayList<String>()
        for (row in squares) {
            for (rowNum in 0 until row.first().size()) {
                val sb = StringBuilder()
                for (square in row) {
                    sb.append(square.s[rowNum].joinToString(""))
                }
                res.add(sb.toString())
            }
        }
        return Square(res.joinToString("/"))
    }

    private fun applyRules(
        squares: MutableList<MutableList<Square>>,
        rules: Map<Int, List<Pair<Square, Square>>>,
    ): ArrayList<ArrayList<Square>> {
        val res = ArrayList<ArrayList<Square>>()
        for (row in squares) {
            res.add(ArrayList())
            for (square in row) {
                res.last().add(applyRule(square, rules))
            }
        }
        return res
    }

    private fun applyRule(square: Square, rules: Map<Int, List<Pair<Square, Square>>>): Square {
        val possiblesRules = rules.filter { it.key == square.size() }.map { it.value }.flatten()
        for (rule in possiblesRules) {
            repeat(4) {
                square.rot()
                if (square.toString() == rule.first.toString()) {
                    return Square(rule.second.toString())
                }
            }
            square.flip()
            repeat(4) {
                square.rot()
                if (square.toString() == rule.first.toString()) {
                    return Square(rule.second.toString())
                }
            }
        }
        throw Exception("no match found for square $square " +
                "in ${possiblesRules.joinToString(",") { it.first.toString() }}")
    }

    private fun divideIntoSquares(
        square: Square,
        size: Int,
    ): MutableList<MutableList<Square>> {
        val parts = ArrayList<MutableList<Square>>()
        for (y in 0 until square.size() / size) {
            parts.add(ArrayList())
            for (x in 0 until square.size() / size) {
                parts.last().add(createSubSquare(y, x, size, square))
            }
        }
        return parts
    }

    private fun createSubSquare(
        startRow: Int,
        startCol: Int,
        size: Int,
        square: Square,
    ): Square {
        val rows = ArrayList<String>()
        for (y in startRow * size until startRow * size + size) {
            val sb = StringBuilder()
            for (x in startCol * size until startCol * size + size) {
                sb.append(square.s[y][x])
            }
            rows.add(sb.toString())
        }
        return Square(rows.joinToString("/"))
    }

    class Square(line: String) {
        var s: Array<CharArray> = line.split("/").map { it.toCharArray() }.toTypedArray()
        fun size(): Int = s.size

        fun flip() {
            s.forEach { it.reverse() }
        }

        fun rot() {
            val rotated = Array(s.size) { CharArray(s.size) }
            for (rowIdx in s.indices) {
                for (charIdx in s.indices) {
                    rotated[charIdx][s.size - 1 - rowIdx] = s[rowIdx][charIdx]
                }
            }
            s = rotated
        }

        override fun toString(): String {
            return s.joinToString("/") { it.joinToString("") }
        }

        fun countOn(): Int {
            return s.map { it.filter { c -> c == '#' }.count() }.sum()
        }
    }

    @Test
    fun `apply rule`() {
        val rules = mapOf(2 to listOf(Pair(Square("../.#"), Square("##./#../..."))),
            3 to listOf(Pair(Square(".#./..#/###"), Square("#..#/..../..../#..#"))))
        val square = Square(".#./..#/###")
        val res = applyRule(square, rules)
        assertEquals("#..#/..../..../#..#", res.toString())
    }

    @Test
    fun `apply rule flip and rot`() {
        val rules = mapOf(2 to listOf(Pair(Square("../.#"), Square("##./#../..."))),
            3 to listOf(Pair(Square(".#./..#/###"), Square("#..#/..../..../#..#"))))
        val square = Square("###/..#/.#.")
        val res = applyRule(square, rules)
        assertEquals("#..#/..../..../#..#", res.toString())
    }

    @Test
    fun `merge squares to one`() {
        val squares = arrayListOf(arrayListOf(Square("##/##"), Square("AA/AA")),
            arrayListOf(Square("BB/BB"), Square("CC/CC")))
        val merged = mergeSquares(squares)
        assertEquals("##AA/##AA/BBCC/BBCC", merged.toString())
    }

    @Test
    fun `rotate square by 90 deg`() {
        val square = Square("..#/.#./#..").also { it.rot() }
        assertEquals("#../.#./..#", square.toString())
    }

    @Test
    fun `partition square into sub squares`() {
        val twos = divideIntoSquares(Square("#..#/.##./.##./####"), 2)
        assertEquals("#./.#", twos[0][0].toString())
        assertEquals(".#/#.", twos[0][1].toString())
        assertEquals(".#/##", twos[1][0].toString())
        assertEquals("#./##", twos[1][1].toString())
        val threes = divideIntoSquares(Square(".#.#.#/#.#.#./..##../#####./.#####/......"), 3)
        assertEquals(".#./#.#/..#", threes[0][0].toString())
        assertEquals("#.#/.#./#..", threes[0][1].toString())
        assertEquals("###/.##/...", threes[1][0].toString())
        assertEquals("##./###/...", threes[1][1].toString())
    }

    @Test
    fun `create sub square at idx with size`() {
        assertEquals("#./.#", createSubSquare(0, 0, 2, Square("#..#/.#../..#./####")).toString())
        assertEquals("#./##", createSubSquare(1, 1, 2, Square("#..#/.#../..#./####")).toString())
        assertEquals(".../###/...", createSubSquare(0, 0, 3, Square(".../###/...")).toString())
    }

    @Test
    fun sample() {
        assertEquals(12, countPixelsOn("../.# => ##./#../...\n" +
                ".#./..#/### => #..#/..../..../#..#", 2))
    }

    @Test
    fun part1() {
        assertEquals(197, countPixelsOn(File("files/2017/day21.txt").readText(), 5))
    }

    @Test
    fun part2() {
        assertEquals(3081737, countPixelsOn(File("files/2017/day21.txt").readText(), 18))
    }
}