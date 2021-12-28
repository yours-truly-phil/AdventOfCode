package aoc2015

import java.io.File
import java.util.*

fun main() {
    Day11().also { println(it.part1(File("files/2015/day11.txt").readText())) }
        .also { println(it.part1(it.part1(File("files/2015/day11.txt").readText()))) }
}

class Day11 {
    private val abc = "abcdefghijklmnopqrstuvwxyz"

    fun part1(input: String): String {
        return nextPw(input)
    }

    private fun nextPw(pw: String): String {
        val next = pw.toCharArray()
        do {
            increment(next)
        } while (!isValid(next.joinToString("")))
        return next.joinToString("")
    }

    private fun increment(pw: CharArray): CharArray {
        if (pw[pw.size - 1] != abc[abc.length - 1]) {
            pw[pw.size - 1] = abc[abc.indexOf(pw[pw.size - 1]) + 1]
        } else {
            pw[pw.size - 1] = abc[0]
            val newFront = increment(pw.copyOfRange(0, pw.size - 1))
            for (i in newFront.indices) {
                pw[i] = newFront[i]
            }
        }
        return pw
    }

    private fun isValid(pw: String): Boolean {
        if (pw.length != 8) return false
        if (pw.contains("i") || pw.contains("o") || pw.contains("l")) return false
        if (pw != pw.lowercase(Locale.getDefault())) return false
        if (!threeInRow(pw)) return false
        if (!twoPairs(pw)) return false

        return true
    }

    private fun twoPairs(pw: String): Boolean {
        for (i in 0 until pw.length - 3)
            if (pw[i] == pw[i + 1])
                for (y in i + 2 until pw.length - 1) when {
                    pw[y] == pw[y + 1] -> return true
                }
        return false
    }

    private fun threeInRow(pw: String): Boolean {
        (0 until pw.length - 3).forEach {
            when {
                abc.indexOf(pw[it]) == abc.indexOf(pw[it + 1]) - 1
                        && abc.indexOf(pw[it + 1]) == abc.indexOf(pw[it + 2]) - 1 -> return true
            }
        }
        return false
    }
}