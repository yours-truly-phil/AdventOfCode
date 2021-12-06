package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day6 {
    private fun populationAfter(cycles: Int, input: String): Long {
        var fishes = LongArray(9) { 0L }
        input.split(",").map { it.toInt() }.forEach { fishes[it] += 1L }
        repeat(cycles) {
            val newFishes = LongArray(9) { 0L }
            for (i in fishes.indices) {
                if (i == 0) {
                    newFishes[8] += fishes[i]
                    newFishes[6] += fishes[i]
                } else {
                    newFishes[i - 1] += fishes[i]
                }
            }
            fishes = newFishes
        }
        return fishes.sum()
    }

    @Test
    fun part1() {
        assertEquals(5934, populationAfter(80, "3,4,3,1,2"))
        assertEquals(388739, populationAfter(80, File("files/2021/day6.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(26984457539, populationAfter(256, "3,4,3,1,2"))
        assertEquals(1741362314973, populationAfter(256, File("files/2021/day6.txt").readText()))
    }
}