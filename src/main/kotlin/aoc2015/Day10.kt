package aoc2015

import java.io.File

fun main() {
    Day10().also { println(it.part1(File("files/2015/day10.txt").readText())) }
        .also { println(it.part2(File("files/2015/day10.txt").readText())) }
}

class Day10 {
    fun part1(input: String): Int = calcSequence(input, 40).length

    fun part2(input: String): Int = calcSequence(input, 50).length

    private fun calcSequence(input: String, count: Int): String {
        var cur = input
        for (i in 1..count) {
            cur = next(cur)
            println("$i=${cur.length}")
        }
        return cur
    }

    fun next(s: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < s.length) {
            val next = s[i]
            var end = i + 1
            while (end < s.length) {
                if (s[end] != next) {
                    break
                } else {
                    end++
                }
            }
            sb.append(end - i)
            sb.append(next)
            i = end
        }
        return sb.toString()
    }

}