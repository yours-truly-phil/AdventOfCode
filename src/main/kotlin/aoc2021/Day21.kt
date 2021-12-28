package aoc2021

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21 {
    @Suppress("SameParameterValue")
    private fun solvePart1(input: String, sides: Int, maxScore: Int): Int {
        val players = input.lines().map { it.split(": ")[1].toInt() }
            .map { Player(0, it) }
        val die = DeterministicDie(sides)
        for (round in 1..Int.MAX_VALUE) {
            players.forEachIndexed { _, player ->
                val rollSum = (0 until 3).sumOf { die.roll() }
                player.move(rollSum)
                if (player.score >= maxScore) {
                    return players.minOf { it.score } * die.rolls
                }
            }
        }
        throw IllegalStateException("Should not reach here")
    }

    class DeterministicDie(private val sides: Int) {
        var last = sides - 1
        var rolls = 0
        fun roll(): Int {
            rolls++
            last = (last + 1) % sides
            return last + 1
        }
    }

    private fun solvePart2(input: String): Long {
        val players = input.lines().map { it.split(": ")[1].toInt() }.map { Player(0, it) }
        val initial = Universe(players[0], players[1], 1L, true)
        val universes = hashMapOf(initial.toString() to initial)
        var countUniversesP1Wins = 0L
        var countUniversesP2Wins = 0L
        while (universes.isNotEmpty()) {

            val nextUniverses = ArrayList<Universe>()

            universes.values.forEach { universe ->
                val newUniverses = makeMove(universe)
                for (newUniverse in newUniverses) {
                    if (newUniverse.p1.score >= 21) {
                        countUniversesP1Wins += newUniverse.count
                    } else if (newUniverse.p2.score >= 21) {
                        countUniversesP2Wins += newUniverse.count
                    } else {
                        nextUniverses.add(newUniverse)
                    }
                }
            }

            universes.clear()

            nextUniverses.forEach { universe ->
                if (universes.containsKey(universe.toString())) {
                    val existing = universes[universe.toString()]!!
                    existing.count += universe.count
                } else {
                    universes[universe.toString()] = universe
                }
            }
        }
        return maxOf(countUniversesP1Wins, countUniversesP2Wins)
    }

    private fun makeMove(universe: Universe): List<Universe> {
        val newUniverses = mutableListOf<Universe>()
        (3..9).forEach { roll ->
            val multi = getNumNewUniverses(roll)
            val newP1 = Player(universe.p1.score, universe.p1.pos)
            val newP2 = Player(universe.p2.score, universe.p2.pos)
            if (universe.p1Turn) {
                newP1.move(roll)
            } else {
                newP2.move(roll)
            }
            newUniverses += Universe(newP1, newP2, universe.count * multi, !universe.p1Turn)
        }
        return newUniverses
    }

    private fun getNumNewUniverses(roll: Int): Int = when (roll) {
        3 -> 1
        4 -> 3
        5 -> 6
        6 -> 7
        7 -> 6
        8 -> 3
        9 -> 1
        else -> throw IllegalStateException("Should not reach here")
    }

    class Universe(val p1: Player, val p2: Player, var count: Long, var p1Turn: Boolean) {
        override fun toString(): String {
            return "$p1,$p2,$p1Turn"
        }
    }

    data class Player(var score: Int, var pos: Int) {
        fun move(steps: Int) {
            pos = (pos + steps - 1) % 10 + 1
            score += pos
        }
    }

    @Test
    fun part1() {
        assertEquals(
            739785, solvePart1(
                """
Player 1 starting position: 4
Player 2 starting position: 8""".trimIndent(), 100, 1000
            )
        )
        assertEquals(
            903630, solvePart1(
                """
Player 1 starting position: 4
Player 2 starting position: 9""".trimIndent(), 100, 1000
            )
        )
    }

    @Test
    fun part2() {
        assertEquals(
            444356092776315L, solvePart2(
                """
Player 1 starting position: 4
Player 2 starting position: 8""".trimIndent()
            )
        )
        assertEquals(
            303121579983974L, solvePart2(
                """
Player 1 starting position: 4
Player 2 starting position: 9""".trimIndent()
            )
        )
    }
}