package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day14 {
    private fun solvePart2(input: String, steps: Int): Long {
        val (polymer, instructions) = input.split("\n\n")
        val rules = instructions.lines().map { it.split(" -> ") }.associate { it[0] to it[1] }
        var pairs = HashMap<String, Long>()
        for (i in 0 until polymer.length - 1) {
            val pair = "${polymer[i]}${polymer[i + 1]}"
            pairs[pair] = pairs.computeIfAbsent(pair) { 0 } + 1
        }
        repeat(steps) {
            val newPairs = HashMap<String, Long>()
            pairs.entries.forEach { e ->
                val toInsert = rules[e.key]
                val pair1 = "${e.key[0]}${toInsert}"
                val pair2 = "${toInsert}${e.key[1]}"
                newPairs[pair1] = newPairs.getOrDefault(pair1, 0) + e.value
                newPairs[pair2] = newPairs.getOrDefault(pair2, 0) + e.value
            }
            pairs = newPairs
        }
        val count = HashMap<Char, Long>()
        pairs.entries.forEach {
            count[it.key[0]] = count.getOrDefault(it.key[0], 0) + it.value
            count[it.key[1]] = count.getOrDefault(it.key[1], 0) + it.value
        }
        return (count.maxOf { it.value } - count.minOf { it.value }) / 2 + 1
    }

    @Test
    fun part1() {
        assertEquals(
            1588, solvePart2(
                """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C""".trimIndent(), 10
            )
        )
        assertEquals(2703, solvePart2(File("files/2021/day14.txt").readText(), 10))
    }

    @Test
    fun part2() {
        assertEquals(
            2188189693529, solvePart2(
                """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C""".trimIndent(), 40
            )
        )
        assertEquals(2984946368465, solvePart2(File("files/2021/day14.txt").readText(), 40))
    }
}