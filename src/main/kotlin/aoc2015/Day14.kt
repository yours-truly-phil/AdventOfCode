package aoc2015

import java.io.File

fun main() {
    Day14().also { println(it.part1(File("files/2015/day14.txt").readText())) }
}

class Day14 {
    fun part1(input: String): Long {
        val reindeer = input.lines().map { Reindeer(it) }
        repeat(2503) {
            reindeer.forEach { it.step() }
        }
        var max = Long.MIN_VALUE
        reindeer.forEach { max = maxOf(max, it.dist) }
        return max
    }

    class Reindeer(input: String) {
        val name: String
        val flyVel: Int
        val flyDur: Int
        val restDur: Int
        var resting = false
        var flyTime = 0
        var restTime = 0
        var dist = 0L

        init {
            input.split(" ")
                .also { name = it[0] }
                .also { flyVel = it[3].toInt() }
                .also { flyDur = it[6].toInt() }
                .also { restDur = it[13].toInt() }
        }

        fun step() {
            if (!resting) {
                dist += flyVel
                flyTime++
                if (flyTime == flyDur) {
                    resting = true
                    flyTime = 0
                }
            } else {
                restTime++
                if (restTime == restDur) {
                    resting = false
                    restTime = 0
                }
            }
        }
    }

}