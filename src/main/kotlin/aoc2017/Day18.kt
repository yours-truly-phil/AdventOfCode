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