package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day24 {
    private fun minNumSteps(input: String): Int {
        val parsed = parse(input)
        val poi = parsed.first
        val map = parsed.second
        val perms = permutationsOfPointsOfInterest(poi).filter { it[0] == '1' }
        var min = Int.MAX_VALUE
        for (perm in perms) {
            var totalSteps = 0
            for (i in 0 until perm.length - 1) {
                val locFrom = perm[i].toString().toInt()
                val locTo = perm[i + 1].toString().toInt()
                totalSteps += shortestPath(map, poi[locFrom]!!, poi[locTo]!!)
            }
            min = minOf(min, totalSteps)
        }
        return min
    }

    private fun minNumStepsBackToStart(input: String): Int {
        val parsed = parse(input)
        val poi = parsed.first
        val map = parsed.second
        val perms = permutationsOfPointsOfInterest(poi).filter { it[0] == '1' }.map { it + "1" }
        var min = Int.MAX_VALUE
        for (perm in perms) {
            var totalSteps = 0
            for (i in 0 until perm.length - 1) {
                val locFrom = perm[i].toString().toInt()
                val locTo = perm[i + 1].toString().toInt()
                totalSteps += shortestPath(map, poi[locFrom]!!, poi[locTo]!!)
            }
            min = minOf(min, totalSteps)
        }
        return min
    }

    private fun permutationsOfPointsOfInterest(poi: Map<Int, Loc>): ArrayList<String> {
        val res = ArrayList<String>()
        Day21().apply {
            val sb = StringBuilder()
            (0 until poi.size).forEach { sb.append(it + 1) }
            perms(sb.toString(), "", res)
        }
        return res
    }

    private fun shortestPath(map: Array<IntArray>, from: Loc, to: Loc): Int {
        val routes = ArrayDeque<Route>().also {
            it.addLast(Route(from, 0))
        }
        val seen = HashSet<Loc>()

        while (routes.isNotEmpty()) {
            val route = routes.removeFirst()
            if (route.to == to) return route.steps

            val up = route.to.up()
            val down = route.to.down()
            val left = route.to.left()
            val right = route.to.right()
            if (isNewLoc(up, map, seen)) {
                routes.addLast(Route(up, route.steps + 1))
                seen.add(up)
            }
            if (isNewLoc(down, map, seen)) {
                routes.addLast(Route(down, route.steps + 1))
                seen.add(down)
            }
            if (isNewLoc(left, map, seen)) {
                routes.addLast(Route(left, route.steps + 1))
                seen.add(left)
            }
            if (isNewLoc(right, map, seen)) {
                routes.addLast(Route(right, route.steps + 1))
                seen.add(right)
            }
        }
        return -1
    }

    private fun isNewLoc(loc: Loc, map: Array<IntArray>, seen: HashSet<Loc>): Boolean {
        if (seen.contains(loc)) return false

        return loc.x >= 0 && loc.x < map[0].size
                && loc.y >= 0 && loc.y < map.size
                && map[loc.y][loc.x] != 9
    }

    private fun parse(input: String): Pair<HashMap<Int, Loc>, Array<IntArray>> {
        val poi = HashMap<Int, Loc>()
        val lines = input.lines()
        val width = lines[0].length
        val height = lines.size
        val grid = Array(height) { IntArray(width) }
        for (y in 0 until height) {
            for (x in 0 until width) {
                when (val c = lines[y][x]) {
                    in "0123456789" -> {
                        val v = c.toString().toInt() + 1
                        grid[y][x] = v
                        poi[v] = Loc(x, y)
                    }
                    '.' -> grid[y][x] = 0
                    else -> grid[y][x] = 9
                }
            }
        }
        return Pair(poi, grid)
    }

    class Route(val to: Loc, val steps: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Route

            if (to != other.to) return false

            return true
        }

        override fun hashCode(): Int {
            return to.hashCode()
        }
    }

    data class Loc(val x: Int, val y: Int) {
        fun up() = Loc(x, y - 1)
        fun down() = Loc(x, y + 1)
        fun left() = Loc(x - 1, y)
        fun right() = Loc(x + 1, y)
    }

    @Test
    fun sample() {
        assertEquals(14, minNumSteps("###########\n" +
                "#0.1.....2#\n" +
                "#.#######.#\n" +
                "#4.......3#\n" +
                "###########"))
    }

    @Test
    fun part1() {
        assertEquals(430, minNumSteps(File("files/2016/day24.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(700, minNumStepsBackToStart(File("files/2016/day24.txt").readText()))
    }
}