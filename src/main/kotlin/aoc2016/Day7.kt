package aoc2016

import java.io.File

fun main() {
    Day7().apply { println(part1(File("files/2016/day7.txt").readText())) }
}

class Day7 {
    fun part1(input: String): Int {
        return input.lines().filter { supportsTls(it) }.count()
    }

    private fun supportsTls(line: String): Boolean {
        var inBrackets = 0
        var palOutSide = false
        for (i in 0 until line.length - 3) {
            when {
                line[i] == '[' -> inBrackets++
                line[i] == ']' -> inBrackets--
            }
            if (inBrackets == 0) {
                if (line[i] == line[i + 3] && line[i + 1] == line[i + 2] && line[i] != line[i + 1])
                    palOutSide = true
            } else {
                if (line[i] == line[i + 3] && line[i + 1] == line[i + 2] && line[i] != line[i + 1])
                    return false
            }
        }
        return palOutSide
    }
}