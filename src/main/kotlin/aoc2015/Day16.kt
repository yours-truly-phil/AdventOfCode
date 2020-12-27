package aoc2015

import java.io.File

fun main() {
    Day16().also { println(it.part1(File("files/2015/day16.txt").readText())) }
}

class Day16 {
    fun part1(input: String): Sue {
        val present = hashMapOf(
            "children" to 3,
            "cats" to 7, "samoyeds" to 2, "pomeranians" to 3, "akitas" to 0, "vizslas" to 0,
            "goldfish" to 5, "trees" to 3, "cars" to 2, "perfumes" to 1
        )
        return input.lines().map { Sue(it) }.first { it.attributes.all { a -> present[a.key] == a.value } }
    }

    class Sue(input: String) {
        val id: Int
        val attributes: Map<String, Int>

        init {
            // Sue 7: pomeranians: 5, samoyeds: 0, perfumes: 10
            input.substring(input.indexOf(" ") + 1, input.indexOf(":"))
                .toInt().also { id = it }
            input.substring(input.indexOf(":") + 2)
                .split(", ").map {
                    val attribute = it.split(": ")
                    attribute[0] to attribute[1].toInt()
                }
                .toMap().also { attributes = it }
        }

        override fun toString(): String {
            return "Sue(id=$id, attributes=$attributes)"
        }
    }
}