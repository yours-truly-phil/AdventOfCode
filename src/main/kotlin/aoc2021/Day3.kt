package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day3 {
    private fun getPowerConsumption(input: List<String>): Int {
        val mostCommon = getMostCommonBits(input)
        val gammaRate = mostCommon.joinToString("").toInt(2)
        val epsilonRate = mostCommon.map { if (it == 1) 0 else 1 }.joinToString("").toInt(2)
        return gammaRate * epsilonRate
    }

    private fun getMostCommonBits(input: List<String>): List<Int> {
        val countOnes = IntArray(input[0].length)
        input.forEach { it.forEachIndexed { i, c -> if (c == '1') countOnes[i]++ } }
        val half = input.size / 2
        return countOnes.map { if (it >= half) 1 else 0 }
    }

    private fun getLifeSupportRating(input: List<String>): Int {
        var oxygenGeneratorRating = input
        var co2ScrubbingRate = input
        repeat(input[0].length) {
            if (oxygenGeneratorRating.size > 1) {
                val mostCommon = getMostCommonBit(oxygenGeneratorRating, it)
                oxygenGeneratorRating = oxygenGeneratorRating.filter { c -> c[it] == mostCommon }
            }
            if (co2ScrubbingRate.size > 1) {
                val mostCommon = if (getMostCommonBit(co2ScrubbingRate, it) == '1') '0' else '1'
                co2ScrubbingRate = co2ScrubbingRate.filter { c -> c[it] == mostCommon }
            }
        }
        return oxygenGeneratorRating[0].toInt(2) * co2ScrubbingRate[0].toInt(2)
    }

    private fun getMostCommonBit(input: List<String>, index: Int): Char {
        val numZeros = input.count { it[index] == '0' }
        return if (numZeros > input.size / 2) '0' else '1'
    }

    @Test
    fun part1() {
        assertEquals(
            198,
            getPowerConsumption("00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010".lines())
        )
        assertEquals(2498354, getPowerConsumption(File("files/2021/day3.txt").readLines()))
    }

    @Test
    fun part2() {
        assertEquals(
            230,
            getLifeSupportRating(("00100\n11110\n10110\n10111\n10101\n01111\n00111\n11100\n10000\n11001\n00010\n01010").lines())
        )
        assertEquals(3277956, getLifeSupportRating(File("files/2021/day3.txt").readLines()))
    }
}