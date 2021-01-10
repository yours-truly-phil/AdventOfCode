package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day22 {
    private fun countViablePairs(input: String): Int {
        val nodes = input.lines().subList(2, input.lines().size)
            .map { it.replace(" +".toRegex(), " ") }
            .map { Node(it) }

        return pairs(nodes).filter {
            it.first.used > 0 &&
                    it.first.loc != it.second.loc &&
                    it.second.avail() >= it.first.used
        }.count()
    }

    private fun pairs(nodes: List<Node>): List<Pair<Node, Node>> {
        val pairs = ArrayList<Pair<Node, Node>>()
        for (node in nodes) {
            for (other in nodes) {
                pairs.add(Pair(node, other))
            }
        }
        return pairs
    }

    class Node(input: String) {
        val loc: Loc
        val size: Int
        var used: Int

        init {
            input.split(" ")
                .also {
                    loc = Loc(it[0])
                    size = it[1].substring(0, it[1].length - 1).toInt()
                    used = it[2].substring(0, it[2].length - 1).toInt()
                }
        }

        fun avail(): Int = size - used

        override fun toString(): String {
            return "Node($loc, size=$size, used=$used, avail=${avail()})"
        }
    }

    class Loc(input: String) {
        val x: Int
        val y: Int

        init {
            input.split("-")
                .also {
                    x = it[1].substring(1).toInt()
                    y = it[2].substring(1).toInt()
                }
        }

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
        assertEquals(-1, countViablePairs(File("files/2016/day22.txt").readText()))
    }
}