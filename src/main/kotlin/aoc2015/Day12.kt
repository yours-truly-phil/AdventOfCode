package aoc2015

import java.io.File

fun main() {
    Day12().also { println(it.part1(File("files/2015/day12.txt").readText())) }
}

class Day12 {
    fun part1(input: String): Int = Regex("[-]?\\d+")
        .findAll(input)
        .map { it.value.toInt() }
        .sum()
}