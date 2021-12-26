package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day24 {
    private fun solvePart1(input: String): Int {
        val (immuneSystem, infection) = input.split("\n\n")
            .map { it.lines().drop(1).map { line -> parseGroup(line) } }
        while (immuneSystem.any(Group::isInCombat) && infection.any(Group::isInCombat)) {
            throw IllegalStateException("Not yet implemented")
        }
        return 0
    }

    private fun parseGroup(line: String): Group {
        val words = line.split(" ")
        val numUnits = words[0].toInt()
        val hp = words[4].toInt()
        val attackDmg = words[words.lastIndex - 5].toInt()
        val attackType = words[words.lastIndex - 4]
        val initiative = words[words.lastIndex].toInt()
        val extras = line.substring(line.indexOf("(") + 1, line.indexOf(")")).split("; ").map { it.split(", ") }
        val immunities = ArrayList<String>()
        val weaknesses = ArrayList<String>()
        for (extra in extras) {
            if (extra[0].startsWith("immune")) {
                immunities.add(extra[0].substring(10))
                immunities.addAll(extra.drop(1))
            } else {
                weaknesses.add(extra[0].substring(8))
                weaknesses.addAll(extra.drop(1))
            }
        }
        return Group(numUnits, hp, attackDmg, attackType, initiative, immunities, weaknesses)
    }

    class Group(
        val numUnits: Int,
        val hp: Int,
        val attackDmg: Int,
        val attackType: String,
        val initiative: Int,
        val immunities: List<String>,
        val weaknesses: List<String>
    ) {
        fun effectivePower() = numUnits * attackDmg
        fun isInCombat() = numUnits > 0
    }

    @Test
    fun part1() {
        assertEquals(
            5216, solvePart1(
                """
Immune System:
17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3

Infection:
801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4""".trimIndent()
            )
        )
        assertEquals(-1, solvePart1(File("files/2018/day24.txt").readText()))
    }
}