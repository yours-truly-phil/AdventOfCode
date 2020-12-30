package aoc2016

import java.io.File

fun main() {
    Day9().apply { println(part1(File("files/2016/day9.txt").readText())) }
}

class Day9 {
    fun part1(input: String): Int = decompress(input).length

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
}