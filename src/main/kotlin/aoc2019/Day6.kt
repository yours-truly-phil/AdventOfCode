package aoc2019

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day6 {
    private fun solvePart1(input: String): Int {
        val orbits = input.lines().map { it.split(")") }
            .groupBy { it[0] }
            .map { it.key to it.value.flatten().filter { v -> v != it.key } }.toMap()
        val first = orbits["COM"]!!
        val deque = ArrayDeque<Pair<String, Int>>()
        deque.addAll(first.map { it to 1 })
        var count = 0
        while (deque.isNotEmpty()) {
            val current = deque.removeFirst()
            count += current.second
            if (orbits.containsKey(current.first)) {
                deque.addAll(orbits[current.first]!!.map { it to current.second + 1 })
            }
        }
        return count
    }

    private fun solvePart2(input: String): Int {
        val graph = mutableMapOf<String, MutableList<String>>()
        input.lines().forEach {
            val (from, to) = it.split(")")
            graph.computeIfAbsent(from) { mutableListOf() }.add(to)
            graph.computeIfAbsent(to) { mutableListOf() }.add(from)
        }

        val paths = findPath(graph, "SAN", mutableListOf("YOU"))
        return paths.minByOrNull { it.size }!!.size - 3
    }

    private fun findPath(graph: Map<String, List<String>>, end: String, path: MutableList<String>): List<List<String>> {
        val result = mutableListOf<List<String>>()
        if (path.last() == end) {
            return mutableListOf(path)
        }
        for (next in graph[path.last()]!!) {
            if (path.contains(next)) continue
            val newPath = path.toMutableList()
            newPath.add(next)
            result.addAll(findPath(graph, end, newPath))
        }
        return result
    }

    @Test
    fun part1() {
        assertEquals(42, solvePart1("COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L"))
        assertEquals(254447, solvePart1(File("files/2019/day6.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(4, solvePart2("COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L\nK)YOU\nI)SAN"))
        assertEquals(445, solvePart2(File("files/2019/day6.txt").readText()))
    }
}