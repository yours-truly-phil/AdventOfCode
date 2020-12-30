package aoc2016

import java.io.File

fun main() {
    Day6().apply { println(part1(File("files/2016/day6.txt").readText())) }
}

class Day6 {
    fun part1(input: String): String {
        val freq = HashMap<Int, HashMap<Char, Int>>()
        input.lines().map { it.toCharArray() }
            .forEach {
                for (i in it.indices) {
                    freq.computeIfAbsent(i) { HashMap() }
                    freq[i]!!.computeIfAbsent(it[i]) { 0 }
                    freq[i]?.set(it[i], freq[i]!![it[i]]!! + 1)
                }
            }
        var pw = ""
        for(col in freq) {
            var max = 'a'
            var curMax = 0
            for(entry in col.value) {
                if(entry.value > curMax) {
                    max = entry.key
                    curMax = entry.value
                }
            }
            pw += max
        }
        return pw
    }
}