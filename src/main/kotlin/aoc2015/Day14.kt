package aoc2015

import java.io.File

fun main() {
    Day14().also { println(it.part1(File("files/2015/day14.txt").readText())) }
        .also { println(it.part2(File("files/2015/day14.txt").readText())) }
}

class Day14 {
    fun part1(input: String): Long {
        val reindeer = parse(input)
        repeat(2503) {
            reindeer.forEach { it.step() }
        }
        return winnerDistance(reindeer)
    }

    private fun winnerDistance(reindeer: List<Reindeer>): Long {
        var max = Long.MIN_VALUE
        reindeer.forEach { max = maxOf(max, it.dist) }
        return max
    }

    fun part2(input: String): Long {
        val reindeer = parse(input)
        repeat(2503) {
            var leadDist = 0L
            reindeer.forEach {
                it.step()
                leadDist = maxOf(it.dist, leadDist)
            }

            reindeer.filter { it.dist == leadDist }.forEach { it.points++ }
        }
        return winnerPoints(reindeer)
    }

    private fun winnerPoints(reindeer: List<Reindeer>): Long {
        var max = Long.MIN_VALUE
        reindeer.forEach { max = maxOf(max, it.points) }
        return max
    }

    private fun parse(input: String): List<Reindeer> {
        return input.lines().map { Reindeer(it) }
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
        var points = 0L

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

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Reindeer

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

}