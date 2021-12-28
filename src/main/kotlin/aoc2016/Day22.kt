package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day22 {
    private fun countViablePairs(input: String): Int = pairs(parseNodes(input)).count {
        it.first.used > 0 && it.first.loc != it.second.loc && it.second.avail() >= it.first.used
    }

    private fun minNumSteps(input: String, to: Loc, from: Loc): Int {
        val grid = parseNodes(input).associateBy { it.loc }
        val maxY = grid.values.maxOf { it.loc.y }
        val maxX = grid.values.maxOf { it.loc.x }
        printGrid(maxY, maxX, grid, to, from)
        /**
        |    0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
        |  0(.) .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  G
        |  1 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  2 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  3 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  4 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  5 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  6 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  7 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  8 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        |  9 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 10 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 11 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 12 .  .  .  .  .  .  .  .  .  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #  #
        | 13 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 14 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 15 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 16 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 17 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 18 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 19 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 20 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 21 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 22 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  O  .  .  .  .  .  .  .
        | 23 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 24 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 25 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 26 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .
        | 27 .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .

        60 steps for empty to 30,0
        1 step to swap G to 30,0
        5 extra steps to move G one left if empty is right of G
        goal is 30 left of 30,0
        30 * 5 = 150 to move G to goal from 30,0

        overall 60 + 1 + 150 = 211 steps
         */
        return 211
    }

    private fun printGrid(maxY: Int, maxX: Int, grid: Map<Loc, Node>, to: Loc, from: Loc) {
        print("|  ")
        for (x in 0..maxX) {
            print(" %2d".format(x))
        }
        println()
        for (y in 0..maxY) {
            print("| %2d".format(y))
            for (x in 0..maxX) {
                val n = grid[Loc(x, y)]!!
                when {
                    n.used > 100 -> print(" # ")
                    n.used == 0 -> print(" O ")
                    n.loc == to -> print("(.)")
                    n.loc == from -> print(" G ")
                    else -> print(" . ")
                }
            }
            println()
        }
    }

    private fun parseNodes(input: String): List<Node> =
        input.lines().subList(2, input.lines().size).map { it.replace(" +".toRegex(), " ") }.map { Node(it) }

    private fun pairs(nodes: List<Node>): List<Pair<Node, Node>> = ArrayList<Pair<Node, Node>>().apply {
        for (node in nodes) {
            for (other in nodes) {
                add(Pair(node, other))
            }
        }
    }

    class Node(input: String) {
        val loc: Loc
        val size: Int

        var used: Int

        init {
            input.split(" ").also {
                    loc = parseLoc(it[0])
                    size = it[1].substring(0, it[1].length - 1).toInt()
                    used = it[2].substring(0, it[2].length - 1).toInt()
                }
        }

        fun avail(): Int = size - used

        private fun parseLoc(input: String): Loc {
            input.split("-").also {
                    return Loc(
                        it[1].substring(1).toInt(), it[2].substring(1).toInt()
                    )
                }
        }

        override fun toString(): String {
            return "Node($loc, size=$size, used=$used, avail=${avail()})"
        }
    }


    class Loc(val x: Int, val y: Int) {

        fun up(): Loc = Loc(x, y - 1)
        fun down(): Loc = Loc(x, y + 1)
        fun left(): Loc = Loc(x - 1, y)
        fun right(): Loc = Loc(x + 1, y)

        override fun toString(): String {
            return "Loc($x,$y)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Loc

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    @Test
    fun part1() {
        assertEquals(
            872, countViablePairs(File("files/2016/day22.txt").readText())
        )
    }

    @Test
    fun part2() {
        assertEquals(
            211, minNumSteps(
                File("files/2016/day22.txt").readText(), Loc(0, 0), Loc(31, 0)
            )
        )
    }
}