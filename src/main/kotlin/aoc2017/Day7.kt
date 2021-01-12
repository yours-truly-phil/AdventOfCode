package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day7 {
    fun nameOfRoot(input: String): String {
        val nodes = input.lines().map {
            val parts = it.split(" -> ")
            val left = parts[0].split(" ")
            val name = left[0]
            val weight = left[1].substring(1, left[1].length - 1).toInt()
            Node(name, weight, ArrayList())
        }.map { it.name to it }.toMap()
        input.lines().forEach {
            val parts = it.split(" -> ")
            val root = parts[0].split(" ")[0]
            if (parts.size > 1) {
                val names = parts[1].split(", ")
                val node = nodes[root]!!
                for (name in names) {
                    node.nodes.add(nodes[name]!!)
                    nodes[name]!!.parent = nodes[root]
                }
            }
        }
        return nodes.filter { it.value.parent == null }.values.first().name
    }

    data class Node(val name: String, var weight: Int, val nodes: MutableList<Node>) {
        var parent: Node? = null
    }

    @Test
    fun sample() {
        assertEquals("tknk", nameOfRoot("pbga (66)\n" +
                "xhth (57)\n" +
                "ebii (61)\n" +
                "havc (66)\n" +
                "ktlj (57)\n" +
                "fwft (72) -> ktlj, cntj, xhth\n" +
                "qoyq (66)\n" +
                "padx (45) -> pbga, havc, qoyq\n" +
                "tknk (41) -> ugml, padx, fwft\n" +
                "jptl (61)\n" +
                "ugml (68) -> gyxo, ebii, jptl\n" +
                "gyxo (61)\n" +
                "cntj (57)"))
    }

    @Test
    fun part1() {
        assertEquals("fbgguv", nameOfRoot(File("files/2017/day7.txt").readText()))
    }
}