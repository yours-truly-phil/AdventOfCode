import java.io.File
import java.lang.System.nanoTime
import java.util.*

fun main() {
    var start = nanoTime()
    val arr = File("aoc2020/day1.txt")
        .readLines()
        .map { s -> s.toInt() }
        .sorted()
        .toIntArray()
    var diff = nanoTime() - start
    println("Parsing and sorting input: ${diff / 1000}µs")

    start = nanoTime()
    println("part1=${part1(arr, 2020)}")
    diff = nanoTime() - start
    println("Finished part 1 in ${diff / 1000}µs")

    start = nanoTime()
    println("part2=${part2(arr, 2020)}")
    diff = nanoTime() - start
    println("Finished part 2 in ${diff / 1000}µs")
}

fun part1(arr: IntArray, total: Int): Int {
    var i = 0
    while (arr[i] < total / 2) {
        val rest = total - arr[i]
        if (Arrays.binarySearch(arr, rest) >= 0) {
            return arr[i] * rest
        }
        i++
    }
    return -1
}

fun part2(arr: IntArray, total: Int): Int {
    var i = 0
    while (i < arr.size && arr[i] < total / 3) {
        val diff = total - arr[i]
        var j = i + 1
        while (j < arr.size && arr[j] < diff / 2) {
            val rest = diff - arr[j]
            if (rest < arr[j]) {
                break
            }
            if (Arrays.binarySearch(arr, rest) >= 0) {
                return arr[i] * arr[j] * rest
            }
            j++
        }
        i++
    }
    return -1
}