package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import java.util.function.Consumer
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

    fun instructions(input: String): Array<Consumer<CharArray>> {
        val res = ArrayList<Consumer<CharArray>>()
        input.split(",").forEach {
            when {
                it.startsWith("s") -> {
                    res += Consumer<CharArray> { arr -> spinByAmount(it.substring(1).toInt(), arr) }
                }
                it.startsWith("x") -> {
                    val parts = it.substring(1).split("/")
                    val a = parts[0].toInt()
                    val b = parts[1].toInt()
                    res += Consumer<CharArray> { arr -> swap(arr, a, b) }
                }
                it.startsWith("p") -> {
                    val parts = it.substring(1).split("/")
                    res += Consumer<CharArray> { arr ->
                        run {
                            val a = arr.indexOf(parts[0].first())
                            val b = arr.indexOf(parts[1].first())
                            swap(arr, a, b)
                        }
                    }
                }
            }
        }
        return res.toTypedArray()
    }

    fun part2RepeatDancesWithParsing(num: Int, letters: String, input: String): String {
        var order = letters
        val memo = HashMap<String, Int>()
        var idx = 0
        while (idx < num) {
            memo.computeIfAbsent(order) { idx }
            order = orderOfPrograms(order, input)
            idx++
            if (memo.containsKey(order)) {
                val diff = idx - memo[order]!!
                while (idx < num - diff) {
                    idx += diff
                }
            }
        }
        return order
    }

    fun part2WithoutRepeatedParsing(num: Int, letters: String, input: String): String {
        val instructions = instructions(input)
        val memo = HashMap<String, Int>()
        val order = letters.toCharArray()
        var idx = 0
        while (idx < num) {
            memo.computeIfAbsent(order.joinToString("")) { idx }
            for (instruction in instructions) {
                instruction.accept(order)
            }
            idx++
            if (memo.containsKey(order.joinToString(""))) {
                val diff = idx - memo[order.joinToString("")]!!
                while (idx < num - diff) {
                    idx += diff
                }
            }
        }
        return order.joinToString("")
    }

    private fun partner(it: String, mem: CharArray) {
        val parts = it.substring(1).split("/")
        val a = mem.indexOf(parts[0].first())
        val b = mem.indexOf(parts[1].first())
        swap(mem, a, b)
    }

    private fun exchange(it: String, mem: CharArray) {
        val parts = it.substring(1).split("/")
        val a = parts[0].toInt()
        val b = parts[1].toInt()
        swap(mem, a, b)
    }

    private fun swap(mem: CharArray, a: Int, b: Int) {
        val tmp = mem[a]
        mem[a] = mem[b]
        mem[b] = tmp
    }

    private fun spinByAmount(num: Int, arr: CharArray) {
        val tmp = arr.clone()
        for (i in arr.indices) {
            tmp[i] = arr[(i + arr.size - num) % arr.size]
        }
        for (i in arr.indices) {
            arr[i] = tmp[i]
        }
    }

    private fun spin(it: String, arr: CharArray) {
        val num = it.substring(1).toInt()
        spinByAmount(num, arr)
    }

    @Test
    fun `spin by amount`() {
        val arr = "abcde".toCharArray()
        spinByAmount(3, arr)
        assertEquals("cdeab", arr.joinToString(""))
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

    @Test
    fun part2() {
        assertEquals("cbolhmkgfpenidaj",
            part2WithoutRepeatedParsing(1_000_000_000, "abcdefghijklmnop", File("files/2017/day16.txt").readText()))
    }

    @Test
    fun part2RepeatWithParsing() {
        assertEquals("cbolhmkgfpenidaj",
            part2RepeatDancesWithParsing(1_000_000_000, "abcdefghijklmnop", File("files/2017/day16.txt").readText()))
    }
}