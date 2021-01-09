package aoc2016

import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.test.assertEquals

class Day17 {
    private val md = MessageDigest.getInstance("MD5")!!

    fun findPath(s: String, to: Pos): String {
        val paths = ArrayDeque<Path>().apply { add(Path(s, Pos(0, 0))) }
        while (true) {
            paths.sort()
            val path = paths.removeFirst()
            if (addNextPaths(path, to, paths)) {
                return findShortestPathTo(to, paths).substring(s.length)
            }
        }
    }

    private fun findShortestPathTo(to: Pos, paths: ArrayDeque<Path>): String {
        for (path in paths) {
            if (path.pos == to) return path.s
        }
        return "no path found"
    }

    private fun addNextPaths(from: Path, to: Pos, paths: ArrayDeque<Path>): Boolean {
        val hash = generateHash(from.s).substring(0, 4).toCharArray()
        if (from.pos.y > 0 && hash[0] in "bcdef") {
            val pos = Pos(from.pos.x, from.pos.y - 1)
            paths += Path("${from.s}U", pos)
            if (pos == to) return true
        }
        if (from.pos.y < 3 && hash[1] in "bcdef") {
            val pos = Pos(from.pos.x, from.pos.y + 1)
            paths += Path("${from.s}D", pos)
            if (pos == to) return true
        }
        if (from.pos.x > 0 && hash[2] in "bcdef") {
            val pos = Pos(from.pos.x - 1, from.pos.y)
            paths += Path("${from.s}L", pos)
            if (pos == to) return true
        }
        if (from.pos.x < 3 && hash[3] in "bcdef") {
            val pos = Pos(from.pos.x + 1, from.pos.y)
            paths += Path("${from.s}R", pos)
            if (pos == to) return true
        }
        return false
    }

    private fun generateHash(s: String): String = BigInteger(1, md.digest(s.toByteArray())).toString(16)

    @Test
    fun part1() {
        println(findPath("njfxhljp", Pos(3, 3)))
    }

    @Test
    fun `find shortest path`() {
        val paths = ArrayDeque<Path>().apply {
            add(Path("ihgpwlah", Pos(0, 0)))
            add(Path("ihgpwlahDDRRRD", Pos(3, 3)))
        }
        assertEquals("DDRRRD", findShortestPathTo(Pos(3, 3), paths).substring("ihgpwlah".length))
    }

    @Test
    fun `find directions with open doors`() {
        val paths = ArrayDeque<Path>().apply { add(Path("hijkl", Pos(0, 0))) }
        paths.sort()
        addNextPaths(paths.first(), Pos(3, 3), paths)
        assertEquals(2, paths.size)
        paths.sort()
        assertEquals(Path("hijklD", Pos(0, 1)), paths.last())
    }

    @Test
    fun `generate hex md5 hash`() {
        assertEquals("ced9fc52441937264674bca3f4ba7588", generateHash("hijkl"))
    }

    @Test
    fun `find path`() {
        assertEquals("DDRRRD", findPath("ihgpwlah", Pos(3, 3)))
        assertEquals("DDUDRLRRUDRD", findPath("kglvqrro", Pos(3, 3)))
    }

    data class Path(val s: String, val pos: Pos) : Comparable<Path> {
        override fun compareTo(other: Path): Int {
            return s.length.compareTo(other.s.length)
        }
    }

    data class Pos(val x: Int, val y: Int)
}