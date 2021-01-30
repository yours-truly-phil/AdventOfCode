package aoc2018

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day22 {
    fun totalRiskLvl(depth: Int, target: V2i): Int {
        val map = parseMap(depth, target)

        println(mapToString(map, target))
        return totalRiskOfRect(map, V2i(0, 0), target)
    }

    private fun totalRiskOfRect(map: Map<V2i, Region>, loc: V2i, target: V2i): Int =
        map.filter { it.key.x in loc.x..target.x && it.key.y in loc.y..target.y }
            .map {
                when (it.value.type()) {
                    '.' -> 0
                    '=' -> 1
                    else -> 2
                }
            }.sum()

    private fun parseMap(depth: Int, target: V2i): HashMap<V2i, Region> {
        val map = HashMap<V2i, Region>()
        map[V2i(0, 0)] = Region(erosion(0, depth))
        map[target] = Region(erosion(0, depth))

        val maxX = map.maxOf { it.key.x }
        val maxY = map.maxOf { it.key.y }
        for (x in 0..maxX) map[V2i(x, 0)] = Region(erosion(x * 16807L, depth))
        for (y in 0..maxY) map[V2i(0, y)] = Region(erosion(y * 48271L, depth))
        for (y in 1..maxY) {
            for (x in 1..maxX) {
                val loc = V2i(x, y)
                if (loc != target) {
                    map[loc] = Region(erosion(map[V2i(x - 1, y)]!!.erosion * map[V2i(x, y - 1)]!!.erosion, depth))
                }
            }
        }
        return map
    }

    private fun erosion(geoIdx: Long, depth: Int) = (geoIdx + depth) % 20183

    private fun mapToString(map: Map<V2i, Region>, target: V2i): String {
        val maxX = map.maxOf { it.key.x }
        val maxY = map.maxOf { it.key.y }
        val lines = ArrayList<String>()
        for (y in 0..maxY) {
            val sb = StringBuilder()
            for (x in 0..maxX) {
                val loc = V2i(x, y)
                when {
                    loc == V2i(0, 0) -> sb.append("M")
                    loc == target -> sb.append("T")
                    map.containsKey(loc) -> sb.append(map[loc])
                    else -> sb.append("X")
                }
            }
            lines.add(sb.toString())
        }
        return lines.joinToString("\n")
    }

    class Region(val erosion: Long) {
        fun type(): Char = when (erosion % 3) {
            0L -> '.'
            1L -> '='
            else -> '|'
        }

        override fun toString(): String = type().toString()
    }

    data class V2i(val x: Int, val y: Int) {
        override fun toString(): String = "($x,$y)"
    }

    @Test
    fun sample() {
        assertEquals(114, totalRiskLvl(510, V2i(10, 10)))
    }

    @Test
    fun part1() {
        assertEquals(10395, totalRiskLvl(8112, V2i(13, 743)))
    }
}