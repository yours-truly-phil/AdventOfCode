package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day7 {
    fun instructionOrder(input: String): String {

        val nodes = HashMap<String, Node>()
        input.lines().forEach {
            val s = it.split(" ")
            val step = s[1]
            val nodeAfter = s[7]
            nodes.computeIfAbsent(step) { Node(step) }
            nodes.computeIfAbsent(nodeAfter) { Node(nodeAfter) }
        }
        input.lines().forEach {
            val s = it.split(" ")
            val step = s[1]
            val comesAfter = s[7]
            nodes[step]!!.allAfter.add(nodes[comesAfter]!!)
            nodes[step]!!.after.add(nodes[comesAfter]!!)
        }

        nodes.forEach {
            it.value.allAfter.forEach { after -> after.allBefore.add(it.value) }
            it.value.after.forEach { after -> after.before.add(it.value) }
        }

        nodes.values.forEach {
            it.allBefore.addAll(allPre(it))
            it.allAfter.addAll(allAfter(it))
        }

        val first = nodes.values.minByOrNull { it.allBefore.size }!!
        val last = nodes.values.minByOrNull { it.allAfter.size }!!

        val order = ArrayDeque<Node>()
        order.addFirst(first)
        order.addLast(last)

        val unsetNodes = nodes.values.toMutableSet()
        unsetNodes.removeAll { it.id == first.id || it.id == last.id }
        for (new in unsetNodes) {
            for (i in 0 until order.size) {
                if (!order.subList(i, order.size - 1).any { new.allBefore.contains(it) }) {
                    order.add(i, new)
                    break
                }
            }
        }

        println("found sorted order: ${isValidOrder(order)}")
        println("order: ${order.joinToString(", ") { it.id }}")
        var sorted = false
        while (!sorted) {
            sorted = true
            for (i in 0 until order.size - 1) {
                if (order[i].id > order[i + 1].id) {
                    swap(order, i)
                    if (!isValidOrder(order)) {
                        swap(order, i)
                    } else {
                        sorted = false
                        println("new order: ${order.joinToString(", ") { it.id }}")
                    }
                }
            }
        }

        println("first: $first")
        println("last: $last")
        nodes.forEach {
            println(it)
        }

        println()
        println("order: ${order.joinToString(", ") { it.id }}")
        return order.joinToString("") { it.id }
    }

    private fun swap(order: ArrayDeque<Node>, i: Int) {
        val tmp = order[i]
        order[i] = order[i + 1]
        order[i + 1] = tmp
    }

    private fun isValidOrder(order: ArrayDeque<Node>): Boolean {
        for (i in order.indices) {
            if (order[i].allBefore.any { order.subList(i, order.size).contains(it) }) {
                return false
            }
        }
        return true
    }

    private fun allAfter(node: Node): Collection<Node> {
        val res = ArrayList<Node>()
        res.addAll(node.allAfter)
        for (after in node.allAfter) {
            res.addAll(allAfter(after))
        }
        return res
    }

    private fun allPre(node: Node): Collection<Node> {
        val res = ArrayList<Node>()
        res.addAll(node.allBefore)
        for (pre in node.allBefore) {
            res.addAll(allPre(pre))
        }
        return res
    }

    class Node(val id: String) {
        val before = HashSet<Node>()
        val after = HashSet<Node>()
        val allBefore = HashSet<Node>()
        val allAfter = HashSet<Node>()

        override fun toString(): String {
            return "[${before.joinToString(",") { it.id }}] < $id < [${after.joinToString(",") { it.id }}]"
        }
    }

    @Test
    fun sample() {
        assertEquals("CABDFE", instructionOrder("Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin."))
    }

    @Test
    fun part1() {
        assertEquals("IBJTUWGFKDNVEYAHOMPCQRLSZX", instructionOrder(File("files/2018/day7.txt").readText()))
    }
}