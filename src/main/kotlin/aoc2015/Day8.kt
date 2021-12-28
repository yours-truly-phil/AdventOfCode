package aoc2015

import java.io.File

fun main() {
    Day8()
        .also { println(it.part1(File("files/2015/day8.txt").readText())) }
        .also { println(it.part2(File("files/2015/day8.txt").readText())) }
}

class Day8 {
    fun part1(input: String): Int {
        val noChars = input.lines().sumOf { it.length }
        val noUnescapedChars = input.lines().sumOf { unescapedStringLength(it) }
        return noChars - noUnescapedChars
    }

    fun part2(input: String): Int {
        val noChars = input.lines().sumOf { it.length }
        val noEscapedChars = input.lines().sumOf { escapedStringLength(it) }
        return noEscapedChars - noChars
    }

    private fun unescapedStringLength(s: String): Int {
        var i = 0
        var count = 0
        while (i < s.length) {
            val r = s.substring(i)
            when {
                s[i] == '"' -> i++
                r.startsWith("\\x") -> {
                    i += 4
                    count++
                }
                r.startsWith("\\") -> {
                    i += 2
                    count++
                }
                else -> {
                    i++
                    count++
                }
            }
        }
        return count
    }

    private fun escapedStringLength(s: String): Int {
        var count = s.length + 2
        s.forEach {
            when (it) {
                '"' -> count++
                '\\' -> count++
            }
        }
        return count
    }
}