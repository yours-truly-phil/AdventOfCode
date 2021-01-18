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

    @Test
    fun sample() {
        assertEquals(10, remainingUnits("dabAcCaCBAcCcaDA"))
    }

    @Test
    fun part1() {
        assertEquals(-1, remainingUnits(File("files/2018/day5.txt").readText()))
    }
}