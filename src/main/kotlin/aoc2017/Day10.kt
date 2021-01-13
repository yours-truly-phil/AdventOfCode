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