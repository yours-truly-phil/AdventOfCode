package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day22 {
    private fun countBurstsToInfected(input: String, bursts: Int): Int {
        val nodes =
            input.lines().mapIndexed { row, s ->
                s.mapIndexed { col, c ->
                    val offset = input.lines().size / 2
                    Pos(col - offset, row - offset) to (c == '#')
                }
            }.flatten().toMap().toMutableMap()
        val carrier = Carrier(Pos(0, 0), 0) // 0 = N, 1 = E, 2 = S, 3 = W
        var count = 0
        repeat(bursts) {
            nodes.computeIfAbsent(Pos(carrier.pos.x, carrier.pos.y)) { false }
            if (nodes[carrier.pos]!!) {
                carrier.dir++
                carrier.dir %= 4
                nodes[carrier.pos] = false
            } else {
                carrier.dir += 3
                carrier.dir %= 4
                nodes[carrier.pos] = true
                count++
            }
            carrier.forward()
        }
        return count
    }

    private fun countBurstsToInfectedPart2(input: String, bursts: Int): Int {
        val nodes =
            input.lines().mapIndexed { row, s ->
                s.mapIndexed { col, c ->
                    val offset = input.lines().size / 2
                    Pos(col - offset, row - offset) to c
                }
            }.flatten().toMap().toMutableMap()
        val carrier = Carrier(Pos(0, 0), 0) // 0 = N, 1 = E, 2 = S, 3 = W
        var count = 0
        repeat(bursts) {
            nodes.computeIfAbsent(Pos(carrier.pos.x, carrier.pos.y)) { '.' }
            if (nodes[carrier.pos]!! == '#') {
                carrier.dir++
                carrier.dir %= 4
                nodes[carrier.pos] = 'F'
            } else if (nodes[carrier.pos]!! == 'W') {
                nodes[carrier.pos] = '#'
                count++
            } else if (nodes[carrier.pos]!! == 'F') {
                carrier.dir += 2
                carrier.dir %= 4
                nodes[carrier.pos] = '.'
            } else {
                carrier.dir += 3
                carrier.dir %= 4
                nodes[carrier.pos] = 'W'
            }
            carrier.forward()
        }
        return count
    }

    private fun printGrid(carrier: Carrier, nodes: MutableMap<Pos, Boolean>) {
        for (y in -4..4) {
            for (x in -4..4) {
                val p = Pos(x, y)

                if (carrier.pos == p) print("[") else print(" ")
                if (nodes.contains(p) && nodes[p]!!) print("#") else print(".")
                if (carrier.pos == p) print("]") else print(" ")
            }
            println()
        }
    }

    data class Carrier(val pos: Pos, var dir: Int) {
        fun forward() {
            when (dir) {
                0 -> pos.y--
                1 -> pos.x++
                2 -> pos.y++
                3 -> pos.x--
            }
        }
    }

    data class Pos(var x: Int, var y: Int)

    @Test
    fun `part 2 sample`() {
        assertEquals(2511944, countBurstsToInfectedPart2("..#\n" +
                "#..\n" +
                "...", 10000000))
    }

    @Test
    fun sample() {
        assertEquals(5587, countBurstsToInfected("..#\n" +
                "#..\n" +
                "...", 10000))
    }

    @Test
    fun part1() {
        assertEquals(5552, countBurstsToInfected(File("files/2017/day22.txt").readText(), 10000))
    }

    @Test
    fun part2() {
        assertEquals(2511527, countBurstsToInfectedPart2(File("files/2017/day22.txt").readText(), 10000000))
    }
}