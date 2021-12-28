package aoc2020

import micros
import java.io.File

fun main() {
    runDay9()
}

fun runDay9() {
    val nums = File("files/2020/day9.txt").readLines().map { it.toLong() }.toLongArray()

    println("day9part1=${micros { day9part1(nums, 25) }}")
    println("day9part2=${micros { day9part2(nums, 25) }}")
}

fun day9part1(arr: LongArray, range: Int): Long {
    for (i in range + 1 until arr.size) {
        val sum = arr[i]
        val subArray = arr.copyOfRange(i - range - 1, i - 1)
        subArray.sort()
        var valid = false
        for (j in i - range - 1 until i - 1) {
            val num = arr[j]
            if (subArray.binarySearch(sum - num) != -1) {
                valid = true
            }
        }
        if (!valid) {
            return sum
        }
    }
    return -1
}

fun day9part2(arr: LongArray, range: Int): Long {
    val invalidNum = day9part1(arr, range)
    for (i in arr.indices) {
        var sum = arr[i]
        var j = i + 1
        do {
            sum += arr[j]
            if (sum == invalidNum) {
                val subArr = arr.copyOfRange(i, j)
                subArr.sort()
                return subArr[0] + subArr[subArr.size - 1]
            }
            j++
        } while (sum < invalidNum && j < arr.size)
    }
    return -1
}


