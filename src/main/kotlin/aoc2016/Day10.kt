package aoc2016

import java.io.File

fun main() {
    Day10().apply { println(part1(File("files/2016/day10.txt").readText(), listOf(17, 61))) }
        .apply { println(part2(File("files/2016/day10.txt").readText())) }
}

class Day10 {
    fun part1(input: String, containsNums: List<Int>): Int {
        val (instructions, bots, outputs) = parseInput(input)

        while (bots.count { it.value.size == 2 } != 0) {
            bots.filter { it.value.size == 2 }.forEach { (k, v) ->
                    if (v.containsAll(containsNums)) return k
                    foo(v, instructions, k, outputs, bots)
                }
        }
        return -1
    }

    private fun parseInput(input: String): Triple<Map<Int, Instruction>, HashMap<Int, ArrayList<Int>>, HashMap<Int, ArrayList<Int>>> {
        val instructions =
            input.lines().filter { it.startsWith("bot") }.map { Instruction(it) }.associateBy { it.fromId }
        val bots = initBots(input.lines().filter { it.startsWith("value") })
        val outputs = HashMap<Int, ArrayList<Int>>()
        return Triple(instructions, bots, outputs)
    }

    fun part2(input: String): Int {
        val (instructions, bots, outputs) = parseInput(input)

        while (bots.count { it.value.size == 2 } != 0) {
            bots.filter { it.value.size == 2 }.forEach { (k, v) ->
                    foo(v, instructions, k, outputs, bots)
                }
        }
        return outputs[0]!![0] * outputs[1]!![0] * outputs[2]!![0]
    }

    private fun foo(
        v: java.util.ArrayList<Int>,
        instructions: Map<Int, Instruction>,
        k: Int,
        outputs: HashMap<Int, ArrayList<Int>>,
        bots: HashMap<Int, ArrayList<Int>>
    ) {
        v.sort()
        instructions[k]!!.also {
            when {
                it.lowIsOut -> outputs.computeIfAbsent(it.lowToId) { ArrayList() }.apply { this += v.removeFirst() }
                else -> bots.computeIfAbsent(it.lowToId) { ArrayList() }.apply { this += v.removeFirst() }
            }
            when {
                it.highIsOut -> outputs.computeIfAbsent(it.highToId) { ArrayList() }.apply { this += v.removeLast() }
                else -> bots.computeIfAbsent(it.highToId) { ArrayList() }.apply { this += v.removeLast() }
            }
        }
    }

    class Instruction(val input: String) {
        val fromId: Int
        val lowToId: Int
        val lowIsOut: Boolean
        val highToId: Int
        val highIsOut: Boolean

        init {
            val parts = input.split(" ")
            fromId = parts[1].toInt()
            lowToId = parts[6].toInt()
            lowIsOut = parts[5] == "output"
            highToId = parts.last().toInt()
            highIsOut = parts[10] == "output"
        }
    }

    private fun initBots(initLines: List<String>) = HashMap<Int, ArrayList<Int>>().apply {
        initLines.forEach {
            val parts = it.split(" ")
            val botId = parts.last().toInt()
            this.computeIfAbsent(botId) { ArrayList() }
            this[botId]!! += parts[1].toInt()
        }

    }
}