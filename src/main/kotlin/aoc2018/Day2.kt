package aoc2018

import frequencyMap
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day2 {
    private fun checksum(input: String): Int {
        val freqMap = input.lines().map { frequencyMap(it.toCharArray().toTypedArray()) }
        return freqMap.count { it.containsValue(2) } * freqMap.count { it.containsValue(3) }
    }

    private fun findCommonChars(input: String): String {
        val ids = input.lines().sorted().toTypedArray()
        for (i in 0 until ids.size - 1) {
            var countDifferences = 0
            for (j in ids[i].indices) {
                if (ids[i][j] != ids[i + 1][j]) {
                    countDifferences++
                }
                if (countDifferences > 1) {
                    break
                }
            }
            if (countDifferences == 1) {
                val sb = StringBuilder()
                for (j in ids[i].indices) {
                    if (ids[i][j] == ids[i + 1][j]) sb.append(ids[i][j])
                }
                return sb.toString()
            }
        }
        return "n/a"
    }

    @Test
    fun part1() {
        assertEquals(9633, checksum(File("files/2018/day2.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals("lujnogabetpmsydyfcovzixaw", findCommonChars(File("files/2018/day2.txt").readText()))
    }
}