package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day12 {
    fun numProgramsWithId(input: String, id: Int): Int {
        val nodes = parse(input)
        val connected = HashSet<Int>().also { it.add(id) }
        val unchecked = HashSet<Int>().also { it.add(id) }
        while (unchecked.isNotEmpty()) {
            val new = HashSet<Int>()
            for (node in unchecked) {
                new.addAll(nodes[node]!!)
            }
            unchecked.clear()
            for (node in new) {
                if (!connected.contains(node)) {
                    connected.add(node)
                    unchecked.add(node)
                }
            }
        }
        return connected.size
    }

    fun noOfGroups(input: String): Int {
        val nodes = parse(input)
        var count = 0
        val notInGroup = ArrayDeque<Int>().also { it.addAll(nodes.keys) }
        while (notInGroup.isNotEmpty()) {
            val num = notInGroup.removeFirst()
            val connected = HashSet<Int>().also { it.add(num) }
            val unchecked = HashSet<Int>().also { it.add(num) }
            while (unchecked.isNotEmpty()) {
                val new = HashSet<Int>()
                for (node in unchecked) {
                    new.addAll(nodes[node]!!)
                }
                unchecked.clear()
                for (node in new) {
                    if (!connected.contains(node)) {
                        connected.add(node)
                        notInGroup.remove(node)
                        unchecked.add(node)
                    }
                }
            }
            count++
        }
        return count
    }

    private fun parse(input: String): Map<Int, ArrayList<Int>> {
        val res = input.split("\n").map {
            it.substring(0, it.indexOf(" ")).toInt() to ArrayList<Int>()
        }.toMap()
        input.split("\n").forEach {
            val num = it.substring(0, it.indexOf(" ")).toInt()
            it.split(" <-> ")[1].split(", ")
                .forEach { i -> res[num]!!.add(i.toInt()) }
        }
        return res
    }

    @Test
    fun sample() {
        assertEquals(6, numProgramsWithId("0 <-> 2\n" +
                "1 <-> 1\n" +
                "2 <-> 0, 3, 4\n" +
                "3 <-> 2, 4\n" +
                "4 <-> 2, 3, 6\n" +
                "5 <-> 6\n" +
                "6 <-> 4, 5", 0))
    }

    @Test
    fun `part 2 sample()`() {
        assertEquals(2, noOfGroups("0 <-> 2\n" +
                "1 <-> 1\n" +
                "2 <-> 0, 3, 4\n" +
                "3 <-> 2, 4\n" +
                "4 <-> 2, 3, 6\n" +
                "5 <-> 6\n" +
                "6 <-> 4, 5"))
    }

    @Test
    fun part1() {
        assertEquals(169, numProgramsWithId(File("files/2017/day12.txt").readText(), 0))
    }

    @Test
    fun part2() {
        assertEquals(179, noOfGroups(File("files/2017/day12.txt").readText()))
    }
}