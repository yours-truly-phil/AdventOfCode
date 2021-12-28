package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

class Day6 {
    private fun maxArea(input: String): Int {
        val coords = parseCoordinates(input)
        val grid = fillGridWithShortestIndices(coords)
        val borderIndices = indicesThatGoToInfinity(grid)
        val indexCountMap = countClosest(grid, borderIndices)
        return indexCountMap.maxByOrNull { it.value }!!.value
    }

    private fun parseCoordinates(input: String): Array<Loc> {
        val coords = input.lines().map {
            val parts = it.split(", ")
            Loc(parts[0].toInt(), parts[1].toInt())
        }.toTypedArray()
        return coords
    }

    private fun sizeOfLoocationsWithDistToAllLessThen(input: String, maxDist: Int): Int {
        val coords = parseCoordinates(input)

        val maxX = coords.maxOf { it.x }
        val maxY = coords.maxOf { it.y }

        var count = 0
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                var totalDist = 0
                val loc = Loc(x, y)
                for (i in coords.indices) {
                    totalDist += coords[i].distFrom(loc)
                }
                if (totalDist < maxDist) {
                    count++
                }
            }
        }
        return count
    }

    private fun fillGridWithShortestIndices(coords: Array<Loc>): Array<IntArray> {
        val maxX = coords.maxOf { it.x }
        val maxY = coords.maxOf { it.y }

        val grid = Array(maxY + 1) { IntArray(maxX + 1) }
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                var minDist = Int.MAX_VALUE
                var minDistIdx = -1
                var equal = false
                val loc = Loc(x, y)
                for (i in coords.indices) {
                    val dist = coords[i].distFrom(loc)
                    if (dist < minDist) {
                        equal = false
                        minDistIdx = i
                        minDist = dist
                    } else if (dist == minDist) {
                        equal = true
                    }
                }
                if (equal) {
                    grid[y][x] = -1
                } else {
                    grid[y][x] = minDistIdx
                }
            }
        }
        return grid
    }

    private fun countClosest(
        grid: Array<IntArray>,
        borderIndices: HashSet<Int>,
    ): HashMap<Int, Int> {
        val indexCountMap = HashMap<Int, Int>()
        grid.forEach { row ->
            row.forEach {
                if (!borderIndices.contains(it)) {
                    indexCountMap.computeIfAbsent(it) { 0 }
                    indexCountMap[it] = indexCountMap[it]!! + 1
                }
            }
        }
        return indexCountMap
    }

    private fun indicesThatGoToInfinity(grid: Array<IntArray>): HashSet<Int> {
        val borderIndices = HashSet<Int>()
        borderIndices.addAll(grid[0].toSet())
        borderIndices.addAll(grid[grid.size - 1].toSet())
        for (y in grid.indices) {
            borderIndices.add(grid[y][0])
            borderIndices.add(grid[y][grid[y].size - 1])
        }
        return borderIndices
    }

    class Loc(val x: Int, val y: Int) {
        fun dist(): Int = abs(x) + abs(y)
        fun distFrom(to: Loc) = abs(x - to.x) + abs(y - to.y)
    }

    @Test
    fun manhattanDistanceOnePointToAnother() {
        val a = Loc(8, 9)
        val b = Loc(1, 1)
        assertEquals(15, a.distFrom(b))
        assertEquals(15, b.distFrom(a))
    }

    @Test
    fun sample() {
        assertEquals(
            17, maxArea(
                """1, 1
1, 6
8, 3
3, 4
5, 5
8, 9"""
            )
        )
    }

    @Test
    fun part2Sample() {
        assertEquals(
            16, sizeOfLoocationsWithDistToAllLessThen(
                """1, 1
1, 6
8, 3
3, 4
5, 5
8, 9""", 32
            )
        )
    }

    @Test
    fun part1() {
        assertEquals(3010, maxArea(File("files/2018/day6.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(48034, sizeOfLoocationsWithDistToAllLessThen(File("files/2018/day6.txt").readText(), 10000))
    }
}