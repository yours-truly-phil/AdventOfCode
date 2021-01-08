package aoc2016

import java.io.File

fun main() {
    Day15().apply {
        println("sample=${
            part1("Disc #1 has 5 positions; at time=0, it is at position 4.\n" +
                    "Disc #2 has 2 positions; at time=0, it is at position 1.")
        }")
        println("part1=${part1(File("files/2016/day15.txt").readText())}")
    }
}

class Day15 {
    fun part1(input: String): Int {
        val discs = input.lines().map { Disc(it) }
            .toTypedArray()
        discs.forEach {
            it.pos += it.no
            it.pos %= it.size
        }
        var t = 0
        while (discs.any { it.pos != 0 }) {
            discs.forEach { it.step() }
            t++
        }
        return t
    }

    class Disc(input: String) {
        val no: Int
        val size: Int
        var pos: Int

        init {
            input.split(" ").also {
                no = it[1].substring(1).toInt()
                size = it[3].toInt()
                pos = it.last().substring(0, it.last().length - 1).toInt()
            }
        }

        fun step() {
            pos++
            pos %= size
        }
    }
}