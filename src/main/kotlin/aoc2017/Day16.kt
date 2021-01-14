package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day16 {
    fun orderOfPrograms(letters: String, input: String): String {
        val mem = letters.toCharArray()
        input.split(",").forEach {
            when {
                it.startsWith("s") -> spin(it, mem)
                it.startsWith("x") -> exchange(it, mem)
                it.startsWith("p") -> partner(it, mem)
            }
        }
        return mem.joinToString("")
    }

    private fun partner(it: String, mem: CharArray) {
        val parts = it.substring(1).split("/")
        val a = mem.indexOf(parts[0].first())
        val b = mem.indexOf(parts[1].first())
        val tmp = mem[a]
        mem[a] = mem[b]
        mem[b] = tmp
    }

    private fun exchange(it: String, mem: CharArray) {
        val parts = it.substring(1).split("/")
        val a = parts[0].toInt()
        val b = parts[1].toInt()
        val tmp = mem[a]
        mem[a] = mem[b]
        mem[b] = tmp
    }

    private fun spin(it: String, arr: CharArray) {
        repeat(it.substring(1).toInt()) {
            val tmp = arr.last()
            for (i in arr.size - 1 downTo 1) {
                arr[i] = arr[i - 1]
            }
            arr[0] = tmp
        }
    }

    @Test
    fun `partner in array`() {
        val arr = "eabdc".toCharArray()
        partner("pe/b", arr)
        assertEquals("baedc", arr.joinToString(""))
    }

    @Test
    fun `exchange in array`() {
        val arr = "eabcd".toCharArray()
        exchange("x3/4", arr)
        assertEquals("eabdc", arr.joinToString(""))
    }

    @Test
    fun `spin array`() {
        val arr = "abcde".toCharArray()
        spin("s1", arr)
        assertEquals("eabcd", arr.joinToString(""))
        val brr = "abcde".toCharArray()
        spin("s3", brr)
        assertEquals("cdeab", brr.joinToString(""))
    }

    @Test
    fun sample() {
        assertEquals("baedc", orderOfPrograms("abcde", "s1,x3/4,pe/b"))
    }

    @Test
    fun part1() {
        assertEquals("cknmidebghlajpfo",
            orderOfPrograms("abcdefghijklmnop", File("files/2017/day16.txt").readText()))
    }
}