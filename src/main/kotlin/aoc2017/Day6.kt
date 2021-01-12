package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day6 {
    private fun countRedistributionCycles(input: String): Int {
        val mem = input.split("\t").map { it.toInt() }.toIntArray()
        val seen = HashSet<String>()
        var count = 0
        while (!seen.contains(mem.joinToString(","))) {
            seen.add(mem.joinToString(","))
            val maxIdx = idxOfMaxValue(mem)
            redistribute(maxIdx, mem)
            count++
        }
        return count
    }

    private fun redistribute(maxIdx: Int, mem: IntArray) {
        var v = mem[maxIdx]
        var idx = maxIdx
        mem[maxIdx] = 0
        while (v > 0) {
            idx++
            idx %= mem.size
            mem[idx]++
            v--
        }
    }

    private fun idxOfMaxValue(mem: IntArray): Int {
        var maxIdx = 0
        for (i in 1 until mem.size) {
            if (mem[i] > mem[maxIdx]) maxIdx = i
        }
        return maxIdx
    }

    @Test
    fun `redistribute value in memory`() {
        val arr = intArrayOf(0, 2, 7, 0)
        redistribute(2, arr)
        assertTrue(arr[0] == 2 && arr[1] == 4 && arr[2] == 1 && arr[3] == 2)
    }

    @Test
    fun `index of max value`() {
        assertEquals(0, idxOfMaxValue(intArrayOf()))
        assertEquals(0, idxOfMaxValue(intArrayOf(1)))
        assertEquals(2, idxOfMaxValue(intArrayOf(1, 2, 3)))
        assertEquals(0, idxOfMaxValue(intArrayOf(4, 2, 3)))
    }

    @Test
    fun sample() {
        assertEquals(5, countRedistributionCycles("0\t2\t7\t0"))
    }

    @Test
    fun part1() {
        assertEquals(12841, countRedistributionCycles(File("files/2017/day6.txt").readText()))
    }
}