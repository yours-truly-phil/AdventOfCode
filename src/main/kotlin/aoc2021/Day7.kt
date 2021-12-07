package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

class Day7 {
    private fun leastFuel(input: String): Int {
        val nums = input.split(",").map { it.toInt() }
        var min = Int.MAX_VALUE
        for (depth in nums.minOrNull()!!..nums.maxOrNull()!!) {
            var diff = 0
            for (num in nums) {
                diff += (num - depth).absoluteValue
                if (diff >= min) break
            }
            if (diff < min) {
                min = diff
            }
        }
        return min
    }

    private fun leastFuel2(input: String): Int {
        val nums = input.split(",").map { it.toInt() }
        var min = Int.MAX_VALUE
        for (depth in nums.minOrNull()!!..nums.maxOrNull()!!) {
            var cost = 0
            for (num in nums) {
                val diff = (num - depth).absoluteValue
                cost += (diff * (diff + 1)) / 2
                if (cost >= min) break
            }
            if (cost < min) {
                min = cost
            }
        }
        return min
    }

    @Test
    fun part1() {
        assertEquals(37, leastFuel("16,1,2,0,4,2,7,1,2,14"))
        assertEquals(326132, leastFuel(File("files/2021/day7.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(168, leastFuel2("16,1,2,0,4,2,7,1,2,14"))
        assertEquals(88612508, leastFuel2(File("files/2021/day7.txt").readText()))
    }
}