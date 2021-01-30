package aoc2018

import aoc2018.Day22.Gear.*
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertEquals

class Day22 {
    fun totalRiskLvl(depth: Int, target: V2i): Int {
        val map = parseMap(depth, target)

        println(mapToString(map, target))
        return totalRiskOfRect(map, V2i(0, 0), target)
    }

    fun shortestPath(depth: Int, target: V2i): Int {
        val map = parseMap(depth, target)

        val memo = HashMap<Pair<V2i, Gear>, Int>().also { it[Pair(V2i(0, 0), TORCH)] = 0 }

        val paths = ArrayList<PossiblePath>().also {
            it += PossiblePath(V2i(0, 0), 0, minTotalTime(V2i(0, 0), TORCH, 0, target), TORCH, V2i(0, 0).toString())
        }

        while (true) {
            val next = paths.removeAt(0)

            if (next.loc == target && next.gear == TORCH) {
                println(next.track)
                println(mapToString(map, target))
                return next.minTotalTime
            }

            val nextPossiblePaths = ArrayList<PossiblePath>()
            addNewPathsThroughSwitchingGear(map, next, nextPossiblePaths, target)
            addNewPathsThroughMoving(next, map, depth, nextPossiblePaths, target)
            val newPaths = nextPossiblePaths
                .filter { it.loc.x >= 0 && it.loc.y >= 0 }
                .filter {
                    val key = Pair(it.loc, it.gear)
                    !memo.containsKey(key) || memo[key]!! > it.dt
                }

            newPaths.forEach { memo[Pair(it.loc, it.gear)] = it.dt }

            insertNewPaths(paths, newPaths)
        }
    }

    private fun addNewPathsThroughSwitchingGear(
        map: HashMap<V2i, Region>,
        next: PossiblePath,
        nextPossiblePaths: ArrayList<PossiblePath>,
        target: V2i,
    ) {
        when (map[next.loc]!!.type()) {
            '.' -> {
                if (next.gear != TORCH) nextPossiblePaths += nextPath(next, target, TORCH)
                else if (next.gear != CLIMB) nextPossiblePaths += nextPath(next, target, CLIMB)
            }
            '=' -> {
                if (next.gear != CLIMB) nextPossiblePaths += nextPath(next, target, CLIMB)
                else if (next.gear != NONE) nextPossiblePaths += nextPath(next, target, NONE)
            }
            '|' -> {
                if (next.gear != TORCH) nextPossiblePaths += nextPath(next, target, TORCH)
                else if (next.gear != NONE) nextPossiblePaths += nextPath(next, target, NONE)
            }
        }
    }

    private fun addNewPathsThroughMoving(
        next: PossiblePath,
        map: HashMap<V2i, Region>,
        depth: Int,
        nextPossiblePaths: ArrayList<PossiblePath>,
        target: V2i,
    ) {
        val u = next.loc.up()
        if (u.x >= 0 && u.y >= 0) addPossiblePath(map, u, depth, next, nextPossiblePaths, target)

        val d = next.loc.down()
        addPossiblePath(map, d, depth, next, nextPossiblePaths, target)

        val l = next.loc.left()
        if (l.x >= 0 && l.y >= 0) addPossiblePath(map, l, depth, next, nextPossiblePaths, target)

        val r = next.loc.right()
        addPossiblePath(map, r, depth, next, nextPossiblePaths, target)
    }

    private fun addPossiblePath(
        map: HashMap<V2i, Region>,
        to: V2i,
        depth: Int,
        fromPath: PossiblePath,
        possiblePathsList: ArrayList<PossiblePath>,
        target: V2i,
    ) {
        if (!map.containsKey(to)) createRegion(map, to.x, to.y, depth)
        if (map[to]!!.canMove(fromPath.gear)) {
            possiblePathsList +=
                PossiblePath(to,
                    fromPath.dt + 1,
                    minTotalTime(to, fromPath.gear, fromPath.dt + 1, target),
                    fromPath.gear, "${fromPath.track}|$to")
        }
    }

    private fun nextPath(next: PossiblePath, target: V2i, gear: Gear) =
        PossiblePath(next.loc, next.dt + 7, minTotalTime(next.loc, gear, next.dt + 7, target), gear,
            "${next.track}|${next.gear}->$gear")

    private fun insertNewPaths(paths: MutableList<PossiblePath>, newPaths: List<PossiblePath>) {
        newPaths.forEach {
            var idx = paths.binarySearch(it, { o1, o2 -> o1.minTotalTime.compareTo(o2.minTotalTime) })
            if (idx < 0) idx = -idx - 1
            paths.add(idx, it)
        }
    }

    private fun minTotalTime(loc: V2i, gear: Gear, mins: Int, target: V2i): Int =
        mins + (if (gear != TORCH) 7 else 0) + loc.dist(target)

    class PossiblePath(val loc: V2i, val dt: Int, val minTotalTime: Int, val gear: Gear, val track: String) {
        override fun toString(): String = "$loc $gear dt=$dt minTotal=$minTotalTime $track"
    }

    enum class Gear {
        CLIMB, TORCH, NONE
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
                    createRegion(map, x, y, depth)
                }
            }
        }
        return map
    }

    private fun createRegion(map: HashMap<V2i, Region>, x: Int, y: Int, depth: Int) {
        when {
            y == 0 -> map[V2i(x, y)] = Region(erosion(x * 16807L, depth))
            x == 0 -> map[V2i(x, y)] = Region(erosion(y * 48271L, depth))
            else -> {
                val left = V2i(x - 1, y)
                val up = V2i(x, y - 1)
                if (!map.containsKey(left)) createRegion(map, left.x, left.y, depth)
                if (!map.containsKey(up)) createRegion(map, up.x, up.y, depth)
                map[V2i(x, y)] = Region(erosion(map[V2i(x - 1, y)]!!.erosion * map[V2i(x, y - 1)]!!.erosion, depth))
            }
        }
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
                }
            }
            lines.add(sb.toString())
        }
        return lines.joinToString("\n")
    }

    class Region(val erosion: Long) {
        fun canMove(gear: Gear): Boolean =
            (type() == '.' && (gear == CLIMB || gear == TORCH)) ||
                    (type() == '=' && (gear == CLIMB || gear == NONE)) ||
                    (type() == '|' && (gear == TORCH || gear == NONE))

        fun type(): Char = when (erosion % 3) {
            0L -> '.'
            1L -> '='
            else -> '|'
        }

        override fun toString(): String = type().toString()
    }

    data class V2i(val x: Int, val y: Int) {
        fun up(): V2i = V2i(x, y - 1)
        fun down(): V2i = V2i(x, y + 1)
        fun left(): V2i = V2i(x - 1, y)
        fun right(): V2i = V2i(x + 1, y)

        fun dist(o: V2i): Int = abs(y - o.y) + abs(x - o.x)

        override fun toString(): String = "($x,$y)"
    }

    @Test
    fun `insert new path in sorted list`() {
        val sorted = ArrayList<String>()
        val insert = listOf("k",
            "c",
            "r",
            "b",
            "m",
            "v",
            "s",
            "a",
            "p",
            "e",
            "n",
            "u",
            "f",
            "j",
            "g",
            "l",
            "h",
            "o",
            "i",
            "d",
            "q",
            "t")
        insert.forEach {
            var idx = sorted.binarySearch(it, { o1, o2 -> o1.compareTo(o2) })
            if (idx < 0) idx = -idx - 1
            sorted.add(idx, it)
        }
        assertEquals("abcdefghijklmnopqrstuv", sorted.joinToString(""))
    }

    @Test
    fun sample() {
        assertEquals(114, totalRiskLvl(510, V2i(10, 10)))
    }

    @Test
    fun `sample part 2`() {
        assertEquals(45, shortestPath(510, V2i(10, 10)))
    }

    @Test
    fun part1() {
        assertEquals(10395, totalRiskLvl(8112, V2i(13, 743)))
    }

    @Test
    fun part2() {
        assertEquals(1010, shortestPath(8112, V2i(13, 743)))
    }
}