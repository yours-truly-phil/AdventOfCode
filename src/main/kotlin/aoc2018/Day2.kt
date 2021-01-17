package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day2 {
    fun func(input: String): Int {
        val freqMap = input.lines().map { charFrequencyMap(it) }
        return freqMap.filter { it.containsValue(2) }.count() * freqMap.filter { it.containsValue(3) }.count()
    }

    fun charFrequencyMap(chars: String): Map<Char, Int> =
        HashMap<Char, Int>().apply {
            chars.forEach {
                this.putIfAbsent(it, 0)
                this[it] = this[it]!! + 1
            }

        }

    @Test
    fun part1() {
        assertEquals(9633, func(File("files/2018/day2.txt").readText()))
    }
}