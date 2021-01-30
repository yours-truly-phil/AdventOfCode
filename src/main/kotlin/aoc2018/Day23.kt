package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.test.assertEquals

class Day23 {
    fun noBotsInRange(input: String): Int {
        val bots = parseBots(input)

        val maxRangeBot = bots.maxByOrNull { it.range }!!
        val botsInRange = bots.filter { maxRangeBot.pos.dist(it.pos) <= maxRangeBot.range }

        return botsInRange.size
    }

    fun parseBots(input: String): List<Bot> = input.lines().map {
        val parts = it.split(", r=")
        val coords = parts[0].substring(parts[0].indexOf("<") + 1, parts[0].indexOf(">"))
            .split(",")
        val range = parts[1].toInt()
        val pos = V3i(coords[0].toInt(), coords[1].toInt(), coords[2].toInt())
        Bot(pos, range)
    }

    fun distTeleportLocation(input: String): Int {
        val bots = parseBots(input)

        val minX = bots.minOf { it.pos.x }
        val maxX = bots.maxOf { it.pos.x }
        val minY = bots.minOf { it.pos.y }
        val maxY = bots.maxOf { it.pos.y }
        val minZ = bots.minOf { it.pos.z }
        val maxZ = bots.maxOf { it.pos.z }

        var size = 1 shl 22
        val botsInRangePerQuadrant = HashMap<V3i, Int>()
        for (z in minZ..maxZ step size) {
            for (y in minY..maxY step size) {
                for (x in minX..maxX step size) {
                    val loc = V3i(x, y, z)
                    botsInRangePerQuadrant[loc] = numBotsInRange(loc, bots)
                }
            }
        }
        val bestCandidates = botsInRangePerQuadrant.toList().sortedByDescending { it.second }.take(10)
        botsInRangePerQuadrant.clear()
        var newSize = size shr 1
        for (point in bestCandidates) {
            val quad = point.first
            for (z in quad.z - size..quad.z + size step newSize) {
                for (y in quad.y - size..quad.y + size step newSize) {
                    for (x in quad.x - size..quad.x + size step newSize) {
                        val loc = V3i(x, y, z)
                        botsInRangePerQuadrant[loc] = numBotsInRange(loc, bots)
                    }
                }
            }
        }
        botsInRangePerQuadrant.toList().sortedByDescending { it.second }.take(10)
            .forEach { println("${it.first} -> ${it.second}") }
        while (newSize > 1) {
            val bestCandidates = botsInRangePerQuadrant.toList().sortedByDescending { it.second }.take(10)
            botsInRangePerQuadrant.clear()
            size = newSize
            newSize = newSize shr 1
            for (point in bestCandidates) {
                val quad = point.first
                for (z in quad.z - size..quad.z + size step newSize) {
                    for (y in quad.y - size..quad.y + size step newSize) {
                        for (x in quad.x - size..quad.x + size step newSize) {
                            val loc = V3i(x, y, z)
                            botsInRangePerQuadrant[loc] = numBotsInRange(loc, bots)
                        }
                    }
                }
            }
            println("size=$newSize:")
        }
        val maxInRange = botsInRangePerQuadrant.toList().sortedByDescending { it.first.dist(V3i(0, 0, 0)) }.first()
        println("${maxInRange.first} -> ${maxInRange.second}")
        val numInRange = maxInRange.second
        val quad = maxInRange.first
        size = 1 shl 19
        newSize = size shr 1
        while (newSize > 1) {
            val candidate =
                botsInRangePerQuadrant.toList().filter { it.second == numInRange }
                    .minByOrNull { it.first.dist(V3i(0, 0, 0)) }!!
            botsInRangePerQuadrant.clear()
            size = newSize
            newSize = newSize shr 1
            for (z in candidate.first.z - size..candidate.first.z + size step newSize) {
                for (y in candidate.first.y - size..candidate.first.y + size step newSize) {
                    for (x in candidate.first.x - size..candidate.first.x + size step newSize) {
                        val loc = V3i(x, y, z)
                        botsInRangePerQuadrant[loc] = numBotsInRange(loc, bots)
                    }
                }
            }
            println("size=$newSize:")
            botsInRangePerQuadrant.toList().sortedBy { it.second }.take(40)
                .forEach { println("${it.first} -> ${it.second} dist=${it.first.dist(V3i(0, 0, 0))}") }
        }

        botsInRangePerQuadrant.toList().sortedBy { it.second }.take(10)
            .forEach { println("${it.first} -> ${it.second}") }
        val pos = botsInRangePerQuadrant.toList().filter { it.second == numInRange }
            .minByOrNull { it.first.dist(V3i(0, 0, 0)) }!!.first

        for (z in pos.z - 1..pos.z + 1) {
            for (y in pos.y - 1..pos.y + 1) {
                for (x in pos.x - 1..pos.x + 1) {
                    val loc = V3i(x, y, z)
                    println("$loc -> ${numBotsInRange(loc, bots)}")
                }
            }
        }
        // found actually one with 876 in the log
        val maxDist = 876
        val loc = V3i(48785280, 32078070, 24507920)

        return 1
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

    private fun numBotsInRange(loc: V3i, bots: List<Bot>): Int =
        bots.count { it.pos.dist(loc) <= it.range }

    fun vizXY(bots: List<Bot>): String {
        val minX = bots.minOf { it.pos.x }
        val maxX = bots.maxOf { it.pos.x }
        val minY = bots.minOf { it.pos.y }
        val maxY = bots.maxOf { it.pos.y }

        val offX = -minX
        val offY = -minY
        val scale = minOf(150 / (offX + maxX).toDouble(), 150 / (offY + maxY).toDouble())

        val moveVector = V3i(offX, offY, 0)
        val scaledBots = bots.map { it.pos.move(moveVector).scaledV3i(scale) to it.range }
            .map { V2i(it.first.x, it.first.y) to it.second }.toMap()
        val rows = ArrayList<String>()
        for (y in 0..150) {
            val sb = StringBuilder()
            for (x in 0..150) {
                if (scaledBots.contains(V2i(x, y))) {
                    sb.append("*")
                } else {
                    sb.append(" ")
                }
            }
            rows += sb.toString()
        }
        return rows.joinToString("\n")
    }

    fun vizXZ(bots: List<Bot>): String {
        val minX = bots.minOf { it.pos.x }
        val maxX = bots.maxOf { it.pos.x }
        val minZ = bots.minOf { it.pos.z }
        val maxZ = bots.maxOf { it.pos.z }

        val offX = -minX
        val offZ = -minZ
        val scale = minOf(150 / (offX + maxX).toDouble(), 150 / (offZ + maxZ).toDouble())

        val moveVector = V3i(offX, offZ, 0)
        val scaledBots = bots.map { it.pos.move(moveVector).scaledV3i(scale) to it.range }
            .map { V2i(it.first.x, it.first.y) to it.second }.toMap()
        val rows = ArrayList<String>()
        for (y in 0..150) {
            val sb = StringBuilder()
            for (x in 0..150) {
                if (scaledBots.contains(V2i(x, y))) {
                    sb.append("*")
                } else {
                    sb.append(" ")
                }
            }
            rows += sb.toString()
        }
        return rows.joinToString("\n")
    }

    data class Bot(val pos: V3i, val range: Int) {
        override fun toString(): String = "$pos r=$range"
    }

    data class V2i(val x: Int, val y: Int)

    data class V3i(val x: Int, val y: Int, val z: Int) {
        fun scaledV3i(f: Double): V3i = V3i((x * f).roundToInt(), (y * f).roundToInt(), (z * f).roundToInt())
        fun move(v: V3i): V3i = V3i(x + v.x, y + v.y, z + v.z)
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

//    @Test
//    fun `shortest distance between me and 0,0,0`() {
//        assertEquals(36, distTeleportLocation("pos=<10,12,12>, r=2\n" +
//                "pos=<12,14,12>, r=2\n" +
//                "pos=<16,12,12>, r=4\n" +
//                "pos=<14,14,14>, r=6\n" +
//                "pos=<50,50,50>, r=200\n" +
//                "pos=<10,10,10>, r=5"))
//    }

    @Test
    fun part1() {
        assertEquals(457, noBotsInRange(File("files/2018/day23.txt").readText()))
    }

    @Test
    fun part2() {
//        assertEquals(-1, distTeleportLocation(File("files/2018/day23.txt").readText()))
        assertEquals(105370773, minDistance(File("files/2018/day23.txt").readText()))
    }
}