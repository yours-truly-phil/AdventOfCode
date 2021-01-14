package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day10 {
    fun mulTwoNumbers(input: String, size: Int): Int {
        val arr = IntArray(size).also { for (i in 0 until size) it[i] = i }
        val nums = input.split(",").map { it.toInt() }.toIntArray()
        var pos = 0
        for ((skip, num) in nums.withIndex()) {
            reverse(arr, pos, num)
            pos += num + skip
            pos %= arr.size
        }
        return arr[0] * arr[1]
    }

    fun part2WithAsciiCodes(input: String, size: Int): String {
        return generateHash(size, input)
    }

    fun knotHash(input: String): String {
        return generateHash(256, input)
    }

    private fun generateHash(size: Int, input: String): String {
        val arr = IntArray(size).also { for (i in 0 until size) it[i] = i }
        val nums = toPart2ASCIIArr(input)
        var pos = 0
        var skip = 0
        for (i in 0 until 64) {
            for (num in nums) {
                reverse(arr, pos, num)
                pos += num + skip
                pos %= arr.size
                skip++
            }
        }
        val res = StringBuilder()
        for (i in 0 until 16) {
            val reducedSlice = reduceSlice(arr, i * 16, i * 16 + 15)
            res.append(shortToHex(reducedSlice))
        }
        return res.toString()
    }

    private fun shortToHex(num: Int): String {
        return num.toString(16).padStart(2, '0')
    }

    private fun toPart2ASCIIArr(input: String): IntArray {
        val list = input.map { it.toInt() }.toMutableList()
        list.addAll(listOf(17, 31, 73, 47, 23))
        return list.toIntArray()
    }

    private fun reduceSlice(arr: IntArray, from: Int, to: Int): Int {
        return arr.sliceArray(IntRange(from, to)).reduce { acc, i -> acc xor i }
    }

    private fun reverse(arr: IntArray, from: Int, length: Int) {
        if (from + length < arr.size) arr.reverse(from, from + length)
        else {
            val seq = IntArray(length)
            for (i in 0 until length) {
                seq[i] = arr[(from + i) % arr.size]
            }
            seq.reverse()
            for (i in 0 until length) {
                arr[(from + i) % arr.size] = seq[i]
            }
        }
    }

    @Test
    fun `reduce part of array with xor`() {
        assertEquals(64, reduceSlice(intArrayOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22), 0, 15))
    }

    @Test
    fun `byte to hex string with leading zeros`() {
        assertEquals("00", shortToHex(0))
        assertEquals("0f", shortToHex(15))
        assertEquals("ff", shortToHex(255))
    }

    @Test
    fun `part 2 samples()`() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", generateHash(256, ""))
        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", generateHash(256, "AoC 2017"))
        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", generateHash(256, "1,2,3"))
        assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", generateHash(256, "1,2,4"))
    }

    @Test
    fun part2() {
        assertEquals("541dc3180fd4b72881e39cf925a50253",
            part2WithAsciiCodes(File("files/2017/day10.txt").readText(), 256))
    }

    @Test
    fun `to part 2 type ascii arr`() {
        val arr = toPart2ASCIIArr("1,2,3")
        assertEquals("49,44,50,44,51,17,31,73,47,23", arr.joinToString(","))
    }

    @Test
    fun `reverse without overlap`() {
        val arr = intArrayOf(0, 1, 2, 3, 4)
        reverse(arr, 1, 3)
        assertEquals("0,3,2,1,4", arr.joinToString(","))
        val arr2 = intArrayOf(0, 1, 2)
        reverse(arr2, 0, 3)
        assertEquals("2,1,0", arr2.joinToString(","))
    }

    @Test
    fun `reverse with overlap`() {
        val arr = intArrayOf(2, 1, 0, 3, 4)
        reverse(arr, 3, 4)
        assertEquals("4,3,0,1,2", arr.joinToString(","))
    }

    @Test
    fun sample() {
        assertEquals(12, mulTwoNumbers("3,4,1,5", 5))
    }

    @Test
    fun part1() {
        assertEquals(23715, mulTwoNumbers(File("files/2017/day10.txt").readText(), 256))
    }
}