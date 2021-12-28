package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day24 {
    private fun strongestBridge(input: String): Int {
        val parts = parseInput(input)
        var max = 0
        for (zero in parts[0]!!) {
            max = if (zero.a == 0) {
                maxOf(max, construct(zero.b, zero, HashSet(), parts))
            } else {
                maxOf(max, construct(zero.a, zero, HashSet(), parts))
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

    private fun strengthLongestBridge(input: String): Int {
        val parts = parseInput(input)
        var max = 0
        val longest = HashMap<Int, Int>()
        for (zero in parts[0]!!) {
            max = if (zero.a == 0) {
                maxOf(max, constructLongest(zero.b, zero, HashSet(), parts, longest))
            } else {
                maxOf(max, constructLongest(zero.a, zero, HashSet(), parts, longest))
            }
        }
        return longest.entries.maxByOrNull { it.key }!!.value
    }

    private fun parseInput(input: String): HashMap<Int, MutableList<Part>> {
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
        return parts
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
        val total = used.sumOf { it.total() }
        longest.computeIfAbsent(used.size) { total }
        if (longest[used.size]!! < total) {
            longest[used.size] = total
            println("${used.size}=${used.sumOf { it.total() }}")
        }
        return str + cur.total()
    }

    private fun copyParts(used: HashSet<Part>): HashSet<Part> = HashSet(used)

    data class Part(val a: Int, val b: Int) {
        fun total(): Int = a + b
    }

    @Test
    fun sample() {
        assertEquals(31, strongestBridge("0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10"))
    }

    @Test
    fun part2Sample() {
        assertEquals(19, strengthLongestBridge("0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10"))
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