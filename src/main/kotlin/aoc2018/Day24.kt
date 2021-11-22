package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day24 {
    fun numUnitsWinningArmy(input: String): Int {
        val parts = input.split("\n\n")
        val immuneSystem = parts[0].lines().subList(1, parts[0].lines().size)
        val infection = parts[1].lines().subList(1, parts[1].lines().size)

        immuneSystem.map {

        }
        immuneSystem.forEach { println(it) }
        infection.forEach { println(it) }
        return 1
    }

    class Group(
        val units: Int,
        val hp: Int,
        val dmg: Int,
        val dmtType: String,
        val initiative: Int,
        val weaknesses: List<String>,
        val immunities: List<String>
    ) {
        override fun toString(): String =
            "count=$units hp=$hp dmg=$dmg type=$dmtType init=$initiative " +
                    "weak=${weaknesses.joinToString(",", "[", "]")} " +
                    "immunities=${immunities.joinToString(",", "[", "]")}"
    }

    @Test
    fun sample() {
        assertEquals(
            -1, numUnitsWinningArmy(
                "Immune System:\n" +
                        "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2\n" +
                        "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3\n" +
                        "\n" +
                        "Infection:\n" +
                        "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1\n" +
                        "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
            )
        )
    }

    @Test
    fun part1() {
        assertEquals(-1, numUnitsWinningArmy(File("files/2018/day24.txt").readText()))
    }
}