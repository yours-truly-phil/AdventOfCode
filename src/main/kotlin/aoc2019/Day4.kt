package aoc2019

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day4 {
    private fun solvePart1(input: String): Int {
        val (min, max) = input.split("-").map { it.toInt() }
        var count = 0
        outer@ for (i in min..max) {
            val arr = i.toString().toCharArray()
            var hasDouble = false
            for (c in 1 until arr.size) {
                if (arr[c] < arr[c - 1]) {
                    continue@outer
                }
                if (arr[c - 1] == arr[c]) {
                    hasDouble = true
                }
            }
            if (hasDouble) {
                count++
            }
        }
        return count
    }

    private fun solvePart2(input: String): Int {
        val (min, max) = input.split("-").map { it.toInt() }
        var count = 0
        outer@ for (i in min..max) {
            val s = i.toString()
            val doubles = mutableMapOf<Char, Int>()
            for (c in 1 until s.length) {
                if (s[c] < s[c - 1]) {
                    continue@outer
                }
                if (s[c - 1] == s[c]) {
                    if (doubles.containsKey(s[c])) {
                        doubles[s[c]] = doubles[s[c]]!! + 1
                    } else {
                        doubles[s[c]] = 2
                    }
                }
            }
            if (doubles.filter { it.value == 2 }.isNotEmpty()) {
                count++
            }
        }
        return count
    }

    @Test
    fun part1() {
        assertEquals(495, solvePart1("367479-893698"))
    }

    @Test
    fun part2() {
        assertEquals(305, solvePart2("367479-893698"))
    }
}