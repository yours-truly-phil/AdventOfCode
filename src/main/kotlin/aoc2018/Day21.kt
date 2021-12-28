package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day21 {
    private fun minRegValueToHaltWithFewestInstructions(input: String): Int {
        val startVal = 4682012
        val mem = intArrayOf(startVal, 0, 0, 0, 0, 0)
        val (ipBind, instructions) = parseInstructions(input)

        var ip = 0
        while (ip in instructions.indices) {
            val inst = instructions[ip]
            mem[ipBind] = ip
            when (inst.type) {
                "seti" -> mem[inst.c] = inst.a
                "setr" -> mem[inst.c] = mem[inst.a]
                "addi" -> mem[inst.c] = mem[inst.a] + inst.b
                "addr" -> mem[inst.c] = mem[inst.a] + mem[inst.b]
                "muli" -> mem[inst.c] = mem[inst.a] * inst.b
                "mulr" -> mem[inst.c] = mem[inst.a] * mem[inst.b]
                "bori" -> mem[inst.c] = mem[inst.a] or inst.b
                "bani" -> mem[inst.c] = mem[inst.a] and inst.b
                "gtir" -> if (inst.a > mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                "gtrr" -> if (mem[inst.a] > mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                "eqri" -> if (mem[inst.a] == inst.b) mem[inst.c] = 1 else mem[inst.c] = 0
                "eqrr" -> {// part1: when this gets called register 1 and 0 have to be equal
                    // reg 1 is always 4682012, so i put 4682012 in reg 0 from the start for it to halt
                    if (mem[inst.a] == mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                }
                else -> println("unknown instruction: $inst")
            }
            ip = mem[ipBind]
            ip++
        }
        return startVal
    }

    private fun mostInstructionsToHalt(input: String): Int {
        val memo = mutableSetOf<Int>()
        val mem = intArrayOf(0, 0, 0, 0, 0, 0)
        val (ipBind, instructions) = parseInstructions(input)

        var ip = 0
        while (ip in instructions.indices) {
            val inst = instructions[ip]
            mem[ipBind] = ip
            when (inst.type) {
                "seti" -> mem[inst.c] = inst.a
                "setr" -> mem[inst.c] = mem[inst.a]
                "addi" -> mem[inst.c] = mem[inst.a] + inst.b
                "addr" -> mem[inst.c] = mem[inst.a] + mem[inst.b]
                "muli" -> mem[inst.c] = mem[inst.a] * inst.b
                "mulr" -> mem[inst.c] = mem[inst.a] * mem[inst.b]
                "bori" -> mem[inst.c] = mem[inst.a] or inst.b
                "bani" -> mem[inst.c] = mem[inst.a] and inst.b
                "gtir" -> if (inst.a > mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                "gtrr" -> if (mem[inst.a] > mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                "eqri" -> if (mem[inst.a] == inst.b) mem[inst.c] = 1 else mem[inst.c] = 0
                "eqrr" -> {
                    if (!memo.add(mem[inst.a])) return memo.last() // find loop
                    if (mem[inst.a] == mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                }
                else -> println("unknown instruction: $inst")
            }
            ip = mem[ipBind]
            ip++
        }
        return -1
    }

    private fun parseInstructions(input: String): Pair<Int, Array<Day19.Instruction>> {
        val lines = input.lines()
        val ipBind = lines[0].split(" ")[1].toInt()
        val instructions = lines.subList(1, lines.size)
            .map { it.split(" ") }
            .map { Day19.Instruction(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt()) }
            .toTypedArray()
        return Pair(ipBind, instructions)
    }

    @Test
    fun part1() {
        assertEquals(4682012, minRegValueToHaltWithFewestInstructions(File("files/2018/day21.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(5363733, mostInstructionsToHalt(File("files/2018/day21.txt").readText()))
    }
}