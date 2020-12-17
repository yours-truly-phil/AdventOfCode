package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay10()
}

fun runDay10() {
    val input = File("aoc2020/day10.txt")
        .readLines()
        .map { it.toInt() }
        .sorted()
        .toIntArray()

    println("day10part1=${micros(Callable { day10part1(input) })}")
    println("day10part2=${micros(Callable { day10part2(input) })}")
}

fun day10part1(sortedArr: IntArray): Int {
    var c1s = 0
    var c3s = 0
    var last = 0
    for (j in sortedArr) {
        when {
            j - last == 3 -> c3s++
            j - last == 1 -> c1s++
        }
        last = j
    }
    return c1s * (c3s + 1)
}

fun day10part2(input: IntArray): Long {
    val arr = add0AndLastNumToArr(input)
    val perms = HashMap<Int, Long>()

    perms[arr[arr.size - 1]] = 1L
    for (i in arr.size - 2 downTo 0) {
        val cur = arr[i]
        var count = 0L
        for (j in cur + 1..cur + 3) {
            count += perms.getOrDefault(j, 0L)
        }
        perms[cur] = count
    }

    return perms.getOrDefault(0, -1L)
}

fun add0AndLastNumToArr(arr: IntArray): IntArray {
    val arr2 = IntArray(arr.size + 2)
    arr2[0] = 0
    arr2[arr2.size - 1] = arr[arr.size - 1] + 3
    arr.copyInto(arr2, 1, 0, arr.size)
    return arr2
}
