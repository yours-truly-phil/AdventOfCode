package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

class Day23 {
    fun noBotsInRange(input: String): Int {
        val bots = input.lines().map {
            val parts = it.split(", r=")
            val coords = parts[0].substring(parts[0].indexOf("<") + 1, parts[0].indexOf(">"))
                .split(",")
            val range = parts[1].toInt()
            val pos = V3i(coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
            Bot(pos, range)
        }

        val maxRangeBot = bots.maxByOrNull { it.range }!!
        val botsInRange = bots.filter { maxRangeBot.pos.dist(it.pos) <= maxRangeBot.range }

        return botsInRange.size
    }

    class Bot(val pos: V3i, val range: Int) {
        override fun toString(): String = "$pos r=$range"
    }

    class V3i(val x: Int, val y: Int, val z: Int) {
        fun dist(o: V3i): Int = abs(x - o.x) + abs(y - o.y) + abs(z - o.z)
        override fun toString(): String = "($x,$y,$z)"
    }

    @Test
    fun `nanobots in range of strongest nanobot`() {
        assertEquals(7, noBotsInRange("pos=<0,0,0>, r=4\n" +
                "pos=<1,0,0>, r=1\n" +
                "pos=<4,0,0>, r=3\n" +
                "pos=<0,2,0>, r=1\n" +
                "pos=<0,5,0>, r=3\n" +
                "pos=<0,0,3>, r=1\n" +
                "pos=<1,1,1>, r=1\n" +
                "pos=<1,1,2>, r=1\n" +
                "pos=<1,3,1>, r=1"))
    }

    @Test
    fun part1() {
        assertEquals(457, noBotsInRange(File("files/2018/day23.txt").readText()))
    }
}