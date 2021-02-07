package aoc2019

import org.junit.jupiter.api.Test
import java.io.File
import java.util.function.Consumer
import kotlin.test.assertEquals

class Day2 {
    fun valueInPos0(input: String, transform: Consumer<IntArray>): Int {
        val arr = parseMemory(input)
        return run(transform, arr)
    }

    private fun run(transform: Consumer<IntArray>, arr: IntArray): Int {
        transform.accept(arr)
        for (i in arr.indices step 4) {
            when (arr[i]) {
                1 -> arr[arr[i + 3]] = arr[arr[i + 1]] + arr[arr[i + 2]]
                2 -> arr[arr[i + 3]] = arr[arr[i + 1]] * arr[arr[i + 2]]
                99 -> return arr[0]
            }
        }
        return -1
    }

    private fun parseMemory(input: String) = input.split(",").map { it.toInt() }.toIntArray()

    fun nounVerbForResult(input: String, num: Int): Int {
        val arr = parseMemory(input)
        for (noun in 0..99) {
            for (verb in 0..99) {
                if (run({ v ->
                        run {
                            v[1] = noun
                            v[2] = verb
                        }
                    }, arr.clone()) == num) {
                    return 100 * noun + verb
                }
            }
        }
        return 1
    }

    @Test
    fun samples() {
        assertEquals(2, valueInPos0("1,0,0,0,99") {})
        assertEquals(2, valueInPos0("2,3,0,3,99") {})
        assertEquals(2, valueInPos0("2,4,4,5,99,0") {})
        assertEquals(30, valueInPos0("1,1,1,4,99,5,6,0,99") {})
    }

    @Test
    fun part1() {
        assertEquals(2842648, valueInPos0(File("files/2019/day2.txt").readText())
        { arr ->
            run {
                arr[1] = 12
                arr[2] = 2
            }
        })
    }

    @Test
    fun part2() {
        assertEquals(9074, nounVerbForResult(File("files/2019/day2.txt").readText(), 19690720))
    }

}