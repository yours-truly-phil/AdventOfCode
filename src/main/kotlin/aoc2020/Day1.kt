package aoc2020

import micros
import java.io.File
import java.lang.System.nanoTime

fun main() {
    runDay1()
}

fun runDay1() {
    val start = nanoTime()
    val arr = File("files/2020/day1.txt")
        .readLines()
        .map { it.toInt() }
        .sorted()
        .toIntArray()
    val diff = nanoTime() - start
    println("Parsing and sorting input: ${diff / 1000}µs")

    println("day1part1=${micros { day1part1(arr, 2020) }}")
    println("day1part2=${micros { day1part2(arr, 2020) }}")
}

fun day1part1(arr: IntArray, total: Int): Int {
    for (num in arr) {
        if (num >= total / 2) break
        val rest = total - num
        if (arr.binarySearch(rest) >= 0) {
            println("num $num rest $rest")
            return num * rest
        }
    }
    return -1
}

fun day1part2(arr: IntArray, total: Int): Int {
    for (i in arr.indices) {
        val a = arr[i]
        if (a >= total / 3) break
        val diff = total - a
        for (j in (i + 1) until arr.size) {
            val b = arr[j]
            val rest = diff - b
            if (b >= diff / 2 || rest < b) break
            if (arr.binarySearch(rest) >= 0) return a * b * rest
        }
    }
    return -1
}