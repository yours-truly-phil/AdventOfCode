package aoc2016

import java.io.File

fun main() {
    Day3().apply { println(part1(File("files/2016/day3.txt").readText())) }
        .apply { println(part2(File("files/2016/day3.txt").readText())) }
}

class Day3 {
    fun part1(input: String): Int {
        val match = "\\d+".toRegex()
        return input.lines().filter {
            val num = match.findAll(it).map { num -> num.value.toInt() }.toList()
            num[0] + num[1] > num[2] && num[0] + num[2] > num[1] && num[1] + num[2] > num[0]
        }.count()
    }

    fun part2(input: String): Int {
        val match = "\\d+".toRegex()
        val nums = input.lines().map { match.findAll(it).map { n -> n.value.toInt() }.toList() }
        var count = 0
        for (i in 0 until 3) {
            for (y in 0 until nums.size - 3 step 3) {
                val a = nums[y][i]
                val b = nums[y + 1][i]
                val c = nums[y + 2][i]
                if (a + b > c && a + c > b && b + c > a) count++
            }
        }
        return count
    }
}