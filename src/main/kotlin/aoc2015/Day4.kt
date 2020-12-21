package aoc2015

import java.io.File
import java.security.MessageDigest

fun main() {
    val day4 = Day4(File("files/2015/day4.txt").readText())
    println(day4.part1())
    println(day4.part2())
}

class Day4(val input: String) {
    fun part1(): Int {
        var num = 1
        while (!md5(input.plus(num)).toHex().startsWith("00000")) {
            num++
        }
        return num
    }

    fun part2(): Int {
        var num = 1
        while (!md5(input.plus(num)).toHex().startsWith("000000")) {
            num++
        }
        return num
    }

    private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5")
            .digest(str.toByteArray(Charsets.UTF_8))

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
}