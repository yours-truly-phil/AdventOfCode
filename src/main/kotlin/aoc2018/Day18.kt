package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day18 {
    fun totalValueLumberCollection(input: String, mins: Int): Int {
        val world = input.lines().map { it.toCharArray() }.toTypedArray()
        val memo = HashMap<String, Int>()
        var curRep = 0
        var foundCycle = false
        while (curRep < mins) {
            val tmp = Array(world.size) { CharArray(world.maxOf { it.size }) }
            world.forEachIndexed { y, row ->
                row.forEachIndexed { x, c ->
                    tmp[y][x] = world[y][x]
                    if (c == '.' && countNeighbors(x, y, world, '|') >= 3) tmp[y][x] = '|'
                    if (c == '|' && countNeighbors(x, y, world, '#') >= 3) tmp[y][x] = '#'
                    if (c == '#' && (countNeighbors(x, y, world, '#') < 1
                                || countNeighbors(x, y, world, '|') < 1)
                    ) tmp[y][x] = '.'
                }
            }
            tmp.forEachIndexed { y, row -> row.forEachIndexed { x, c -> world[y][x] = c } }

            curRep++
            if (!foundCycle) {
                val strRep = world.joinToString("\n") { it.joinToString("") }
                if (memo.contains(strRep)) {
                    val dt = curRep - memo[strRep]!!
                    while (curRep < mins) {
                        curRep += dt
                    }
                    curRep -= dt
                    foundCycle = true
                } else {
                    memo[strRep] = curRep
                }
            }
        }

        val noWood = world.map { it.count { c -> c == '|' } }.sum()
        val noYards = world.map { it.count { c -> c == '#' } }.sum()
        return noWood * noYards
    }

    private fun countNeighbors(sourceX: Int, sourceY: Int, world: Array<CharArray>, findMe: Char): Int {
        var count = 0
        for (y in sourceY - 1..sourceY + 1) for (x in sourceX - 1..sourceX + 1)
            if (y in world.indices && x in world[y].indices
                && (x != sourceX || y != sourceY)
                && world[y][x] == findMe
            ) count++
        return count
    }

    @Test
    fun sample() {
        assertEquals(1147, totalValueLumberCollection(".#.#...|#.\n" +
                ".....#|##|\n" +
                ".|..|...#.\n" +
                "..|#.....#\n" +
                "#.#|||#|#|\n" +
                "...#.||...\n" +
                ".|....|...\n" +
                "||...#|.#|\n" +
                "|.||||..|.\n" +
                "...#.|..|.", 10))
    }

    @Test
    fun part1() {
        assertEquals(514944, totalValueLumberCollection(File("files/2018/day18.txt").readText(), 10))
    }

    @Test
    fun part2() {
        assertEquals(193050, totalValueLumberCollection(File("files/2018/day18.txt").readText(), 1000000000))
    }
}