package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day16 {
    fun behavesLikeMinOpCodes(input: String, num: Int): Int = input
        .split("\n\n\n\n")[0]
        .split("\n\n")
        .count { possibleOpCodes(it) >= num }

    private fun possibleOpCodes(s: String): Int {
        val l = s.lines()
        val before = l[0].substring(l[0].indexOf("[") + 1, l[0].indexOf("]"))
            .split(", ").map { it.toInt() }.toIntArray()
        val after = l[2].substring(l[2].indexOf("[") + 1, l[2].indexOf("]"))
            .split(", ").map { it.toInt() }.toIntArray()
        val opcodes = l[1].split(" ").map { it.toInt() }.toIntArray()
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

    private fun isMuli(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] * c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun isBanr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            before[c[3]] = before[c[1]] and before[c[2]]
            compareBeforeAfter(before, after)
        } else false

    private fun isBani(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] and c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun isBorr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            before[c[3]] = before[c[1]] or before[c[2]]
            compareBeforeAfter(before, after)
        } else false

    private fun isBori(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]] or c[2]
            compareBeforeAfter(before, after)
        } else false

    private fun isSetr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            before[c[3]] = before[c[1]]
            compareBeforeAfter(before, after)
        } else false

    private fun isSeti(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (c[3] in 0..3) {
            before[c[3]] = c[1]
            compareBeforeAfter(before, after)
        } else false

    private fun isGtir(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (bcPossibleRegister(c)) {
            if (c[1] > before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun isGtri(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            if (before[c[1]] > c[2]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun isGtrr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            if (before[c[1]] > before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun isEqir(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (bcPossibleRegister(c)) {
            if (c[1] == before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun isEqri(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (acPossibleRegister(c)) {
            if (before[c[1]] == c[2]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

    private fun isEqrr(c: IntArray, before: IntArray, after: IntArray): Boolean =
        if (abcPossibleRegister(c)) {
            if (before[c[1]] == before[c[2]]) before[c[3]] = 1
            else before[c[3]] = 0
            compareBeforeAfter(before, after)
        } else false

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
}