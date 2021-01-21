package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day12 {
    fun sumOfNumsOfPotsContainingPlant(input: String, gens: Int): Int {
        val paragraphs = input.split("\n\n")
        var state = paragraphs[0].split(" ")[2]
        val mappings = paragraphs[1].lines()
            .map { it.split(" => ") }
            .map { it[0] to it[1] }
            .toMap()
        println("0: $state")
        val iLeftPtr = intArrayOf(0)
        repeat(gens) {
            state = step(state, mappings, iLeftPtr)
            println("${it + 1}: $state | ${iLeftPtr[0]}")
        }

        var count = 0
        val leftMostIdx = iLeftPtr[0]
        for(i in state.indices) {
            if(state[i] == '#') count += i + leftMostIdx
        }
        return count
    }

    fun step(state: String, mappings: Map<String, String>, iLeftPtr: IntArray): String {
        iLeftPtr[0] += 2
        val tmpState = StringBuilder().apply {
            if (!state.startsWith(".....")) {
                append(".....")
                iLeftPtr[0] -= 5
            }
            append(state)
            if (!state.endsWith(".....")) append(".....")
        }.toString()

        return StringBuilder().apply {
            (0 until tmpState.length - 5)
                .map { tmpState.substring(it until it + 5) }
                .forEach {
                    when {
                        mappings.containsKey(it) -> append(mappings[it])
                        else -> append(".")
                    }
                }
        }.toString()
    }

    @Test
    fun sample() {
        assertEquals(325, sumOfNumsOfPotsContainingPlant("initial state: #..#.#..##......###...###\n" +
                "\n" +
                "...## => #\n" +
                "..#.. => #\n" +
                ".#... => #\n" +
                ".#.#. => #\n" +
                ".#.## => #\n" +
                ".##.. => #\n" +
                ".#### => #\n" +
                "#.#.# => #\n" +
                "#.### => #\n" +
                "##.#. => #\n" +
                "##.## => #\n" +
                "###.. => #\n" +
                "###.# => #\n" +
                "####. => #", 20))
    }

    @Test
    fun part1() {
        assertEquals(-1, sumOfNumsOfPotsContainingPlant(File("files/2018/day12.txt").readText(), 20))
    }
}