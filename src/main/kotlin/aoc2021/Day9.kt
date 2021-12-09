package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day9 {
    private fun sumLowPoints(input: String): Int {
        val arr = input.lines().map { line -> line.toCharArray().map { it.toInt() - 48 } }
        val map = Map(arr)
        return map.getLowPoints().sumOf { map.map[it]!! + 1 }
    }

    private fun threeLargestBasins(input: String): Int {
        val arr = input.lines().map { line -> line.toCharArray().map { it.toInt() - 48 } }
        val map = Map(arr)
        val lowPoints = map.getLowPoints()
        val basins = map.getBasins(lowPoints).sortedByDescending { it.size }
        return basins[0].size * basins[1].size * basins[2].size
    }

    class Map(arr: List<List<Int>>) {
        val map: HashMap<Vec2i, Int>

        init {
            map = hashMapOf<Vec2i, Int>().also {
                arr.indices.forEach { y -> arr[y].indices.forEach { x -> it[Vec2i(x, y)] = arr[y][x] } }
            }
        }

        fun getLowPoints(): Set<Vec2i> = map.filter { (p, v) ->
            val up = map.getOrDefault(p.up(), Int.MAX_VALUE)
            val left = map.getOrDefault(p.left(), Int.MAX_VALUE)
            val right = map.getOrDefault(p.right(), Int.MAX_VALUE)
            val down = map.getOrDefault(p.down(), Int.MAX_VALUE)
            up > v && left > v && right > v && down > v
        }.keys

        fun getBasins(lowPoints: Set<Vec2i>): List<Set<Vec2i>> {
            val basins = arrayListOf<MutableSet<Vec2i>>()
            for (lowPoint in lowPoints) {
                val basin = hashSetOf(lowPoint)
                getBasin(lowPoint, basin)
                basins.add(basin)
            }
            return basins
        }

        private fun getBasin(curPoint: Vec2i, basin: MutableSet<Vec2i>) {
            val neighbors = getNeighbors(curPoint)
            for (neighbor in neighbors) {
                if (basin.contains(neighbor) || !map.containsKey(neighbor)) continue
                if (map[neighbor]!! < 9 && map[neighbor]!! > map[curPoint]!!) {
                    basin.add(neighbor)
                    getBasin(neighbor, basin)
                }
            }
        }

        private fun getNeighbors(p: Vec2i): List<Vec2i> {
            val neighbors = arrayListOf<Vec2i>()
            if (map.containsKey(p.up())) neighbors.add(p.up())
            if (map.containsKey(p.left())) neighbors.add(p.left())
            if (map.containsKey(p.right())) neighbors.add(p.right())
            if (map.containsKey(p.down())) neighbors.add(p.down())
            return neighbors
        }
    }

    data class Vec2i(val x: Int, val y: Int) {
        fun up() = Vec2i(x, y - 1)
        fun down() = Vec2i(x, y + 1)
        fun left() = Vec2i(x - 1, y)
        fun right() = Vec2i(x + 1, y)
    }

    @Test
    fun part1() {
        assertEquals(
            15, sumLowPoints(
                """
2199943210
3987894921
9856789892
8767896789
9899965678""".trimIndent()
            )
        )
        assertEquals(502, sumLowPoints(File("files/2021/day9.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(
            1134, threeLargestBasins(
                """
2199943210
3987894921
9856789892
8767896789
9899965678""".trimIndent()
            )
        )
        assertEquals(1330560, threeLargestBasins(File("files/2021/day9.txt").readText()))
    }
}