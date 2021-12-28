package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day24 {
    private fun solvePart1(input: String): Int {
        var (immuneSystem, infection) = parse(input)

        val pair = run(immuneSystem, infection)
        immuneSystem = pair.first
        infection = pair.second

        return if (immuneSystem.any(Group::isInCombat)) {
            immuneSystem.sumOf { it.numUnits }
        } else {
            infection.sumOf { it.numUnits }
        }
    }

    private fun run(
        immuneSystem: List<Group>,
        infection: List<Group>
    ): Pair<List<Group>, List<Group>> {
        var immuneSystem1 = immuneSystem
        var infection1 = infection
        var noDraw = true
        while (noDraw && immuneSystem1.any(Group::isInCombat) && infection1.any(Group::isInCombat)) {
            immuneSystem1 = immuneSystem1.filter { it.isInCombat() }
            infection1 = infection1.filter { it.isInCombat() }
//            println("Immune System:\n${immuneSystem1.joinToString("\n") { "${it.id} contains ${it.numUnits} units" }}")
//            println("Infection:\n${infection1.joinToString("\n") { "${it.id} contains ${it.numUnits} units" }}")

            val sortedImmuneSystem = sortForTargetSelection(immuneSystem1)
            val sortedInfection = sortForTargetSelection(infection1)

            val targetsInfection = ArrayList(infection1)
            val targetsImmuneSystem = ArrayList(immuneSystem1)

            for (immuneGroup in sortedImmuneSystem) {
                immuneGroup.setTarget(targetsInfection)
            }
            for (infectionGroup in sortedInfection) {
                infectionGroup.setTarget(targetsImmuneSystem)
            }

            val sortedAttacking = listOf(immuneSystem1, infection1).flatten().filter { it.target != null }
                .sortedByDescending { it.initiative }
            noDraw = false
            for (group in sortedAttacking) {
                val numKilled = group.attack()
//                println("${group.id} attacks defending group ${group.target?.id} killing $numKilled units")
                noDraw = (numKilled > 0) || noDraw
            }
//            println()
        }
        return Pair(immuneSystem1, infection1)
    }

    private fun solvePart2(input: String): Int {
        var max = 10000
        var min = 0
        var curMinRemain = Int.MAX_VALUE
        var boost: Int
        while (max - min > 1) {
            boost = (max + min + 1) / 2
            var (immuneSystem, infection) = parse(input, boost)

            val pair = run(immuneSystem, infection)
            immuneSystem = pair.first
            infection = pair.second
            if (immuneSystem.any { it.isInCombat() } && !infection.any { it.isInCombat() }) {
                // too much boost
                curMinRemain = minOf(curMinRemain, immuneSystem.sumOf { it.numUnits })
                max = boost
            } else {
                // not enough boost
                min = boost
            }
        }
        return curMinRemain
    }

    private fun parse(
        input: String,
        boost: Int = 0
    ) = input.split("\n\n").mapIndexed { t, system ->
        system.lines().drop(1).mapIndexed { i, line ->
            val name = if (t == 0) "Immune ${i + 1}" else "Infection ${i + 1}"
            parseGroup(name, line, if (t == 0) boost else 0)
        }
    }

    private fun sortForTargetSelection(groups: List<Group>) =
        groups.groupBy { it.effectivePower() }.toSortedMap { o1, o2 -> o2.compareTo(o1) }
            .mapValues { it.value.sortedByDescending(Group::initiative) }.map { it.value }.flatten()

    @Test
    fun sortForTargetSelectionTest() {
        val groups = listOf(
            Group("1", 1, 1, 1, "", 1, listOf(), listOf()),
            Group("2", 1, 1, 1, "", 2, listOf(), listOf()),
            Group("3", 1, 1, 1, "", 3, listOf(), listOf()),
            Group("4", 1, 1, 2, "", 1, listOf(), listOf()),
            Group("5", 1, 1, 2, "", 2, listOf(), listOf()),
            Group("6", 1, 1, 2, "", 3, listOf(), listOf()),
        )
        val sorted = sortForTargetSelection(groups)
        assertEquals(groups[5], sorted[0])
        assertEquals(groups[4], sorted[1])
        assertEquals(groups[3], sorted[2])
        assertEquals(groups[2], sorted[3])
        assertEquals(groups[1], sorted[4])
        assertEquals(groups[0], sorted[5])
    }

    private fun parseGroup(id: String, line: String, boost: Int = 0): Group {
        val words = line.split(" ")
        val numUnits = words[0].toInt()
        val hp = words[4].toInt()
        val attackDmg = words[words.lastIndex - 5].toInt() + boost
        val attackType = words[words.lastIndex - 4]
        val initiative = words[words.lastIndex].toInt()
        val immunities = ArrayList<String>()
        val weaknesses = ArrayList<String>()
        if (line.contains("(")) {
            val extras = line.substring(line.indexOf("(") + 1, line.indexOf(")")).split("; ").map { it.split(", ") }
            for (extra in extras) {
                if (extra[0].startsWith("immune")) {
                    immunities.add(extra[0].substring(10))
                    immunities.addAll(extra.drop(1))
                } else {
                    weaknesses.add(extra[0].substring(8))
                    weaknesses.addAll(extra.drop(1))
                }
            }
        }
        return Group(id, numUnits, hp, attackDmg, attackType, initiative, immunities, weaknesses)
    }

    data class Group(
        val id: String,
        var numUnits: Int,
        val hp: Int,
        val attackDmg: Int,
        val attackType: String,
        val initiative: Int,
        val immunities: List<String>,
        val weaknesses: List<String>
    ) {
        var target: Group? = null

        fun effectivePower() = numUnits * attackDmg
        fun isInCombat() = numUnits > 0

        fun setTarget(possibleTargets: MutableList<Group>) {
            if (possibleTargets.isNotEmpty()) {
                val byPotentialDmg = possibleTargets.groupBy { potentialDamage(it) }.maxByOrNull { it.key }!!.value
                val byEffectivePower = byPotentialDmg.groupBy { it.effectivePower() }.maxByOrNull { it.key }!!.value
                val potentialTarget = byEffectivePower.sortedByDescending { it.initiative }[0]
                if (potentialDamage(potentialTarget) > 0) {
                    target = potentialTarget
                    possibleTargets.remove(target)
                } else {
                    target = null
                }
            } else {
                target = null
            }
        }

        fun attack(): Int {
            if (target != null) {
                val damage = potentialDamage(target!!)
                if (damage == 0 || target!!.numUnits <= 0) {
                    return 0
                }
                val killed = minOf(target!!.numUnits, damage / target!!.hp)
                target!!.numUnits -= killed
                return killed
            } else {
                return 0
            }
        }

        private fun potentialDamage(target: Group): Int {
            if (target.immunities.contains(attackType)) {
                return 0
            }
            val damage = effectivePower()
            return if (target.weaknesses.contains(attackType)) {
                2 * damage
            } else {
                damage
            }
        }
    }

    @Test
    fun setTargetTest() {
        val g = Group("attacker", 1, 1, 1, "a", 1, listOf(), listOf())
        val target = Group("target", 1, 1, 2, "", 2, listOf(), listOf("a"))
        val targets = mutableListOf(
            Group("1", 1, 1, 1, "", 1, listOf(), listOf("a")),
            Group("2", 1, 1, 1, "", 2, listOf(), listOf("a")),
            Group("3", 1, 1, 2, "", 1, listOf(), listOf("a")),
            target,
            Group("5", 1, 1, 1, "", 3, listOf("a"), listOf()),
            Group("6", 1, 1, 1, "", 1, listOf(), listOf()),
            Group("7", 1, 1, 1, "", 2, listOf(), listOf()),
            Group("8", 1, 1, 1, "", 3, listOf(), listOf()),
        )
        g.setTarget(targets)
        assertEquals(g.target, target)
        assertTrue(!targets.contains(target))
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
        assertEquals(21127, solvePart1(File("files/2018/day24.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(
            51, solvePart1(
                """
Immune System:
17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 6077 fire damage at initiative 2
989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 1595 slashing damage at initiative 3

Infection:
801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4""".trimIndent()
            )
        )
        assertEquals(
            51, solvePart2(
                """
Immune System:
17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3

Infection:
801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4""".trimIndent()
            )
        )
        assertEquals(2456, solvePart2(File("files/2018/day24.txt").readText()))
    }
}