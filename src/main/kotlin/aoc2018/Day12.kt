package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day12 {
    private fun sumOfNumsOfPotsContainingPlant(input: String, gens: Int): Int {
        val pair = getState(input)
        var state = pair.first
        val mappings = pair.second
        val iLeftPtr = intArrayOf(0)
        repeat(gens) {
            state = step(state, mappings, iLeftPtr)
            println("${it + 1}: $state | ${iLeftPtr[0]}")
        }

        var count = 0
        val leftMostIdx = iLeftPtr[0]
        for (i in state.indices) {
            if (state[i] == '#') count += i + leftMostIdx
        }
        return count
    }

    private fun getPotsAfterIterations(input: String, gens: Long): Long {
        val pair = getState(input)
        var state = pair.first
        val mappings = pair.second
        val iLeftPtr = intArrayOf(0)
        for (i in 0 until 200) {
            state = step(state, mappings, iLeftPtr)
            println("${i + 1}: $state | ${iLeftPtr[0]}")
        }

        val potsIndices = ArrayList<Long>()
        val leftMostIdx = iLeftPtr[0]
        for (i in state.indices) {
            if (state[i] == '#') {
                potsIndices += i + leftMostIdx.toLong()
            }
        }
        return potsIndices.sumOf { it + (gens - 200) }
    }

    private fun getState(input: String): Pair<String, Map<String, String>> {
        val paragraphs = input.split("\n\n")
        val state = paragraphs[0].split(" ")[2]
        val mappings = paragraphs[1].lines()
            .map { it.split(" => ") }.associate { it[0] to it[1] }
        println("0: $state")
        return Pair(state, mappings)
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
        assertEquals(325, sumOfNumsOfPotsContainingPlant(
            """initial state: #..#.#..##......###...###

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #""", 20))
    }

    @Test
    fun playground() {
        assertEquals(17228, sumOfNumsOfPotsContainingPlant(File("files/2018/day12.txt").readText(), 400))
        /*
        looks like there comes a pattern that just repeats and 1 right each iteration:
        392: ........#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#.. | 304
393: .......#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#... | 306
394: ......#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#.... | 308
395: .....#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#..... | 310
396: ....#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#. | 312
397: ........#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#.. | 309
398: .......#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#... | 311
399: ......#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#.... | 313
400: .....#.#..............#.#....#.#......#.#....#.#....#.#....#.#....#.#....#.#........#.#....#.#......#.#....#.#.......#.#...........#.#......#.#....#.#.....#.#.....#.#......#.#.......#.#..... | 315
         */
        assertEquals(17228, getPotsAfterIterations(File("files/2018/day12.txt").readText(), 400))
    }

    @Test
    fun part1() {
        assertEquals(2045, sumOfNumsOfPotsContainingPlant(File("files/2018/day12.txt").readText(), 20))
    }

    @Test
    fun part2() {
        assertEquals(2100000000428, getPotsAfterIterations(File("files/2018/day12.txt").readText(), 50_000_000_000))
    }
}