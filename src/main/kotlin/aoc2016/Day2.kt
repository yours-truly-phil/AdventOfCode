package aoc2016

import java.io.File

fun main() {
    Day2().apply { println(part1(File("files/2016/day2.txt").readText())) }
}

class Day2 {
    fun part1(input: String): String {
        var code = ""
        input.lines()
            .forEach {
                var key = 5
                it.forEach { dir ->
                    when (dir) {
                        'U' -> if (key > 3) key -= 3
                        'D' -> if (key < 7) key += 3
                        'L' -> if (key % 3 != 1) key--
                        'R' -> if (key % 3 != 0) key++
                    }
                }
                code += key.toString()
            }
        return code
    }
}