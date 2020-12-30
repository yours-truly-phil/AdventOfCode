package aoc2016

import java.io.File

fun main() {
    Day8().apply { println(part1(File("files/2016/day8.txt").readText())) }
        .apply { println(part2(File("files/2016/day8.txt").readText())) }
}

class Day8 {
    fun part1(input: String): Int = screen(input).map { it.sumBy { b -> if (b) 1 else 0 } }.sum()

    fun part2(input: String) {
        screen(input).forEach { it.map { b -> if(b) "#" else " " }.apply { println(this.joinToString(" ")) } }
    }

    private fun screen(input: String): Array<BooleanArray> = Array(6) { BooleanArray(50) }
        .also { arr ->
            input.lines().forEach {
                when {
                    it.startsWith("rect") -> it.split(" ")[1].split("x")
                        .apply { rect(this[0].toInt(), this[1].toInt(), arr) }
                    else -> it.split(" ").apply {
                        val index = this[2].split("=")
                        when {
                            index[0] == "x" -> rotCol(index[1].toInt(), this.last().toInt(), arr)
                            else -> rotRow(index[1].toInt(), this.last().toInt(), arr)
                        }
                    }
                }
            }
        }

    private fun rotCol(col: Int, v: Int, screen: Array<BooleanArray>) {
        BooleanArray(screen.size).apply {
            screen.indices.forEach { this[(it + v) % screen.size] = screen[it][col] }
            screen.indices.forEach { screen[it][col] = this[it] }
        }
    }

    private fun rotRow(row: Int, v: Int, screen: Array<BooleanArray>) {
        screen[row] = BooleanArray(screen[row].size).apply {
            screen[row].indices.forEach { this[(it + v) % screen[row].size] = screen[row][it] }
        }
    }

    private fun rect(width: Int, height: Int, screen: Array<BooleanArray>) {
        (0 until height).forEach { y -> (0 until width).forEach { x -> screen[y][x] = true } }
    }
}