package aoc2015

import java.io.File
import kotlin.math.sqrt

fun main() {
    Day20().also { println(it.part1(File("files/2015/day20.txt").readText())) }
        .also { println(it.part2(File("files/2015/day20.txt").readText())) }
}

class Day20 {
    fun part1(input: String): Int {
        val num = input.toInt()
        var house = 0
        var sum: Int
        while (true) {
            house++
            sum = 0
            val maxFac = sqrt(house.toDouble() + 1).toInt()
            for (i in 1..maxFac) {
                if (house % i == 0) {
                    sum += i
                    if (i != house / i)
                        sum += house / i
                    if (sum * 10 >= num) {
                        return house
                    }
                }
            }
        }
    }

    fun part2(input: String): Int {
        val num = input.toInt()
        var house = 0
        var sum: Int
        while(true) {
            house++
            sum = 0
            val maxFac = sqrt(house.toDouble() + 1).toInt()
            for(i in 1 ..maxFac) {
                if(house % i == 0) {
                    if(house / i <= 50) {
                        sum += i
                    }
                    if(i <= 50 && i != house / i) {
                        sum += house / i
                    }
                    if(sum * 11 >= num) {
                        return house
                    }
                }
            }
        }
    }
}