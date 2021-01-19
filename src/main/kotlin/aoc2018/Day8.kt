package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day8 {
    private fun sumMetaData(input: String): Int {
        val root = parseRoot(input)
        return sumMeta(root)
    }

    private fun parseRoot(input: String): Node {
        val nums = input.split(" ").map { it.toInt() }.toIntArray()
        val master = Node()
        parseNodes(0, nums, master)
        return master.children.first()
    }

    private fun valueOfRootNode(input: String): Int = parseRoot(input).nodeValue()

    private fun sumMeta(node: Node): Int {
        return node.meta.sum() + node.children.map { sumMeta(it) }.sum()
    }

    private fun parseNodes(idx: Int, nums: IntArray, parent: Node): Int {
        val node = Node()
        parent.children.add(node)
        var i = idx + 2
        repeat(nums[idx]) {
            i += parseNodes(i, nums, node)
        }
        for (metaIdx in i until i + nums[idx + 1]) {
            node.meta.add(nums[metaIdx])
        }
        return i + nums[idx + 1] - idx
    }

    class Node {
        val children = ArrayList<Node>()
        val meta = ArrayList<Int>()

        fun nodeValue(): Int {
            return if (children.isEmpty()) {
                meta.sum()
            } else {
                var value = 0
                for (i in meta) {
                    val idx = i - 1
                    if (idx in children.indices) {
                        value += children[idx].nodeValue()
                    }
                }
                value
            }
        }

        override fun toString(): String {
            return "N(c=$children, m=$meta)"
        }
    }

    @Test
    fun sample() {
        assertEquals(138, sumMetaData("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(66, valueOfRootNode("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"))
    }

    @Test
    fun part1() {
        assertEquals(38780, sumMetaData(File("files/2018/day8.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(18232, valueOfRootNode(File("files/2018/day8.txt").readText()))
    }
}