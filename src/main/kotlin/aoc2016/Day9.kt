package aoc2016

import java.io.File

fun main() {
    Day9().apply { println(part1(File("files/2016/day9.txt").readText())) }
        .apply { println(part2("X(8x2)(3x3)ABCY")) }
        .apply { println(part2("(27x12)(20x12)(13x14)(7x10)(1x12)A")) }
        .apply { println(part2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")) }
        .apply { println(part2(File("files/2016/day9.txt").readText())) }
}

class Day9 {
    fun part1(input: String): Int = decompress(input).length

    fun part2(input: String): Long = decompress2(input)

    private fun decompress(s: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < s.length) {
            if (s[i] == '(') {
                val closeBracketIdx = s.indexOf(')', i + 1)
                val nums = s.substring(i + 1, closeBracketIdx).split("x")
                val length = nums[0].toInt()
                val count = nums[1].toInt()
                val copyUntil = closeBracketIdx + 1 + length
                val copyMe = s.substring(closeBracketIdx + 1, copyUntil)
                repeat(count) { sb.append(copyMe) }
                i = copyUntil
            } else {
                sb.append(s[i])
                i++
            }
        }
        return sb.toString()
    }

    private fun decompress2(s: String): Long {
        val multis = IntArray(s.length) { 1 }
        var i = 0
        while (i < s.length) {
            if (s[i] == '(') {
                val closeBracketIdx = s.indexOf(')', i + 1)
                val nums = s.substring(i + 1, closeBracketIdx).split("x")
                val length = nums[0].toInt()
                val multi = nums[1].toInt()
                val continueAt = closeBracketIdx + 1
                for (m in continueAt until continueAt + length) {
                    multis[m] *= multi
                }
                for (del in i until continueAt) {
                    multis[del] = 0
                }
                i = continueAt
            } else {
                i++
            }
        }
        return multis.map { it.toLong() }.sum()
    }
}