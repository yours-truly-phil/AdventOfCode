package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day23 {
    fun countMul(input: String): Int {
        val instructions = input.lines()
            .map { it.split(" ").toTypedArray() }.toTypedArray()
        val reg = hashMapOf("a" to 0L, "b" to 0L, "c" to 0L,
            "d" to 0L, "e" to 0L, "f" to 0L, "g" to 0L, "h" to 0L)
        var idx = 0
        var count = 0
        while (idx >= 0 && idx < instructions.size) {
            val inst = instructions[idx]
            if (inst[0] == "set") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[2]]!!
                } else {
                    reg[inst[1]] = inst[2].toLong()
                }
            } else if (inst[0] == "sub") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[1]]!! - reg[inst[2]]!!
                } else {
                    reg[inst[1]] = reg[inst[1]]!! - inst[2].toLong()
                }
            } else if (inst[0] == "mul") {
                count++
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[1]]!! * reg[inst[2]]!!
                } else {
                    reg[inst[1]] = reg[inst[1]]!! * inst[2].toLong()
                }
            } else if (inst[0] == "jnz") {
                if (reg.containsKey(inst[1])) {
                    if (reg[inst[1]] != 0L) {
                        idx += inst[2].toInt() - 1
                    }
                } else {
                    if (inst[1].toInt() != 0) {
                        idx += inst[2].toInt() - 1
                    }
                }
            }
            idx++
        }
        return count
    }

    @Test
    fun part1() {
        assertEquals(6724, countMul(File("files/2017/day23.txt").readText()))
    }
}