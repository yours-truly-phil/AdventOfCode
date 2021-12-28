@file:Suppress("FunctionName")

package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("DANGEROUS_CHARACTERS")
class Day20 {
    private fun maxDoorsToReachRoom(input: String): Int {
        val map = createMap(input)
        println(map.toString())
        val distMap = distanceMap(map)
        return distMap.maxOf { it.value }
    }

    private fun distanceMap(map: Map): HashMap<Loc, Int> {
        val distMap = HashMap<Loc, Int>().also { it[Loc(0, 0)] = 0 }
        val rooms = ArrayDeque<Loc>().also { it.add(Loc(0, 0)) }
        while (rooms.isNotEmpty()) {
            val loc = rooms.removeFirst()
            val doors = map.map[loc]!!
            if (doors.contains(0)) {
                updateDistMap(distMap, loc.n(), rooms, loc)
            }
            if (doors.contains(1)) {
                updateDistMap(distMap, loc.e(), rooms, loc)
            }
            if (doors.contains(2)) {
                updateDistMap(distMap, loc.s(), rooms, loc)
            }
            if (doors.contains(3)) {
                updateDistMap(distMap, loc.w(), rooms, loc)
            }
        }
        return distMap
    }

    @Suppress("SameParameterValue")
    private fun noRoomsShortestPathAtLeastNumOfDoors(input: String, numDoors: Int): Int {
        val map = createMap(input)
        val distMap = distanceMap(map)
        return distMap.filter { it.value >= numDoors }.count()
    }

    private fun updateDistMap(distMap: HashMap<Loc, Int>, next: Loc, rooms: ArrayDeque<Loc>, loc: Loc) {
        if (!distMap.containsKey(next)) {
            distMap[next] = distMap[loc]!! + 1
            rooms.addLast(next)
        } else {
            distMap[next] = minOf(distMap[next]!!, distMap[loc]!! + 1)
        }
    }

    private fun createMap(s: String): Map {
        val map = Map()
        var loc = Loc(0, 0)
        val memo = HashSet<String>()
        val strings = ArrayDeque<Pair<Loc, String>>()
        strings.add(Pair(loc, s.substring(1, s.length - 1)))
        map.map.computeIfAbsent(loc) { HashSet() }
        while (strings.isNotEmpty()) {
            val next = strings.removeFirst()
            memo.add("${next.first}-${next.second}")
            val nextStr = next.second
            loc = next.first
            val parseTo = dirsUntil(nextStr)

            for (i in 0 until parseTo) {
                val here = map.map[loc]!!
                when (nextStr[i]) {
                    'N' -> {
                        here.add(0)
                        val there = loc.n()
                        map.map.computeIfAbsent(there) { HashSet() }
                        map.map[there]!!.add(2)
                        loc = there
                    }
                    'E' -> {
                        here.add(1)
                        val there = loc.e()
                        map.map.computeIfAbsent(there) { HashSet() }
                        map.map[there]!!.add(3)
                        loc = there
                    }
                    'S' -> {
                        here.add(2)
                        val there = loc.s()
                        map.map.computeIfAbsent(there) { HashSet() }
                        map.map[there]!!.add(0)
                        loc = there
                    }
                    'W' -> {
                        here.add(3)
                        val there = loc.w()
                        map.map.computeIfAbsent(there) { HashSet() }
                        map.map[there]!!.add(1)
                        loc = there
                    }
                }
            }

            val remainder = nextStr.substring(parseTo)
            if (remainder.isNotEmpty()) {
                var lvl = 0
                for (i in remainder.indices) {
                    if (remainder[i] == '(') lvl++
                    else if (remainder[i] == ')') lvl--
                    if (lvl == 0) {
                        val splitRes = splitInPartsAndRest(remainder, i)
                        for (part in splitRes.second) {
                            val newStr = part + splitRes.first
                            if (!memo.contains("${loc}-$newStr")) {
                                strings.addLast(Pair(loc, part + splitRes.first))
                                memo.add("${loc}-$newStr")
                            }
                        }
                        break
                    }
                }
            }
        }
        return map
    }

    private fun dirsUntil(s: String): Int {
        for (i in s.indices) {
            if (s[i] !in "NESW") return i
        }
        return s.length
    }

    private fun splitInPartsAndRest(s: String, endIdx: Int): Pair<String, ArrayList<String>> {
        val block = s.substring(1, endIdx)
        val rest = s.substring(endIdx + 1)
        val parts = ArrayList<String>()
        var dpth = 0
        val cur = StringBuilder()
        for (c in block) {
            if (dpth == 0) {
                when (c) {
                    in "NEWS" -> {
                        cur.append(c)
                    }
                    '(' -> {
                        dpth++
                        cur.append(c)
                    }
                    '|', ')' -> {
                        parts.add(cur.toString())
                        cur.clear()
                    }
                }
            } else {
                if (c == ')') {
                    dpth--
                } else if (c == '(') {
                    dpth++
                }
                cur.append(c)
            }
        }
        parts.add(cur.toString())
        return Pair(rest, parts)
    }

    @Test
    fun splitStringInPartsInParenthesisAndRemaindingString() {
        val (r1, p1) = splitInPartsAndRest("(E|)NN", 3)
        assertTrue(p1.containsAll(listOf("E", "")))
        assertEquals("NN", r1)
        val (rest, parts) = splitInPartsAndRest("(N|E(SS|(W|E)|)N|SSS(W|N(N|S))ENNNNN", 29)
        assertTrue(parts.containsAll(listOf("N", "E(SS|(W|E)|)N", "SSS(W|N(N|S)")))
        assertEquals("ENNNNN", rest)
    }

    data class Loc(val x: Int, val y: Int) {
        fun n(): Loc = Loc(x, y - 1)
        fun e(): Loc = Loc(x + 1, y)
        fun s(): Loc = Loc(x, y + 1)
        fun w(): Loc = Loc(x - 1, y)
    }

    class Map {
        val map = HashMap<Loc, HashSet<Int>>()

        private fun maxX(): Int = map.maxOf { it.key.x }
        private fun minX(): Int = map.minOf { it.key.x }
        private fun maxY(): Int = map.maxOf { it.key.y }
        private fun minY(): Int = map.minOf { it.key.y }

        override fun toString(): String {
            val arr = Array(2 * (maxY() - minY() + 1) + 1) {
                CharArray(2 * (maxX() - minX() + 1) + 1) { 'X' }
            }
            for (roomY in minY()..maxY()) {
                for (roomX in minX()..maxX()) {
                    val start = roomX == 0 && roomY == 0
                    if (map.containsKey(Loc(roomX, roomY))) {
                        val doors = map[Loc(roomX, roomY)]!!
                        val x = 2 * (roomX - minX())
                        val y = 2 * (roomY - minY())

                        arr[y][x] = '#'
                        if (doors.contains(3)) arr[y + 1][x] = '|' else arr[y + 1][x] = '#'
                        arr[y + 2][x] = '#'

                        if (doors.contains(0)) arr[y][x + 1] = '-' else arr[y][x + 1] = '#'
                        if (start) arr[y + 1][x + 1] = 'X' else arr[y + 1][x + 1] = '.'
                        if (doors.contains(2)) arr[y + 2][x + 1] = '-' else arr[y + 2][x + 1] = '#'

                        arr[y][x + 2] = '#'
                        if (doors.contains(1)) arr[y + 1][x + 2] = '|' else arr[y + 1][x + 2] = '#'
                        arr[y + 2][x + 2] = '#'
                    }
                }
            }
            return arr.joinToString("\n") { it.joinToString("") }
        }
    }

    @Test
    fun `map of ^WNE$`() {
        assertEquals("#####\n" +
                "#.|.#\n" +
                "#-###\n" +
                "#.|X#\n" +
                "#####", createMap("^WNE\$").toString())
    }

    @Test
    fun `map of ^ENWWW(NEEE|SSE(EE|N))$`() {
        assertEquals(
            """#########
#.|.|.|.#
#-#######
#.|.|.|.#
#-#####-#
#.#.#X|.#
#-#-#####
#.|.|.|.#
#########""", createMap("^ENWWW(NEEE|SSE(EE|N))\$").toString())
    }

    @Test
    fun `map of ^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$`() {
        assertEquals(
            """###########
#.|.#.|.#.#
#-###-#-#-#
#.|.|.#.#.#
#-#####-#-#
#.#.#X|.#.#
#-#-#####-#
#.#.|.|.|.#
#-###-###-#
#.|.|.#.|.#
###########""", createMap("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN\$").toString())
    }

    @Test
    fun `map of ^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$`() {
        assertEquals(
            """#############
#.|.|.|.|.|.#
#-#####-###-#
#.#.|.#.#.#.#
#-#-###-#-#-#
#.#.#.|.#.|.#
#-#-#-#####-#
#.#.#.#X|.#.#
#-#-#-###-#-#
#.|.#.|.#.#.#
###-#-###-#-#
#.|.#.|.|.#.#
#############""", createMap("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$").toString())
    }

    @Test
    fun `map of ^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$`() {
        assertEquals(
            """###############
#.|.|.|.#.|.|.#
#-###-###-#-#-#
#.|.#.|.|.#.#.#
#-#########-#-#
#.#.|.|.|.|.#.#
#-#-#########-#
#.#.#.|X#.|.#.#
###-#-###-#-#-#
#.|.#.#.|.#.|.#
#-###-#####-###
#.|.#.|.|.#.#.#
#-#-#####-#-#-#
#.#.|.|.|.#.|.#
###############""",
            createMap("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$").toString())
    }

    @Test
    fun sample() {
        assertEquals(3, maxDoorsToReachRoom("^WNE$"))
        assertEquals(10, maxDoorsToReachRoom("^ENWWW(NEEE|SSE(EE|N))\$"))
        assertEquals(18, maxDoorsToReachRoom("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN\$"))
        assertEquals(23, maxDoorsToReachRoom("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$"))
        assertEquals(31, maxDoorsToReachRoom("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$"))
    }

    @Test
    fun part1() {
        assertEquals(3835, maxDoorsToReachRoom(File("files/2018/day20.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(8520, noRoomsShortestPathAtLeastNumOfDoors(File("files/2018/day20.txt").readText(), 1000))
    }
}