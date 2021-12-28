package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day12 {

    private fun solvePart1(input: List<String>): Int {
        val caves = parseCaves(input)
        val paths = HashSet<String>()
        findPath("start", paths, "start", caves)
        return paths.size
    }

    private fun findPath(cur: String, paths: MutableSet<String>, visited: String, caves: Map<String, List<String>>) {
        val neighbors = caves[cur.split(",").last()]!!.filter { cave ->
            cave !in visited.split(",").filter { !it.any { c -> c.isUpperCase() } }
        }
        for (neighbor in neighbors) {
            val path = "$visited,$neighbor"
            if (neighbor == "end") {
                paths.add(path)
            } else {
                findPath("$cur,$neighbor", paths, path, caves)
            }
        }
    }

    private fun solvePart2(input: List<String>): Int {
        val caves = parseCaves(input)
        val paths = HashSet<String>()
        findPath2("start", paths, "start", caves, true)
        return paths.size
    }

    private fun findPath2(
        cur: String,
        paths: MutableSet<String>,
        visited: String,
        caves: Map<String, List<String>>,
        canVisitedAny: Boolean
    ) {
        var neighbors = caves[cur.split(",").last()]!!
        if (!canVisitedAny) {
            neighbors = neighbors.filter { neighbor ->
                neighbor !in visited.split(",").filter { !it.any { c -> c.isUpperCase() } }
            }
        }
        for (neighbor in neighbors) {
            val path = "$visited,$neighbor"
            if (neighbor == "end") {
                paths.add(path)
            } else {
                val visitedSmallCaves = visited.split(",").filterNot { it.any { c -> c.isUpperCase() } }
                if (visitedSmallCaves.contains(neighbor)) {
                    findPath2("$cur,$neighbor", paths, path, caves, false)
                } else {
                    findPath2("$cur,$neighbor", paths, path, caves, canVisitedAny)
                }
            }
        }
    }

    private fun parseCaves(input: List<String>): HashMap<String, MutableList<String>> {
        val caves = hashMapOf<String, MutableList<String>>()
        input.forEach { line ->
            val (from, to) = line.split("-")
            if (to != "start") caves.computeIfAbsent(from) { arrayListOf() }.add(to)
            if (from != "start") caves.computeIfAbsent(to) { arrayListOf() }.add(from)
        }
        return caves
    }

    @Test
    fun part1() {
        assertEquals(
            10, solvePart1(
                """
start-A
start-b
A-c
A-b
b-d
A-end
b-end""".trimIndent().lines()
            )
        )
        assertEquals(3802, solvePart1(File("files/2021/day12.txt").readLines()))
    }

    @Test
    fun part2() {
        assertEquals(
            36, solvePart2(
                """
start-A
start-b
A-c
A-b
b-d
A-end
b-end""".trimIndent().lines()
            )
        )
        assertEquals(99448, solvePart2(File("files/2021/day12.txt").readLines()))
    }
}