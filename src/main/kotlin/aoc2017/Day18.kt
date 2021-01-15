package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import java.util.function.BiFunction
import kotlin.test.assertEquals

class Day18 {
    private fun recoveredFrequency(input: String, mem: HashMap<String, Long>, registers: String): Long {
        val instructions = input.lines().map { Instruction(it) }.toTypedArray()
        var idx = 0
        var sound = 0L
        while (idx >= 0 && idx < instructions.size) {
            val inst = instructions[idx]
            when (inst.type) {
                "snd" -> {
                    sound = mem[inst.args[0]]!!
                }
                "set" -> {
                    func(inst, mem, registers) { _, newVal -> newVal }
                }
                "add" -> {
                    func(inst, mem, registers) { reg, newVal -> reg + newVal }
                }
                "mul" -> {
                    func(inst, mem, registers) { reg, newVal -> reg * newVal }
                }
                "mod" -> {
                    func(inst, mem, registers) { reg, newVal -> reg % newVal }
                }
                "rcv" -> {
                    if (isSigValNonZero(inst, mem, registers))
                        return sound
                }
                "jgz" -> {
                    if (isSigValGreaterZero(inst, mem, registers))
                        idx += (if (inst.args[1][0] in registers) mem[inst.args[1]]!! - 1
                        else inst.args[1].toInt() - 1).toInt()
                }
            }
            idx++
        }
        return -1
    }

    private fun countSendOnes(
        input: String,
        mem: HashMap<String, Long>, mem2: HashMap<String, Long>, registers: String,
    ): Int {
        val instructions = input.lines().map { Instruction(it) }.toTypedArray()
        var i1 = 0
        var i2 = 0
        val q1 = ArrayDeque<Long>()
        val q2 = ArrayDeque<Long>()
        var run1 = true
        var run2 = true
        var countSends1 = 0
        while (run1 || run2) {
            if (i1 < 0 || i1 >= instructions.size) run1 = false
            while (run1 && i1 >= 0 && i1 < instructions.size) {
                val inst = instructions[i1]
                when (inst.type) {
                    "snd" -> {
                        if (inst.args[0] in registers) q2.addLast(mem[inst.args[0]]!!)
                        else q2.addLast(inst.args[0].toLong())
                        run2 = true
                    }
                    "set" -> func(inst, mem, registers) { _, newVal -> newVal }
                    "add" -> func(inst, mem, registers) { reg, newVal -> reg + newVal }
                    "mul" -> func(inst, mem, registers) { reg, newVal -> reg * newVal }
                    "mod" -> func(inst, mem, registers) { reg, newVal -> reg % newVal }
                    "rcv" -> {
                        if (q1.isEmpty()) run1 = false
                        else mem[inst.args[0]] = q1.removeFirst()
                    }
                    "jgz" -> if (isSigValGreaterZero(inst, mem, registers))
                        i1 += (if (inst.args[1][0] in registers) mem[inst.args[1]]!! - 1
                        else inst.args[1].toInt() - 1).toInt()
                }
                if (run1) i1++
            }

            if (i2 < 0 || i2 >= instructions.size) run2 = false
            while (run2 && i2 >= 0 && i2 < instructions.size) {
                val inst = instructions[i2]
                when (inst.type) {
                    "snd" -> {
                        if (inst.args[0] in registers) q1.addLast(mem2[inst.args[0]]!!)
                        else q1.addLast(inst.args[0].toLong())
                        countSends1++
                        run1 = true
                    }
                    "set" -> func(inst, mem2, registers) { _, newVal -> newVal }
                    "add" -> func(inst, mem2, registers) { reg, newVal -> reg + newVal }
                    "mul" -> func(inst, mem2, registers) { reg, newVal -> reg * newVal }
                    "mod" -> func(inst, mem2, registers) { reg, newVal -> reg % newVal }
                    "rcv" -> {
                        if (q2.isEmpty()) run2 = false
                        else mem2[inst.args[0]] = q2.removeFirst()
                    }
                    "jgz" -> if (isSigValGreaterZero(inst, mem2, registers))
                        i2 += (if (inst.args[1][0] in registers) mem2[inst.args[1]]!! - 1
                        else inst.args[1].toInt() - 1).toInt()
                }
                if (run2) i2++
            }
        }
        return countSends1
    }

    private fun isSigValGreaterZero(
        inst: Instruction,
        mem: HashMap<String, Long>,
        registers: String,
    ) =
        ((inst.args[0][0] in registers && mem[inst.args[0]]!! > 0L) ||
                (inst.args[0][0] !in registers && inst.args[0].toLong() > 0L))

    private fun isSigValNonZero(
        inst: Instruction,
        mem: HashMap<String, Long>,
        registers: String,
    ) =
        ((inst.args[0][0] in registers && mem[inst.args[0]] != 0L) ||
                (inst.args[0][0] !in registers && inst.args[0].toLong() != 0L))

    private fun func(
        inst: Instruction,
        mem: HashMap<String, Long>,
        registers: String,
        func: BiFunction<Long, Long, Long>,
    ) {
        if (inst.args[1][0] in registers) {
            mem[inst.args[0]] = func.apply(mem[inst.args[0]]!!, mem[inst.args[1]]!!)
        } else {
            mem[inst.args[0]] = func.apply(mem[inst.args[0]]!!, inst.args[1].toLong())
        }
    }

    class Instruction(input: String) {
        val type: String
        val args: Array<String>

        init {
            val parts = input.split(" ")
            type = parts[0]
            args = parts.subList(1, parts.size).toTypedArray()
        }
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(3, countSendOnes("snd 1\n" +
                "snd 2\n" +
                "snd p\n" +
                "rcv a\n" +
                "rcv b\n" +
                "rcv c\n" +
                "rcv d",
            hashMapOf("a" to 0L, "b" to 0L, "c" to 0L, "d" to 0L, "p" to 0),
            hashMapOf("a" to 0L, "b" to 0L, "c" to 0L, "d" to 0L, "p" to 1), "abcdp"))
    }

    @Test
    fun part2() {
        assertEquals(7112, countSendOnes(File("files/2017/day18.txt").readText(),
            hashMapOf("i" to 0L, "a" to 0L, "p" to 0L, "b" to 0L, "f" to 0L),
            hashMapOf("i" to 0L, "a" to 0L, "p" to 1L, "b" to 0L, "f" to 0L), "iapbf"))
    }

    @Test
    fun sample() {
        assertEquals(4, recoveredFrequency("set a 1\n" +
                "add a 2\n" +
                "mul a a\n" +
                "mod a 5\n" +
                "snd a\n" +
                "set a 0\n" +
                "rcv a\n" +
                "jgz a -1\n" +
                "set a 1\n" +
                "jgz a -2", hashMapOf("a" to 0L), "a"))
    }

    @Test
    fun part1() {
        assertEquals(3188, recoveredFrequency(File("files/2017/day18.txt").readText(),
            hashMapOf("i" to 0L, "a" to 0L, "p" to 0L, "b" to 0L, "f" to 0L), "iapbf"))
    }
}