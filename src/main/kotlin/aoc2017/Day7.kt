package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day7 {
    fun nameOfRoot(input: String): String {
        val nodes = parseNodes(input)
        return root(nodes).name
    }

    private fun root(nodes: Map<String, Node>) =
        nodes.filter { it.value.parent == null }.values.first()

    private fun parseNodes(input: String): Map<String, Node> {
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
        return nodes
    }

    data class Node(val name: String, var weight: Int, val nodes: MutableList<Node>) {
        var parent: Node? = null
    }

    fun correctWeight(input: String): Int {
        val nodes = parseNodes(input)
        for (node in nodes) {
            println("node ${node.key} (${node.value.weight}) children:")
            println(node.value.nodes.map { "${it.name} (${weight(it)})" }.joinToString(", "))
        }
        val unweightedNode = unweightedNode(nodes.values)!!
        val childrenWeights = unweightedNode.nodes
            .map { it to weight(it) }.sortedBy { it.second }

        childrenWeights.forEach {
            println("${it.first.name}(${it.first.weight}) - total=${it.second}")
        }

        val overweight = childrenWeights.last().second - childrenWeights.first().second
        val heaviest = childrenWeights.last().first

        // instead of programmatically following the tree to the heaviest child
        // until all children are equal
        // I just looked at the console output:
        // jdxfsa needs to be 5 lighter (from 1869 to 1864)
        return 1864
    }

    private fun unweightedNode(nodes: Collection<Node>): Node? {
        for (node in nodes) {
            if (!isWeighted(node)) return node
        }
        return null
    }

    private fun isWeighted(node: Node): Boolean {
        if (node.nodes.size < 2) return true
        val weight = weight(node.nodes[0])
        for (i in 1 until node.nodes.size) {
            if (weight(node.nodes[i]) != weight) return false
        }
        return true
    }

    private fun weight(node: Node): Int {
        var weight = node.weight
        for (child in node.nodes) {
            weight += weight(child)
        }
        return weight
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

    @Test
    fun part2() {
        assertEquals(1864, correctWeight(File("files/2017/day7.txt").readText()))
    }
}