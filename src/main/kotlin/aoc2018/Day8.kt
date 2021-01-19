package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day8 {
    private fun sumMetaData(input: String): Int {
        val nums = input.split(" ").map { it.toInt() }.toIntArray()
        val master = Node()
        parseNodes(0, nums, master)
        val root = master.children.first()
        return sumMeta(root)
    }

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
        override fun toString(): String {
            return "N(c=$children, m=$meta)"
        }
    }

    @Test
    fun sample() {
        assertEquals(138, sumMetaData("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"))
    }

    @Test
    fun part1() {
        assertEquals(38780, sumMetaData(File("files/2018/day8.txt").readText()))
    }
}