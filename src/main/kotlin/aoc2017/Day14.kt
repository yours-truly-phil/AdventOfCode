package aoc2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14 {
    val day10 = Day10()
    private fun usedSquares(input: String): Int =
        ArrayList<String>()
            .apply { (0 until 128).mapTo(this) { hashToBinary("$input-$it") } }
            .map { it.filter { c -> c == '1' }.count() }.sum()

    fun hashToBinary(s: String): String =
        day10.knotHash(s).toBigInteger(16).toString(2).padStart(128, '0')

    @Test
    fun `hash to binary`() {
        assertEquals("1101010011110111" +
                "0110101111011100" +
                "1011111110000011" +
                "1000111110000100" +
                "0001011011001100" +
                "1111101010001011" +
                "1100011011010001" +
                "1111100111100110", hashToBinary("flqrgnkx-0"))
    }

    @Test
    fun sample() {
        assertEquals(8108, usedSquares("flqrgnkx"))
    }

    @Test
    fun part1() {
        assertEquals(8316, usedSquares("ljoxqyyw"))
    }
}