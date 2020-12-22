package aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day22Test {

    @Test
    fun part1() {
        val input = """Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10""".trimIndent()
        val day22 = Day22(input)
        assertEquals(306, day22.part1())
        assertEquals(306, day22.part1())
    }

    @Test
    fun noLoops() {
        val input = """Player 1:
43
19

Player 2:
2
29
14""".trimIndent()
        val day22 = Day22(input)
        day22.part2()
    }

    @Test
    fun part2() {
        val input = """Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10""".trimIndent()
        val day22 = Day22(input)
        assertEquals(291, day22.part2())
    }
}