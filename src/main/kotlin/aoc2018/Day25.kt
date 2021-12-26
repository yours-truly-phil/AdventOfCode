package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

class Day25 {
    private fun solvePart1(input: String): Int {
        val points = input.lines().map { line ->
            val (x, y, z, w) = line.split(",").map { it.trim().toInt() }
            Vec4i(x, y, z, w)
        }
        val neighbors = mutableMapOf<Vec4i, MutableSet<Vec4i>>()
        for (point in points) {
            neighbors[point] = points.filter { it != point && it.manhattanDistance(point) <= 3 }.toMutableSet()
        }
        val unAssigned = points.toMutableSet()
        val constellations = mutableListOf<Set<Vec4i>>()
        while (unAssigned.isNotEmpty()) {
            val newOrigin = unAssigned.first()

            val inConstellation = mutableListOf(newOrigin).toMutableSet()
            findNeighbors(newOrigin, neighbors, inConstellation)
            constellations.add(inConstellation)

            unAssigned.removeAll(inConstellation)
        }
        return constellations.size
    }

    private fun findNeighbors(
        point: Vec4i,
        neighbors: MutableMap<Vec4i, MutableSet<Vec4i>>,
        inConstellation: MutableSet<Vec4i>
    ) {
        val next = neighbors[point]!!.toSet().filter { !inConstellation.contains(it) }
        inConstellation.addAll(next)

        for (n in next) {
            findNeighbors(n, neighbors, inConstellation)
        }
    }

    data class Vec4i(val x: Int, val y: Int, val z: Int, val w: Int) {
        fun manhattanDistance(o: Vec4i): Int {
            return abs(x - o.x) + abs(y - o.y) + abs(z - o.z) + abs(w - o.w)
        }
    }

    @Test
    fun part1() {
        assertEquals(
            2, solvePart1(
                """
0,0,0,0
3,0,0,0
0,3,0,0
0,0,3,0
0,0,0,3
0,0,0,6
9,0,0,0
12,0,0,0""".trimIndent()
            )
        )
        assertEquals(
            4, solvePart1(
                """
-1,2,2,0
0,0,2,-2
0,0,0,-2
-1,2,0,0
-2,-2,-2,2
3,0,2,-1
-1,3,2,2
-1,0,-1,0
0,2,1,-2
3,0,0,0""".trimIndent()
            )
        )
        assertEquals(
            3, solvePart1(
                """
1,-1,0,1
2,0,-1,0
3,2,-1,0
0,0,3,1
0,0,-1,-1
2,3,-2,0
-2,2,0,0
2,-2,0,-1
1,-1,0,-1
3,2,0,2""".trimIndent()
            )
        )
        assertEquals(
            8, solvePart1(
                """
1,-1,-1,-2
-2,-2,0,1
0,2,1,3
-2,3,-2,1
0,2,3,-2
-1,-1,1,-2
0,-2,-1,0
-2,2,3,-1
1,2,2,0
-1,-2,0,-2""".trimIndent()
            )
        )
        assertEquals(367, solvePart1(File("files/2018/day25.txt").readText()))
    }
}