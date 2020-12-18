package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay13()
}

fun runDay13() {
    val lines = File("aoc2020/day13.txt").readLines()
    val day13 = Day13()
    println("day13part1=${micros(Callable { day13.part1(lines) })}")
    println("day13part2=${micros(Callable { day13.part2(lines) })}")
}

class Day13 {
    fun part1(lines: List<String>): Int {
        val num = lines[0].toInt()
        val arr = lines[1].split(",")
            .filter { it != "x" }
            .map { it.toInt() }
        return findSmallestMultiplyLE(num, arr)
    }

    private fun findSmallestMultiplyLE(num: Int, arr: List<Int>): Int {
        var min = Int.MAX_VALUE
        var res = -1
        arr.forEach {
            var cur = 0
            while (cur < num) {
                cur += it
            }
            if (cur < min) {
                res = it
                min = cur
            }
        }
        return res * (min - num)
    }

    fun part2(lines: List<String>): Long {
        val line = lines[1].split(",")
        val busses = line.indices
            .filter { line[it] != "x" }
            .map { Bus(-it.toLong(), line[it].toLong()) }
            .toMutableList()

        while (busses.size > 1) {
            busses.sort()
            val bus0 = busses.removeAt(0)
            val bus1 = busses.removeAt(0)
            busses.add(offAndFrequency(bus0, bus1))
        }

        return busses[0].offset
    }

    private fun offAndFrequency(bus1: Bus, bus2: Bus): Bus {
        var offset: Long = -1
        var frequency: Long = -1
        var sum1 = bus1.offset
        var sum2 = bus2.offset
        var countMatches = 0
        while (sum1 < 0) {
            sum1 += bus1.frequency
        }
        while (sum2 < 0) {
            sum2 += bus2.frequency
        }
        while (countMatches < 2) {
            if (sum1 < sum2) {
                sum1 += bus1.frequency
            } else {
                sum2 += bus2.frequency
            }
            if (sum1 == sum2) {
                if (countMatches == 0) {
                    offset = sum1
                } else {
                    frequency = sum1 - offset
                }
                countMatches++
            }
        }
        return Bus(offset, frequency)
    }

    class Bus(val offset: Long, val frequency: Long) : Comparable<Bus> {
        override fun compareTo(other: Bus): Int {
            return when {
                other.offset - this.offset > 0 -> -1
                else -> 1
            }
        }

    }
}
