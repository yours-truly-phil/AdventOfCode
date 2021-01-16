package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day24 {
    fun strongestBridge(input: String): Int {
        val numArr = input.lines().map {
            val nums = it.split("/")
            Part(nums[0].toInt(), nums[1].toInt())
        }
        val parts = HashMap<Int, MutableList<Part>>()
        for (part in numArr) {
            parts.computeIfAbsent(part.a) { ArrayList() }
            parts.computeIfAbsent(part.b) { ArrayList() }
            parts[part.a]!!.add(part)
            if (part.a != part.b) parts[part.b]!!.add(part)
        }
        var max = 0
        for (zero in parts[0]!!) {
            if (zero.a == 0) {
                max = maxOf(max, construct(zero.b, zero, HashSet(), parts))
            } else {
                max = maxOf(max, construct(zero.a, zero, HashSet(), parts))
            }
        }
        return max
    }

    private fun construct(connectTo: Int, cur: Part, used: HashSet<Part>, map: HashMap<Int, MutableList<Part>>): Int {
        var str = 0
        used.add(cur)
        for (next in map[connectTo]!!) {
            if (next !in used) {
                str = if (next.a == connectTo) {
                    maxOf(str, construct(next.b, next, copyParts(used), map))
                } else {
                    maxOf(str, construct(next.a, next, copyParts(used), map))
                }
            }
        }
        return str + cur.total()
    }

    fun strengthLongestBridge(input: String): Int {
        val numArr = input.lines().map {
            val nums = it.split("/")
            Part(nums[0].toInt(), nums[1].toInt())
        }
        val parts = HashMap<Int, MutableList<Part>>()
        for (part in numArr) {
            parts.computeIfAbsent(part.a) { ArrayList() }
            parts.computeIfAbsent(part.b) { ArrayList() }
            parts[part.a]!!.add(part)
            if (part.a != part.b) parts[part.b]!!.add(part)
        }
        var max = 0
        val longest = HashMap<Int, Int>()
        for (zero in parts[0]!!) {
            if (zero.a == 0) {
                max = maxOf(max, constructLongest(zero.b, zero, HashSet(), parts, longest))
            } else {
                max = maxOf(max, constructLongest(zero.a, zero, HashSet(), parts, longest))
            }
        }
        return longest.entries.sortedBy { it.key }.last().value
//        return max
    }

    private fun constructLongest(
        connectTo: Int,
        cur: Part,
        used: HashSet<Part>,
        map: HashMap<Int, MutableList<Part>>,
        longest: HashMap<Int, Int>,
    ): Int {
        var str = 0
        used.add(cur)
        for (next in map[connectTo]!!) {
            if (next !in used) {
                str = if (next.a == connectTo) {
                    maxOf(str, constructLongest(next.b, next, copyParts(used), map, longest))
                } else {
                    maxOf(str, constructLongest(next.a, next, copyParts(used), map, longest))
                }
            }
        }
        val total = used.sumBy { it.total() }
        longest.computeIfAbsent(used.size) { total }
        if (longest[used.size]!! < total) {
            longest[used.size] = total
            println("${used.size}=${used.sumBy { it.total() }}")
        }
        return str + cur.total()
    }

    private fun copyParts(used: HashSet<Part>): HashSet<Part> = HashSet(used)

    data class Part(val a: Int, val b: Int) {
        fun total(): Int = a + b
    }

    @Test
    fun sample() {
        assertEquals(31, strongestBridge("0/2\n" +
                "2/2\n" +
                "2/3\n" +
                "3/4\n" +
                "3/5\n" +
                "0/1\n" +
                "10/1\n" +
                "9/10"))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(19, strengthLongestBridge("0/2\n" +
                "2/2\n" +
                "2/3\n" +
                "3/4\n" +
                "3/5\n" +
                "0/1\n" +
                "10/1\n" +
                "9/10"))
    }

    @Test
    fun part1() {
        assertEquals(1511, strongestBridge(File("files/2017/day24.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(1471, strengthLongestBridge(File("files/2017/day24.txt").readText()))
    }
}