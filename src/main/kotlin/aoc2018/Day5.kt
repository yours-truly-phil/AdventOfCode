package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day5 {
    fun remainingUnits(input: String): Int {
        var s = input
        var noChanges = false
        while (!noChanges) {
            noChanges = true
            val sb = StringBuilder()
            var i = 0
            while (i < s.length) {
                if (i != s.length - 1 && s[i].equals(s[i + 1], ignoreCase = true) && s[i] != s[i + 1]) {
                    i += 2
                    noChanges = false
                } else {
                    sb.append(s[i])
                    i++
                }
            }
            s = sb.toString()
        }
        return s.length
    }

    fun shortestPossible(input: String): Int {
        val chars = input.toLowerCase().toCharArray().distinct()
        var shortest = Int.MAX_VALUE
        chars.asSequence()
            .map { char -> input.filter { !it.equals(char, ignoreCase = true) } }
            .forEach { shortest = minOf(shortest, remainingUnits(it)) }
        return shortest
    }

    @Test
    fun sample() {
        assertEquals(10, remainingUnits("dabAcCaCBAcCcaDA"))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(4, shortestPossible("dabAcCaCBAcCcaDA"))
    }

    @Test
    fun part1() {
        assertEquals(11108, remainingUnits(File("files/2018/day5.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(5094, shortestPossible(File("files/2018/day5.txt").readText()))
    }
}