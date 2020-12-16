package aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6Test {

    @Test
    fun getCommonChars() {
        assertEquals(getCommonChars("abc"), 3)
        assertEquals(getCommonChars("a\nb\nc"), 0)
        assertEquals(getCommonChars("a\na\na\na"), 1)
        assertEquals(getCommonChars("ab\nac"), 1)
        assertEquals(getCommonChars("b"), 1)
    }
}