package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

@Suppress("DuplicatedCode")
class Day25 {

    //cpy a d
    //cpy 7 c
    //cpy 362 b
    //inc d
    //dec b
    //jnz b -2
    //dec c
    //jnz c -5
    //cpy d a
    //jnz 0 0
    //cpy a b
    //cpy 0 a
    //cpy 2 c
    //jnz b 2
    //jnz 1 6
    //dec b
    //dec c
    //jnz c -4
    //inc a
    //jnz 1 -7
    //cpy 2 b
    //jnz c 2
    //jnz 1 4
    //dec b
    //dec c
    //jnz 1 -4
    //jnz 0 0
    //out b
    //jnz a -19
    //jnz 1 -21

    var last = 1

    private fun minAForClockSignal(input: String): Int {
        val insts = input.lines().mapIndexed { i, s -> i to Instruction(s) }.toMap()

        for (i in 0 until 1000) {
            last = 1
            val register = hashMapOf("a" to i, "b" to 0, "c" to 0, "d" to 0)
            try {
                val res = executeInstructions(insts, register)
                if (res != -1) {
                    return i
                }
            } catch (_: Exception) {
            }
        }
        throw IllegalStateException("No solution found")
    }

    private fun executeInstructions(insts: Map<Int, Instruction>, reg: HashMap<String, Int>): Int {
        var idx = 0
        val memo = mutableSetOf<String>()
        while (true) {
            if (idx >= insts.size || idx < 0) return -1

            val inst = insts[idx]!!
            when (inst.type) {
                "cpy" -> idx += cpy(reg, inst)
                "dec" -> idx += dec(reg, inst)
                "inc" -> idx += inc(reg, inst)
                "jnz" -> idx += jnz(reg, inst)
                "tgl" -> idx += tgl(reg, inst, idx, insts)
                "out" -> idx += printOut(reg, inst)
            }
            val hash = hashRegister(reg, idx)
            if (memo.contains(hash)) {
                return idx
            } else {
                memo.add(hash)
            }
        }
    }

    private fun hashRegister(reg: HashMap<String, Int>, idx: Int): String {
        return "$idx:${reg.map { "${it.key}=${it.value}" }.joinToString(",")}"
    }

    private fun printOut(reg: HashMap<String, Int>, inst: Instruction): Int {
        if (last == reg[inst.args[0]]) {
            throw Exception("wrong code")
        } else {
            last = reg[inst.args[0]]!!
        }
        return 1
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

        override fun toString(): String = "Instruction(type='$type', args=$args)"
    }

    @Test
    fun part1() {
        assertEquals(196, minAForClockSignal(File("files/2016/day25.txt").readText()))
    }
}