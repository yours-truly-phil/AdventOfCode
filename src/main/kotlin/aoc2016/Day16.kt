package aoc2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16 {
    private fun checksumForDisc(input: String, length: Int): String {
        var res = input
        while (res.length < length) {
            res = foldDragonCurve(res)
        }
        return generateChecksum(res.substring(0, length))
    }

    private fun foldDragonCurve(s: String): String =
        StringBuilder().append(s).append("0").append(reverseNegate(s)).toString()

    private fun reverseNegate(s: String): String =
        s.reversed().toCharArray()
            .map {
                when (it) {
                    '1' -> '0'
                    else -> '1'
                }
            }.toCharArray().joinToString("")

    private fun generateChecksum(s: String): String {
        if (s.length % 2 == 1) return s

        return generateChecksum(StringBuilder().apply {
            (s.indices step 2).forEach {
                when {
                    s[it] == s[it + 1] -> append("1")
                    else -> append("0")
                }
            }
        }.toString())
    }

    @Test
    fun part1() {
        assertEquals("10010110010011110", checksumForDisc("10010000000110000", 272))
    }

    @Test
    fun part2() {
        assertEquals("01101011101100011", checksumForDisc("10010000000110000", 35651584))
    }

    @Test
    fun foldDragonCurve() {
        assertEquals("100", foldDragonCurve("1"))
        assertEquals("001", foldDragonCurve("0"))
        assertEquals("11111000000", foldDragonCurve("11111"))
        assertEquals("1111000010100101011110000", foldDragonCurve("111100001010"))
    }

    @Test
    fun generateChecksumTest() {
        assertEquals("100", generateChecksum("110010110100"))
    }

    @Test
    fun generateChecksumForDiscTest() {
        assertEquals("01100", checksumForDisc("10000", 20))
    }
}