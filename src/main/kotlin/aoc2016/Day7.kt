package aoc2016

import java.io.File

fun main() {
    Day7().apply { println(part1(File("files/2016/day7.txt").readText())) }
        .apply { println(part2(File("files/2016/day7.txt").readText())) }
}

class Day7 {
    fun part1(input: String): Int {
        return input.lines().filter { supportsTls(it) }.count()
    }

    fun part2(input: String): Int {
        return input.lines().filter { supportSsl(it) }.count()
    }

    private fun supportSsl(line: String): Boolean {
        var inBrackets = 0
        val abasOutside = ArrayList<String>()
        val babsInside = ArrayList<String>()
        for (i in 0 until line.length - 2) {
            when {
                line[i] == '[' -> inBrackets++
                line[i] == ']' -> inBrackets--
            }
            if (inBrackets == 0) {
                if (line[i] == line[i + 2] && line[i] != line[i + 1]) {
                    abasOutside.add(line[i].toString() + line[i + 1].toString())
                }
            } else {
                if (line[i] == line[i + 2] && line[i] != line[i + 1]) {
                    babsInside.add(line[i].toString() + line[i + 1].toString())
                }
            }
        }
        abasOutside.forEach {
            if (babsInside.contains(it.reversed())) return true
        }
        return false
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