package aoc2016

import java.io.File

fun main() {
    Day10().apply { println(part1(File("files/2016/day10.txt").readText(), listOf(17, 61))) }
}

class Day10 {
    fun part1(input: String, containsNums: List<Int>): Int {
        val instructions = input.lines()
            .filter { it.startsWith("bot") }
            .map { Instruction(it) }
            .map { it.fromId to it }.toMap()
        val bots = initBots(input.lines().filter { it.startsWith("value") })
        val outputs = HashMap<Int, ArrayList<Int>>()

        while (bots.count { it.value.size == 2 } != 0) {
            bots.filter { it.value.size == 2 }
                .forEach { (k, v) ->
                    if (v.containsAll(containsNums)) return k
                    v.sort()
                    instructions[k]!!.also {
                        when {
                            it.lowIsOut -> outputs.computeIfAbsent(it.lowToId) { ArrayList() }
                                .apply { this += v.removeFirst() }
                            else -> bots.computeIfAbsent(it.lowToId) { ArrayList() }
                                .apply { this += v.removeFirst() }
                        }
                        when {
                            it.highIsOut -> outputs.computeIfAbsent(it.highToId) { ArrayList() }
                                .apply { this += v.removeLast() }
                            else -> bots.computeIfAbsent(it.highToId) { ArrayList() }
                                .apply { this += v.removeLast() }
                        }
                    }
                }
        }
        return -1
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

    private fun initBots(initLines: List<String>) =
        HashMap<Int, ArrayList<Int>>().apply {
            initLines.forEach {
                val parts = it.split(" ")
                val botId = parts.last().toInt()
                this.computeIfAbsent(botId) { ArrayList() }
                this[botId]!! += parts[1].toInt()
            }

        }
}