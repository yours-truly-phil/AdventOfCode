package aoc2015

import java.io.File

fun main() {
    Day10().also { println(it.part1(File("files/2015/day10.txt").readText())) }
}

class Day10 {
    fun part1(input: String): Int {
        var cur = input
        repeat(40) {
            cur = next(cur)
        }
        return cur.length
    }

    fun next(s: String): String {
        var res = ""
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
            res += end - i
            res += next
            i = end
        }
        return res
    }

}