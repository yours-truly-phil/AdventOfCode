package aoc2020

import java.io.File

fun main() {
    val day25 = Day25()
    File("files/2020/day25.txt")
        .readText()
        .lines()
        .map { it.toLong() }
        .also { println(day25.part1(it[0], it[1])) }
}

class Day25 {

    val initSubjNum = 7
    val divNum = 20201227

    fun part1(cardPubKey: Long, doorPubKey: Long): Long {
        val loopSizeDoor = loopSize(doorPubKey)
        return decryptKey(cardPubKey, loopSizeDoor)
    }

    private fun decryptKey(key: Long, loopSize: Int): Long {
        var res = 1L
        repeat(loopSize) {
            res *= key
            res %= divNum
        }
        return res
    }

    private fun loopSize(num: Long): Int {
        var res = 1L
        var i = 0
        while (true) {
            i++
            res *= initSubjNum
            res %= divNum
            if (res == num) return i
        }
    }
}