package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day23 {
    fun valueForSafe(input: String, register: HashMap<String, Int>): Int {
        val insts = input.lines().mapIndexed { i, s -> i to Instruction(s) }.toMap()

        executeInstructions(insts, register)

        return register["a"]!!
    }

    fun executeInstructions(insts: Map<Int, Instruction>, reg: HashMap<String, Int>) {
        var idx = 0
        while (true) {
            if (idx >= insts.size || idx < 0) return

            val inst = insts[idx]!!
            when (inst.type) {
                "cpy" -> idx += cpy(reg, inst)
                "dec" -> idx += dec(reg, inst)
                "inc" -> idx += inc(reg, inst)
                "jnz" -> idx += jnz(reg, inst)
                "tgl" -> idx += tgl(reg, inst, idx, insts)
            }
        }
    }

    private fun tgl(reg: HashMap<String, Int>, inst: Instruction, idx: Int, insts: Map<Int, Instruction>): Int {
        if (insts.containsKey(idx + reg[inst.args[0]]!!)) {
            val toggleMe = insts[idx + reg[inst.args[0]]!!]!!
            if (toggleMe.args.size == 1) {
                if (toggleMe.type == "inc") toggleMe.type = "dec"
                else toggleMe.type = "inc"
            } else if (toggleMe.args.size == 2) {
                if (toggleMe.type == "jnz") toggleMe.type = "cpy"
                else toggleMe.type = "jnz"
            }
        }
        return 1
    }

    private fun jnz(reg: HashMap<String, Int>, inst: Instruction): Int {
        val run = if (inst.args[0] in "abcd") {
            reg[inst.args[0]]!! != 0
        } else {
            inst.args[0].toInt() != 0
        }
        return if (run) {
            if (inst.args[1] in "abcd") {
                reg[inst.args[1]]!!
            } else {
                inst.args[1].toInt()
            }
        } else {
            1
        }
    }

    private fun cpy(reg: HashMap<String, Int>, inst: Instruction): Int {
        if (inst.args[0] in "abcd" && inst.args[1] in "abcd") {
            reg[inst.args[1]] = reg[inst.args[0]]!!
        } else if (inst.args[1] in "abcd") {
            reg[inst.args[1]] = inst.args[0].toInt()
        }
        return 1
    }

    private fun dec(reg: HashMap<String, Int>, inst: Instruction): Int {
        reg[inst.args[0]] = reg[inst.args[0]]!! - 1
        return 1
    }

    private fun inc(reg: HashMap<String, Int>, inst: Instruction): Int {
        reg[inst.args[0]] = reg[inst.args[0]]!! + 1
        return 1
    }

    class Instruction(line: String) {
        var type: String
        val args = ArrayList<String>()

        init {
            val parts = line.split(" ")
            type = parts[0]
            for (i in 1 until parts.size) args.add(parts[i])
        }

        override fun toString(): String {
            return "Instruction(type='$type', args=$args)"
        }
    }

    @Test
    fun sample() {
        assertEquals(3, valueForSafe("cpy 2 a\n" +
                "tgl a\n" +
                "tgl a\n" +
                "tgl a\n" +
                "cpy 1 a\n" +
                "dec a\n" +
                "dec a", hashMapOf("a" to 7, "b" to 0, "c" to 0, "d" to 0)))
    }

    @Test
    fun `input from day12`() {
        assertEquals(318117, valueForSafe("cpy 1 a\n" +
                "cpy 1 b\n" +
                "cpy 26 d\n" +
                "jnz c 2\n" +
                "jnz 1 5\n" +
                "cpy 7 c\n" +
                "inc d\n" +
                "dec c\n" +
                "jnz c -2\n" +
                "cpy a c\n" +
                "inc a\n" +
                "dec b\n" +
                "jnz b -2\n" +
                "cpy c b\n" +
                "dec d\n" +
                "jnz d -6\n" +
                "cpy 17 c\n" +
                "cpy 18 d\n" +
                "inc a\n" +
                "dec d\n" +
                "jnz d -2\n" +
                "dec c\n" +
                "jnz c -5", hashMapOf("a" to 7, "b" to 0, "c" to 0, "d" to 0)))
        assertEquals(9227771, valueForSafe("cpy 1 a\n" +
                "cpy 1 b\n" +
                "cpy 26 d\n" +
                "jnz c 2\n" +
                "jnz 1 5\n" +
                "cpy 7 c\n" +
                "inc d\n" +
                "dec c\n" +
                "jnz c -2\n" +
                "cpy a c\n" +
                "inc a\n" +
                "dec b\n" +
                "jnz b -2\n" +
                "cpy c b\n" +
                "dec d\n" +
                "jnz d -6\n" +
                "cpy 17 c\n" +
                "cpy 18 d\n" +
                "inc a\n" +
                "dec d\n" +
                "jnz d -2\n" +
                "dec c\n" +
                "jnz c -5", hashMapOf("a" to 7, "b" to 0, "c" to 1, "d" to 0)))
    }

    @Test
    fun part1() {
        assertEquals(14445, valueForSafe(File("files/2016/day23.txt").readText(),
            hashMapOf("a" to 7, "b" to 0, "c" to 0, "d" to 0)))
    }

    @Test
    fun part2() {
        println("7=${
            valueForSafe(File("files/2016/day23.txt").readText(),
                hashMapOf("a" to 7, "b" to 0, "c" to 0, "d" to 0))
        }")
        println("8=${
            valueForSafe(File("files/2016/day23.txt").readText(),
                hashMapOf("a" to 8, "b" to 0, "c" to 0, "d" to 0))
        }")
        println("9=${
            valueForSafe(File("files/2016/day23.txt").readText(),
                hashMapOf("a" to 9, "b" to 0, "c" to 0, "d" to 0))
        }")
        println("10=${
            valueForSafe(File("files/2016/day23.txt").readText(),
                hashMapOf("a" to 10, "b" to 0, "c" to 0, "d" to 0))
        }")
        println("11=${
            valueForSafe(File("files/2016/day23.txt").readText(),
                hashMapOf("a" to 11, "b" to 0, "c" to 0, "d" to 0))
        }")
        assertEquals(479011005, valueForSafe(File("files/2016/day23.txt").readText(),
            hashMapOf("a" to 12, "b" to 0, "c" to 0, "d" to 0)))

        // or just 65 * 99 + 12! = 479011005 (because cpy 95 c and jnz 99 d and start of a=12
    }
}