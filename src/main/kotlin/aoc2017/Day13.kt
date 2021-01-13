package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day13 {
    fun severity(input: String): Int {
        val map = input.lines()
            .map { FireWall(it) }
            .map { it.id to it }.toMap()
        val max = map.keys.maxOf { it }
        var severity = 0
        for (i in 0..max) {
            if (map.containsKey(i) && map[i]!!.pos == 0) {
                severity += i * map[i]!!.depth
            }
            map.values.forEach { it.step() }
        }
        return severity
    }

    fun minDelay(input: String): Int {
        val map = input.lines()
            .map { FireWall(it) }
            .map { it.id to it }.toMap()
        val max = map.keys.maxOf { it }

        var delay = 0
        var copy = map.map { it.key to it.value.copy() }.toMap()
        while (!isUncaught(copy, max)) {
            delay++
            map.forEach { it.value.step() }
            copy = map.map { it.key to it.value.copy() }.toMap()
        }
        return delay
    }

    fun isUncaught(map: Map<Int, FireWall>, max: Int): Boolean {
        for (i in 0..max) {
            if (map.containsKey(i) && map[i]!!.pos == 0) {
                return false
            }
            map.values.forEach { it.step() }
        }
        return true
    }

    class FireWall {
        val id: Int
        var pos: Int
        var dir: Boolean
        val depth: Int

        constructor(input: String) {
            val parts = input.split(": ")
            id = parts[0].toInt()
            pos = 0
            dir = true
            depth = parts[1].toInt()
        }

        constructor(id: Int, pos: Int, dir: Boolean, depth: Int) {
            this.id = id
            this.pos = pos
            this.dir = dir
            this.depth = depth
        }

        fun copy(): FireWall {
            return FireWall(id, pos, dir, depth)
        }

        fun step() {
            if (dir) {
                if (pos == depth - 1) {
                    pos--
                    dir = false
                } else pos++
            } else {
                if (pos == 0) {
                    pos++
                    dir = true
                } else pos--
            }
        }

        override fun toString(): String {
            return "FireWall(id=$id, pos=$pos, dir=$dir, depth=$depth)"
        }
    }

    @Test
    fun sample() {
        assertEquals(24, severity("0: 3\n" +
                "1: 2\n" +
                "4: 4\n" +
                "6: 4"))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(10, minDelay("0: 3\n" +
                "1: 2\n" +
                "4: 4\n" +
                "6: 4"))
    }

    @Test
    fun part1() {
        assertEquals(2508, severity(File("files/2017/day13.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(3913186, minDelay(File("files/2017/day13.txt").readText()))
    }
}