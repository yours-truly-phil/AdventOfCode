package aoc2018

import aoc2018.Day16.OP.*
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class Day16 {
    fun behavesLikeMinOpCodes(input: String, num: Int): Int = input
        .split("\n\n\n\n")[0]
        .split("\n\n")
        .count { possibleOpCodes(it) >= num }

    fun valueInRegister(input: String, register: Int): Int {
        val parts = input.split("\n\n\n\n")
        val samples = parts[0].split("\n\n")
        val program = parts[1].lines()

        val possibleCodes = hashMapOf(
            0 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            1 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            2 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            3 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            4 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            5 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            6 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            7 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            8 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            9 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            10 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            11 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            12 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            13 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            14 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR),
            15 to hashSetOf(ADDR,
                ADDI,
                MULR,
                MULI,
                BANR,
                BANI,
                BORR,
                BORI,
                SETR,
                SETI,
                GTIR,
                GTRI,
                GTRR,
                EQIR,
                EQRI,
                EQRR)
        )

        samples.forEach { excludeOpCodes(it, possibleCodes) }
        val opCodes = HashMap<Int, OP>()
        while (possibleCodes.values.any { it.size > 1 }) {
            val codes = possibleCodes.filter { it.value.size == 1 }
            for (code in codes) {
                opCodes[code.key] = possibleCodes.remove(code.key)!!.first()
                possibleCodes.forEach { it.value.remove(opCodes[code.key]) }
            }
        }
        possibleCodes.forEach { k, v -> opCodes[k] = v.first() }
        opCodes.forEach { (k, v) -> println("$k -> $v") }
        /*
        0 -> ADDI
        1 -> BANI
        2 -> GTIR
        3 -> BORR
        4 -> EQRR
        5 -> BORI
        6 -> GTRR
        7 -> SETR
        8 -> MULI
        9 -> SETI
        10 -> BANR
        11 -> GTRI
        12 -> EQIR
        13 -> EQRI
        14 -> ADDR
        15 -> MULR
         */
        val mem = IntArray(4) { 0 }
        program.map { it.split(" ").map { s -> s.toInt() }.toIntArray() }
            .forEach {
                when (it[0]) {
                    0 -> addi(it, mem)
                    1 -> bani(it, mem)
                    2 -> gtir(it, mem)
                    3 -> borr(it, mem)
                    4 -> eqrr(it, mem)
                    5 -> bori(it, mem)
                    6 -> gtrr(it, mem)
                    7 -> setr(it, mem)
                    8 -> muli(it, mem)
                    9 -> seti(it, mem)
                    10 -> banr(it, mem)
                    11 -> gtri(it, mem)
                    12 -> eqir(it, mem)
                    13 -> eqri(it, mem)
                    14 -> addr(it, mem)
                    15 -> mulr(it, mem)
                }
            }
        return mem[register]
    }

    private fun excludeOpCodes(sample: String, possibleCodes: HashMap<Int, HashSet<OP>>) {
        val (before, after, opcodes) = parseSample(sample)
        if (!isAddr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(ADDR)
        if (!isAddi(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(ADDI)
        if (!isMulr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(MULR)
        if (!isMuli(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(MULI)
        if (!isBanr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(BANR)
        if (!isBani(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(BANI)
        if (!isBorr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(BORR)
        if (!isBori(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(BORI)
        if (!isSetr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(SETR)
        if (!isSeti(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(SETI)
        if (!isGtir(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(GTIR)
        if (!isGtri(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(GTRI)
        if (!isGtrr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(GTRR)
        if (!isEqir(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(EQIR)
        if (!isEqri(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(EQRI)
        if (!isEqrr(opcodes, before.clone(), after)) possibleCodes[opcodes[0]]!!.remove(EQRR)
    }

    enum class OP {
        ADDR, ADDI, MULR, MULI, BANR, BANI, BORR, BORI, SETR, SETI,
        GTIR, GTRI, GTRR, EQIR, EQRI, EQRR
    }

    private fun possibleOpCodes(s: String): Int {
        val (before, after, opcodes) = parseSample(s)
        var count = 0
        if (isAddr(opcodes, before.clone(), after)) count++
        if (isAddi(opcodes, before.clone(), after)) count++
        if (isMulr(opcodes, before.clone(), after)) count++
        if (isMuli(opcodes, before.clone(), after)) count++
        if (isBanr(opcodes, before.clone(), after)) count++
        if (isBani(opcodes, before.clone(), after)) count++
        if (isBorr(opcodes, before.clone(), after)) count++
        if (isBori(opcodes, before.clone(), after)) count++
        if (isSetr(opcodes, before.clone(), after)) count++
        if (isSeti(opcodes, before.clone(), after)) count++
        if (isGtir(opcodes, before.clone(), after)) count++
        if (isGtri(opcodes, before.clone(), after)) count++
        if (isGtrr(opcodes, before.clone(), after)) count++
        if (isEqir(opcodes, before.clone(), after)) count++
        if (isEqri(opcodes, before.clone(), after)) count++
        if (isEqrr(opcodes, before.clone(), after)) count++
        return count
    }

    private fun parseSample(s: String): Triple<IntArray, IntArray, IntArray> {
        val l = s.lines()
        val before = l[0].substring(l[0].indexOf("[") + 1, l[0].indexOf("]"))
            .split(", ").map { it.toInt() }.toIntArray()
        val after = l[2].substring(l[2].indexOf("[") + 1, l[2].indexOf("]"))
            .split(", ").map { it.toInt() }.toIntArray()
        val opcodes = l[1].split(" ").map { it.toInt() }.toIntArray()
        return Triple(before, after, opcodes)
    }

    private fun abcPossibleRegister(c: IntArray) = c[1] in 0..3 && c[2] in 0..3 && c[3] in 0..3

    private fun acPossibleRegister(c: IntArray) = c[1] in 0..3 && c[3] in 0..3

    private fun bcPossibleRegister(c: IntArray) = c[2] in 0..3 && c[3] in 0..3

    private fun compareBeforeAfter(before: IntArray, after: IntArray): Boolean =
        before.joinToString(" ") == after.joinToString(" ")

    private fun isAddr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            before[c[3]] = before[c[1]] + before[c[2]]
            compareBeforeAfter(before, after)
        } else false

    private fun addr(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] + mem[c[2]]
    }

    @Test
    fun `test is addr`() {
        assertEquals(true, isAddr(intArrayOf(9, 1, 2, 3), intArrayOf(3, 2, 1, 1), intArrayOf(3, 2, 1, 3)))
        assertEquals(false, isAddr(intArrayOf(9, 1, 2, 3), intArrayOf(3, 2, 1, 1), intArrayOf(3, 2, 1, 4)))
        assertEquals(false, isAddr(intArrayOf(9, 4, 2, 3), intArrayOf(3, 2, 1, 1), intArrayOf(3, 2, 1, 4)))
    }

    private fun isAddi(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] + c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun addi(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] + c[2]
    }

    @Test
    fun `test is addi`() {
        assertEquals(true, isAddi(intArrayOf(9, 0, 5, 3), intArrayOf(3, 2, 1, 1), intArrayOf(3, 2, 1, 8)))
        assertEquals(false, isAddi(intArrayOf(9, 1, 5, 3), intArrayOf(3, 2, 1, 1), intArrayOf(3, 2, 1, 0)))
        assertEquals(false, isAddi(intArrayOf(9, 4, 2, 3), intArrayOf(3, 2, 1, 1), intArrayOf(3, 2, 1, 4)))
    }

    private fun isMulr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            before[c[3]] = before[c[1]] * before[c[2]]
            compareBeforeAfter(before, after)
        } else false

    private fun mulr(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] * mem[c[2]]
    }

    private fun isMuli(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] * c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun muli(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] * c[2]
    }

    private fun isBanr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            before[c[3]] = before[c[1]] and before[c[2]]
            compareBeforeAfter(before, after)
        } else false

    private fun banr(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] and mem[c[2]]
    }

    private fun isBani(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] and c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun bani(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] and c[2]
    }

    private fun isBorr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            before[c[3]] = before[c[1]] or before[c[2]]
            compareBeforeAfter(before, after)
        } else false

    private fun borr(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] or mem[c[2]]
    }

    private fun isBori(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] or c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun bori(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]] or c[2]
    }

    private fun isSetr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]]
            compareBeforeAfter(before, after)
        } else false

    private fun setr(c: IntArray, mem: IntArray) {
        mem[c[3]] = mem[c[1]]
    }

    private fun isSeti(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (c[3] in 0..3) {
            before[c[3]] = c[1]
            compareBeforeAfter(before, after)
        } else false

    private fun seti(c: IntArray, mem: IntArray) {
        mem[c[3]] = c[1]
    }

    private fun isGtir(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (bcPossibleRegister(c)) {
            if (c[1] > before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun gtir(c: IntArray, mem: IntArray) {
        if (c[1] > mem[c[2]]) mem[c[3]] = 1
        else mem[c[3]] = 0
    }

    private fun isGtri(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            if (before[c[1]] > c[2]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun gtri(c: IntArray, mem: IntArray) {
        if (mem[c[1]] > c[2]) mem[c[3]] = 1
        else mem[c[3]] = 0
    }

    private fun isGtrr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            if (before[c[1]] > before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun gtrr(c: IntArray, mem: IntArray) {
        if (mem[c[1]] > mem[c[2]]) mem[c[3]] = 1
        else mem[c[3]] = 0
    }

    private fun isEqir(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (bcPossibleRegister(c)) {
            if (c[1] == before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun eqir(c: IntArray, mem: IntArray) {
        if (c[1] == mem[c[2]]) mem[c[3]] = 1
        else mem[c[3]] = 0
    }

    private fun isEqri(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            if (before[c[1]] == c[2]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun eqri(c: IntArray, mem: IntArray) {
        if (mem[c[1]] == c[2]) mem[c[3]] = 1
        else mem[c[3]] = 0
    }

    private fun isEqrr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            if (before[c[1]] == before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun eqrr(c: IntArray, mem: IntArray) {
        if (mem[c[1]] == mem[c[2]]) mem[c[3]] = 1
        else mem[c[3]] = 0
    }

    @Test
    fun sample() {
        assertEquals(3, possibleOpCodes("Before: [3, 2, 1, 1]\n" +
                "9 2 1 2\n" +
                "After:  [3, 2, 2, 1]"))
    }

    @Test
    fun part1() {
        assertEquals(612, behavesLikeMinOpCodes(File("files/2018/day16.txt").readText(), 3))
    }

    @Test
    fun part2() {
        assertEquals(485, valueInRegister(File("files/2018/day16.txt").readText(), 0))
    }
}