package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import java.util.regex.Pattern
import kotlin.test.assertEquals

val pattern: Pattern = Pattern.compile(" +")

class Day4 {
    private fun scoreOfWinningBoard(input: String): Int {
        val blocks = input.split("\n\n")
        val nums = blocks[0].split(",").map(String::toInt)
        val boards = blocks.subList(1, blocks.size).map(::Board)
        nums.forEach { num ->
            for (board in boards) {
                val hasWon = board.check(num)
                if (hasWon) {
                    return board.getSumUnchecked() * num
                }
            }
        }
        return -1
    }

    private fun scoreOfLastWinningBoard(input: String): Int {
        val blocks = input.split("\n\n")
        val nums = blocks[0].split(",").map(String::toInt)
        var boards = blocks.subList(1, blocks.size).map(::Board)
        var lastWinner: Board? = null
        var numLastWinner: Int? = null
        nums.forEach { num ->
            for (board in boards) {
                val hasWon = board.check(num)
                if (hasWon) {
                    lastWinner = board
                    numLastWinner = num
                }
            }
            boards = boards.filterNot(Board::hasWon)
        }
        return lastWinner!!.getSumUnchecked() * numLastWinner!!
    }

    class Board(rows: String) {
        private val board: Array<Array<Cell>>

        init {
            board = rows.lines().map { line ->
                line.split(pattern).filter(String::isNotBlank).map { Cell(it.toInt()) }.toTypedArray()
            }.toTypedArray()
        }

        fun hasWon(): Boolean = board.any { it.all(Cell::isChecked) }
                || (board.indices).any { board.all { row -> row[it].isChecked } }

        fun getSumUnchecked(): Int = board.sumOf { it.filterNot(Cell::isChecked).sumOf(Cell::num) }

        fun check(num: Int): Boolean {
            outer@ for (row in board) {
                for (cell in row) {
                    if (cell.num == num) {
                        cell.isChecked = true
                        if (hasWon()) {
                            return true
                        }
                        break@outer
                    }
                }
            }
            return false
        }
    }

    class Cell(val num: Int) {
        var isChecked = false
    }

    @Test
    fun part1() {
        assertEquals(
            4512, scoreOfWinningBoard(
                """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7""".trimIndent()
            )
        )
        assertEquals(63552, scoreOfWinningBoard(File("files/2021/day4.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(
            1924, scoreOfLastWinningBoard(
                """
7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7""".trimIndent()
            )
        )
        assertEquals(9020, scoreOfLastWinningBoard(File("files/2021/day4.txt").readText()))
    }
}