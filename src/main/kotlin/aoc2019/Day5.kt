package aoc2019

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day5 {
    private fun solvePart1(input: String, sysIn: Int = 1): Int {
        return run(input.split(",").map { it.toInt() }.toIntArray(), sysIn)
    }

    private fun run(arr: IntArray, sysIn: Int): Int {
        var i = 0
        while (true) {
            val inst = arr[i].toString().padStart(5, '0')
            val opcode = inst.substring(3).toInt()
            val b = Character.getNumericValue(inst[1])
            val c = Character.getNumericValue(inst[2])
            when (opcode) {
                1 -> {
                    arr[arr[i + 3]] = getParam(c, arr, i + 1) + getParam(b, arr, i + 2)
                    i += 4
                }
                2 -> {
                    arr[arr[i + 3]] = getParam(c, arr, i + 1) * getParam(b, arr, i + 2)
                    i += 4
                }
                3 -> {
                    arr[arr[i + 1]] = sysIn
                    i += 2
                }
                4 -> {
                    val out = getParam(c, arr, i + 1)
                    if (out == 0) {
                        i += 2
                    } else if (arr[i + 2] == 99) {
                        return out
                    } else {
                        throw IllegalStateException("Unexpected out: $out")
                    }
                }
                5 -> {
                    val p1 = getParam(c, arr, i + 1)
                    if (p1 != 0) {
                        i = getParam(b, arr, i + 2)
                    } else {
                        i += 3
                    }
                }
                6 -> {
                    val p1 = getParam(c, arr, i + 1)
                    if (p1 == 0) {
                        i = getParam(b, arr, i + 2)
                    } else {
                        i += 3
                    }
                }
                7 -> {
                    val p1 = getParam(c, arr, i + 1)
                    val p2 = getParam(b, arr, i + 2)
                    arr[arr[i + 3]] = if (p1 < p2) 1 else 0
                    i += 4
                }
                8 -> {
                    val param1 = getParam(c, arr, i + 1)
                    val param2 = getParam(b, arr, i + 2)
                    arr[arr[i + 3]] = if (param1 == param2) 1 else 0
                    i += 4
                }
                else -> throw IllegalStateException("Unexpected opcode: $opcode")
            }
        }
    }

    private fun getParam(c: Int, arr: IntArray, i: Int) = if (c == 0) arr[arr[i]] else arr[i]

    @Test
    fun part1() {
        assertEquals(12234644, solvePart1(File("files/2019/day5.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(3508186, solvePart1(File("files/2019/day5.txt").readText(), 5))
    }
}