package aoc2016

import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.test.assertEquals

class Day17 {

    val md = MessageDigest.getInstance("MD5")!!

    private fun hash(s: String): String = BigInteger(1, md.digest(s.toByteArray()))
        .toString(16).padStart(32, '0')

    fun findShortestPath(prefix: String, from: Pos, to: Pos): Path {
        val paths = ArrayDeque<Path>().apply { add(Path("", from)) }
        while (paths.isNotEmpty()) {
            paths.sort()
            val path = paths.removeFirst()
            val newPaths = pathsFrom(prefix, path).onEach { if (it.pos == to) return it }
            paths.addAll(newPaths)
        }
        throw Exception("no possible path from $from to $to")
    }

    fun findLongestPath(prefix: String, from: Pos, to: Pos): Path {
        val paths = ArrayDeque<Path>().apply { add(Path("", from)) }
        val result = ArrayList<Path>()
        while (paths.isNotEmpty()) {
            val path = paths.removeFirst()
            val newPaths = pathsFrom(prefix, path)
            result.addAll(newPaths.filter { it.pos == to })
            paths.addAll(newPaths.filter { it.pos != to })
        }
        result.sort()
        return result.last()
    }

    private fun pathsFrom(pre: String, from: Path): List<Path> {
        val res = ArrayList<Path>()
        val hash = hash(pre + from.s).substring(0, 4).toCharArray()
        if (from.pos.y > 0 && hash[0] in "bcdef") {
            val pos = Pos(from.pos.x, from.pos.y - 1)
            res += Path("${from.s}U", pos)
        }
        if (from.pos.y < 3 && hash[1] in "bcdef") {
            val pos = Pos(from.pos.x, from.pos.y + 1)
            res += Path("${from.s}D", pos)
        }
        if (from.pos.x > 0 && hash[2] in "bcdef") {
            val pos = Pos(from.pos.x - 1, from.pos.y)
            res += Path("${from.s}L", pos)
        }
        if (from.pos.x < 3 && hash[3] in "bcdef") {
            val pos = Pos(from.pos.x + 1, from.pos.y)
            res += Path("${from.s}R", pos)
        }
        return res
    }

    @Test
    fun part1() {
        val path = findShortestPath("njfxhljp", Pos(0, 0), Pos(3, 3)).apply { println(s) }
        assertEquals("DURLDRRDRD", path.s)
    }

    @Test
    fun part2() {
        val path = findLongestPath("njfxhljp", Pos(0, 0), Pos(3, 3)).apply { println(s) }
        assertEquals(650, path.s.length)
    }

    @Test
    fun `add paths from`() {
        val pre = "hijkl"
        val paths = ArrayDeque<Path>().apply { add(Path("", Pos(0, 0))) }
        val newPaths = pathsFrom(pre, paths.first())
        assertEquals(1, newPaths.size)
        assertEquals(Path("D", Pos(0, 1)), newPaths.first())
    }

    @Test
    fun `generate hex md5 hash`() {
        assertEquals("ced9fc52441937264674bca3f4ba7588", hash("hijkl"))
    }

    @Test
    fun `find path`() {
        assertEquals("DDRRRD", findShortestPath("ihgpwlah", Pos(0, 0), Pos(3, 3)).s)
        assertEquals("DDUDRLRRUDRD", findShortestPath("kglvqrro", Pos(0, 0), Pos(3, 3)).s)
    }

    @Test
    fun `find the longest path`() {
        assertEquals(830, findLongestPath("ulqzkmiv", Pos(0, 0), Pos(3, 3)).s.length)
        assertEquals(370, findLongestPath("ihgpwlah", Pos(0, 0), Pos(3, 3)).s.length)
        assertEquals(492, findLongestPath("kglvqrro", Pos(0, 0), Pos(3, 3)).s.length)
    }

    data class Path(val s: String, val pos: Pos) : Comparable<Path> {
        override fun compareTo(other: Path): Int {
            return s.length.compareTo(other.s.length)
        }
    }

    data class Pos(val x: Int, val y: Int)
}