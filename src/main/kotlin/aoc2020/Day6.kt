package aoc2020

import micros
import java.io.File

fun main() {
    runDay6()
}

fun runDay6() {
    val content = File("files/2020/day6.txt").readText()

    println("day6part1=${micros { day6part1(content) }}")
    println("day6part2=${micros { day6part2(content) }}")
}

fun day6part1(content: String): Long {
    return content.split("\n\n")
        .map {
            it.chars()
                .filter { c -> c != 10 }
                .distinct()
                .count()
        }.reduce { acc, l -> acc + l }
}

fun day6part2(content: String): Long {
    return content.split("\n\n")
        .map { getCommonChars(it) }
        .reduce { acc, l -> acc + l }
}

fun getCommonChars(paragraph: String): Long {
    val lines = paragraph.split("\n")
    var count = 0L
    for (i in lines[0].indices) {
        val c = lines[0][i]
        var contains = true
        for (j in 1 until lines.size) {
            if (!lines[j].contains(c)) {
                contains = false
                break
            }
        }
        if (contains) {
            count++
        }
    }
    return count
}