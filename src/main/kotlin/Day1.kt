import java.io.File
import java.lang.System.nanoTime
import java.util.concurrent.Callable

fun main() {
    val start = nanoTime()
    val arr = File("aoc2020/day1.txt")
        .readLines()
        .map { s -> s.toInt() }
        .sorted()
        .toIntArray()
    val diff = nanoTime() - start
    println("Parsing and sorting input: ${diff / 1000}µs")

    println("day1part1=${micros(Callable { day1part1(arr, 2020) })}")
    println("day1part2=${micros(Callable { day1part2(arr, 2020) })}")
}

fun day1part1(arr: IntArray, total: Int): Int {
    for (num in arr) {
        if (num >= total / 2) break
        val rest = total - num
        if (arr.binarySearch(rest) >= 0) return num * rest
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