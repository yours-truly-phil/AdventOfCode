package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day19 {
    private fun regValue(input: String, regNo: Int): Int {
        val lines = input.lines()
        val ipBind = lines[0].split(" ")[1].toInt()
        val instructions = lines.subList(1, lines.size)
            .map { it.split(" ") }
            .map { Instruction(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt()) }
            .toTypedArray()

        val mem = IntArray(6) { 0 }
        var ip = 0
        while (ip in instructions.indices) {
            mem[ipBind] = ip
            val inst = instructions[ip]
            when (inst.type) {
                "seti" -> mem[inst.c] = inst.a
                "setr" -> mem[inst.c] = mem[inst.a]
                "addi" -> mem[inst.c] = mem[inst.a] + inst.b
                "addr" -> mem[inst.c] = mem[inst.a] + mem[inst.b]
                "muli" -> mem[inst.c] = mem[inst.a] * inst.b
                "mulr" -> mem[inst.c] = mem[inst.a] * mem[inst.b]
                "gtrr" -> if (mem[inst.a] > mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                "eqrr" -> if (mem[inst.a] == mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                else -> println("unknown instruction: $inst")
            }
            ip = mem[ipBind]
            ip++
        }
        return mem[regNo]
    }

    data class Instruction(val type: String, val a: Int, val b: Int, val c: Int)

    @Test
    fun sample() {
        assertEquals(6, regValue("#ip 0\n" +
                "seti 5 0 1\n" +
                "seti 6 0 2\n" +
                "addi 0 1 0\n" +
                "addr 1 2 3\n" +
                "setr 1 0 0\n" +
                "seti 8 0 4\n" +
                "seti 9 0 5", 0))
    }

    @Test
    fun part1() {
        assertEquals(1344, regValue(File("files/2018/day19.txt").readText(), 0))
    }
}