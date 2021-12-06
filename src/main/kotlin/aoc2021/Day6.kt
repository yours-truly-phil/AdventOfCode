package aoc2021

import micros
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.sum
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

    private fun populationAfterMatrix(cycles: Int, input: String): Long {
        val startingFish = input.trimEnd().split(',').map { it.toInt() }
        val frequencies = mk.zeros<Long>(9)
        for (num in startingFish) {
            frequencies[num]++
        }
        val mat = mk.ndarray<Long>(mk[
                mk[0, 1, 0, 0, 0, 0, 0, 0, 0],
                mk[0, 0, 1, 0, 0, 0, 0, 0, 0],
                mk[0, 0, 0, 1, 0, 0, 0, 0, 0],
                mk[0, 0, 0, 0, 1, 0, 0, 0, 0],
                mk[0, 0, 0, 0, 0, 1, 0, 0, 0],
                mk[0, 0, 0, 0, 0, 0, 1, 0, 0],
                mk[1, 0, 0, 0, 0, 0, 0, 1, 0],
                mk[0, 0, 0, 0, 0, 0, 0, 0, 1],
                mk[1, 0, 0, 0, 0, 0, 0, 0, 0],
        ])

        return (mk.linalg.pow(mat, cycles) dot frequencies).sum()
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

    @Test
    fun benchmark() {
        println(micros { populationAfter(3, File("files/2021/day6.txt").readText()) })
        println(micros { populationAfterMatrix(3, File("files/2021/day6.txt").readText()) })
        println(micros { populationAfter(1000, File("files/2021/day6.txt").readText()) })
        println(micros { populationAfterMatrix(1000, File("files/2021/day6.txt").readText()) })
    }
}