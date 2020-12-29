package aoc2016

import java.io.File

fun main() {
    Day3().apply { println(part1(File("files/2016/day3.txt").readText())) }
}

class Day3 {
    fun part1(input: String): Int {
        val match = "\\d+".toRegex()
        return input.lines().filter {
            val num = match.findAll(it).map { num -> num.value.toInt() }.toList()
            num[0] + num[1] > num[2] && num[0] + num[2] > num[1] && num[1] + num[2] > num[0]
        }.count()
    }
}