package aoc2016

import java.io.File

fun main() {
    Day4().apply { println(part1(File("files/2016/day4.txt").readText())) }
        .apply { println(part2(File("files/2016/day4.txt").readText())) }
}

class Day4 {
    fun part1(input: String): Long {
        return input.lines().filter { isRealRoom(it) }
            .sumOf { it.substring(it.lastIndexOf("-") + 1, it.indexOf("[")).toLong() }
    }

    fun part2(input: String): Int {
        return input.lines().map {
            val id = it.substring(it.lastIndexOf("-") + 1, it.indexOf("[")).toInt()
            val parts = it.split("-").subList(0, it.split("-").size - 1)
            id to parts.joinToString("")
        }.map {
            it.first to it.second.map { c -> c.code + it.first }.map { i ->
                    var norm = i
                    while (norm > 122) norm -= 26
                    norm
                }.map { i -> i.toChar() }.joinToString("")
        }.first { it.second.contains("northpoleobjects") }.first
    }

    private fun isRealRoom(room: String): Boolean {
        val parts = room.split("-")
        val check = parts.last().substring(parts.last().indexOf("[") + 1, parts.last().indexOf("]"))
        val freq = parts.subList(0, parts.size - 1).joinToString("").toCharArray().groupBy { it }
            .map { it.key to it.value.size }.toMap()

        if (!check.all { freq.containsKey(it) }) return false
        return freq.maxOf { it.value } == freq[check[0]]!! && freq[check[0]]!! >= freq[check[1]]!! && freq[check[1]]!! >= freq[check[2]]!! && freq[check[2]]!! >= freq[check[3]]!! && freq[check[3]]!! >= freq[check[4]]!!
    }
}