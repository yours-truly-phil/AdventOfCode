package aoc2015

import java.io.File

fun main() {
    Day19().also { println(it.part1(File("files/2015/day19.txt").readText())) }
        .also { println(it.part2(File("files/2015/day19.txt").readText())) }
}

class Day19 {
    fun part1(input: String): Int {
        val parts = input.split("\n\n")
        val molecule = parts[1]
        val transformations = parts[0].lines()
            .map { it.split(" => ") }
            .map { it[0].toRegex() to it[1] }

        val result = HashSet<String>()
        transformations.map {
            it.first.findAll(molecule).mapTo(result) { match ->
                StringBuilder().apply {
                    this.append(molecule.substring(0, match.range.first))
                    this.append(it.second)
                    this.append(molecule.substring(match.range.last + 1))
                }.toString()
            }
        }
        return result.size
    }

    fun part2(input: String): Int {
        val parts = input.split("\n\n")
        var molecule = parts[1]
        val transformations = parts[0].lines()
            .map { it.split(" => ") }
            .map { it[0] to it[1].toRegex() }
            .sortedByDescending { pair -> pair.second.pattern.length }

        var count = 0
        while (molecule != "e") {
            transformations.forEach {
                if (it.second.containsMatchIn(molecule)) {
                    val match = it.second.find(molecule)!!
                    StringBuilder().apply {
                        this.append(molecule.substring(0, match.range.first))
                        this.append(it.first)
                        this.append(molecule.substring(match.range.last + 1))
                    }.toString().apply { molecule = this }
                    count++
                }
            }
        }
        return count
    }
}