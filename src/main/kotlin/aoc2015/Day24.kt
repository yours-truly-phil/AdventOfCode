package aoc2015

import util.PowerSet
import java.io.File
import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {
    PowerSet(arrayOf(5, 4, 3, 2, 1).iterator()).also { while (it.hasNext()) println(it.next().joinToString(",")) }
    Day24().also { println(it.part1(File("files/2015/day24.txt").readText())) }
        .also { println(it.part2(File("files/2015/day24.txt").readText())) }
}

class Day24 {
    fun part1(input: String): Long {
        val nums = input.lines().map { it.toInt() }.toIntArray().sortedArrayDescending()
        val groups = 3
        val groupWeight = nums.sum() / groups
        println("nums=${nums.size} sum=${nums.sum()} targetWeight=$groupWeight" +
                " possibilities=${nums.size.factorial()}")

        val ps = PowerSet(nums.iterator())
        var minNoElements = Int.MAX_VALUE
        var qe = Long.MAX_VALUE
        measureTimeMillis {
            while (ps.hasNext()) {
                val next = ps.next().toIntArray()
                val sum = next.sum()
                val size = next.size
                if (size > minNoElements || size > nums.size / groups) continue
                var nqe = 1L
                for (n in next) nqe *= n
                var newBest = false
                when {
                    groupWeight == sum && size == minNoElements && nqe < qe -> newBest = true
                    groupWeight == sum && size < minNoElements -> newBest = true
                }
                if (newBest) {
                    minNoElements = size
                    qe = nqe
                    println("$minNoElements elements, entanglement=$qe sum=$sum" +
                            " ${next.joinToString(",", "[", "]")}")
                }
            }
        }.also { println("Time: $it ms") }
        return qe
    }

    private fun Int.factorial(): BigInteger {
        var res = BigInteger.ONE
        for (i in 1..this) {
            res = res.multiply(BigInteger(i.toString()))
        }
        return res
    }

    fun part2(input: String): Int {
        return -1
    }
}

