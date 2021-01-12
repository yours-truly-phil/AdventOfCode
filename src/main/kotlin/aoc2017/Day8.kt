package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day8 {
    fun largestValue(input: String): Int {
        val mem = HashMap<String, Int>()
        input.lines().forEach {
            val parts = it.split(" if ")
            val left = parts[0].split(" ")
            val reg = left[0]
            val inst = left[1]
            val num = left[2].toInt()
            val cond = parts[1].split(" ")
            val cReg = cond[0]
            val op = cond[1]
            val cVal = cond[2].toInt()

            mem.computeIfAbsent(reg) { 0 }
            mem.computeIfAbsent(cReg) { 0 }
            var condRes = false
            when (op) {
                ">" -> condRes = mem[cReg]!! > cVal
                "<" -> condRes = mem[cReg]!! < cVal
                ">=" -> condRes = mem[cReg]!! >= cVal
                "<=" -> condRes = mem[cReg]!! <= cVal
                "==" -> condRes = mem[cReg]!! == cVal
                "!=" -> condRes = mem[cReg]!! != cVal
            }
            if (condRes) {
                when (inst) {
                    "inc" -> mem[reg] = mem[reg]!! + num
                    "dec" -> mem[reg] = mem[reg]!! - num
                }
            }
        }
        return mem.maxOf { it.value }
    }

    private fun largestValueDuringRuntime(input: String): Int {
        val mem = HashMap<String, Int>()
        var max = Int.MIN_VALUE
        input.lines().forEach {
            val parts = it.split(" if ")
            val left = parts[0].split(" ")
            val reg = left[0]
            val inst = left[1]
            val num = left[2].toInt()
            val cond = parts[1].split(" ")
            val cReg = cond[0]
            val op = cond[1]
            val cVal = cond[2].toInt()

            mem.computeIfAbsent(reg) { 0 }
            mem.computeIfAbsent(cReg) { 0 }
            var condRes = false
            when (op) {
                ">" -> condRes = mem[cReg]!! > cVal
                "<" -> condRes = mem[cReg]!! < cVal
                ">=" -> condRes = mem[cReg]!! >= cVal
                "<=" -> condRes = mem[cReg]!! <= cVal
                "==" -> condRes = mem[cReg]!! == cVal
                "!=" -> condRes = mem[cReg]!! != cVal
            }
            if (condRes) {
                when (inst) {
                    "inc" -> mem[reg] = mem[reg]!! + num
                    "dec" -> mem[reg] = mem[reg]!! - num
                }
            }
            max = maxOf(max, mem.maxOf { m -> m.value })
        }
        return max
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(10, largestValueDuringRuntime("b inc 5 if a > 1\n" +
                "a inc 1 if b < 5\n" +
                "c dec -10 if a >= 1\n" +
                "c inc -20 if c == 10"))
    }

    @Test
    fun sample() {
        assertEquals(1, largestValue("b inc 5 if a > 1\n" +
                "a inc 1 if b < 5\n" +
                "c dec -10 if a >= 1\n" +
                "c inc -20 if c == 10"))
    }

    @Test
    fun part1() {
        assertEquals(5221, largestValue(File("files/2017/day8.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(7491, largestValueDuringRuntime(File("files/2017/day8.txt").readText()))
    }
}