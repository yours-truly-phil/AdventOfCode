package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

class Day23 {
    private fun noBotsInRange(input: String): Int {
        val bots = parseBots(input)

        val maxRangeBot = bots.maxByOrNull { it.range }!!
        val botsInRange = bots.filter { maxRangeBot.pos.dist(it.pos) <= maxRangeBot.range }

        return botsInRange.size
    }

    private fun parseBots(input: String): List<Bot> = input.lines().map {
        val parts = it.split(", r=")
        val coords = parts[0].substring(parts[0].indexOf("<") + 1, parts[0].indexOf(">")).split(",")
        val range = parts[1].toInt()
        val pos = V3i(coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
        Bot(pos, range)
    }

    private fun minDistance(input: String): Int {
        val bots = parseBots(input)
        val maxDist = 903
        val zero = V3i(0, 0, 0)
        val loc = V3i(48785112, 32077905, 24507756)
        val distances = HashMap<V3i, Int>().also { it[loc] = maxDist }
        var min = distances.filter { it.value == maxDist }.minByOrNull { it.key.dist(zero) }
        var lastMax = maxDist
        while (min != null) {
            distances.clear()
            val p = min.key
            for (z in p.z - 10..p.z + 10) {
                for (y in p.y - 10..p.y + 10) {
                    for (x in p.x - 10..p.x + 10) {
                        val l = V3i(x, y, z)
                        distances[l] = numBotsInRange(l, bots)
                    }
                }
            }
            val newMax = distances.maxOf { it.value }
            if (newMax == lastMax) break
            lastMax = newMax

            min = distances.filter { it.value == lastMax }.minByOrNull { it.key.dist(zero) }
            println("min=$min dist=${min!!.key.dist(zero)}")
            distances.forEach { (t, u) -> println("$t -> $u dist=${t.dist(zero)}") }
        }

        return min!!.key.dist(zero)
    }

    private fun numBotsInRange(loc: V3i, bots: List<Bot>): Int = bots.count { it.pos.dist(loc) <= it.range }

    data class Bot(val pos: V3i, val range: Int) {
        override fun toString(): String = "$pos r=$range"
    }

    data class V3i(val x: Int, val y: Int, val z: Int) {
        fun move(v: V3i): V3i = V3i(x + v.x, y + v.y, z + v.z)
        fun dist(o: V3i): Int = abs(x - o.x) + abs(y - o.y) + abs(z - o.z)
        override fun toString(): String = "($x,$y,$z)"
    }

    @Test
    fun nanobotsInRangeOfStrongestNanobot() {
        assertEquals(
            7, noBotsInRange(
                """pos=<0,0,0>, r=4
pos=<1,0,0>, r=1
pos=<4,0,0>, r=3
pos=<0,2,0>, r=1
pos=<0,5,0>, r=3
pos=<0,0,3>, r=1
pos=<1,1,1>, r=1
pos=<1,1,2>, r=1
pos=<1,3,1>, r=1"""
            )
        )
    }

    @Test
    fun part1() {
        assertEquals(457, noBotsInRange(File("files/2018/day23.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(105370773, minDistance(File("files/2018/day23.txt").readText()))
    }
}