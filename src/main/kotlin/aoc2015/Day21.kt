package aoc2015

import java.util.function.Consumer

/**
Boss:
Hit Points: 100
Damage: 8
Armor: 2

Weapons:    Cost  Damage  Armor
Dagger        8     4       0
Shortsword   10     5       0
Warhammer    25     6       0
Longsword    40     7       0
Greataxe     74     8       0

Armor:      Cost  Damage  Armor
Leather      13     0       1
Chainmail    31     0       2
Splintmail   53     0       3
Bandedmail   75     0       4
Platemail   102     0       5

Rings:      Cost  Damage  Armor
Damage +1    25     1       0
Damage +2    50     2       0
Damage +3   100     3       0
Defense +1   20     0       1
Defense +2   40     0       2
Defense +3   80     0       3
 */

fun main() {
    Day21().also { println(it.part1()) }.also { println(it.part2()) }
}

class Day21 {

    private val weapons = arrayOf(
        Item("Dagger", 8, 4, 0),
        Item("Shortsword", 10, 5, 0),
        Item("Warhammer", 25, 6, 0),
        Item("Longsword", 40, 7, 0),
        Item("Greataxe", 74, 8, 0)
    )
    private val armors = arrayOf(
        Item("no armor", 0, 0, 0),
        Item("Leather", 13, 0, 1),
        Item("Chainmail", 31, 0, 2),
        Item("Splintmail", 53, 0, 3),
        Item("Bandedmail", 75, 0, 4),
        Item("Platemail", 102, 0, 5)
    )
    private val rings = arrayOf(
        Item("no ring 1", 0, 0, 0),
        Item("no ring 2", 0, 0, 0),
        Item("Damage +1", 25, 1, 0),
        Item("Damage +2", 50, 2, 0),
        Item("Damage +3", 100, 3, 0),
        Item("Defense +1", 20, 0, 1),
        Item("Defense +2", 40, 0, 2),
        Item("Defense +3", 80, 0, 3)
    )
    private val boss = Item("Boss", 0, 8, 2)

    fun part2(): Int {
        var maxCost = Int.MIN_VALUE
        equipCombinations { equip ->
            startGame(equip).apply {
                if (this.first.hp < 0) {
                    maxCost = maxOf(maxCost, this.first.cost())
                }
            }
        }
        return maxCost
    }

    fun part1(): Int {
        var minCost = Int.MAX_VALUE
        equipCombinations { equip ->
            startGame(equip).apply {
                if (this.first.hp > 0) {
                    minCost = minOf(minCost, this.first.cost())
                }            }
        }
        return minCost
    }

    private fun startGame(equip: IntArray): Pair<Player, Player> {
        val player = Player()
            .also {
                it.items.add(weapons[equip[0]])
                it.items.add(armors[equip[1]])
                it.items.add(rings[equip[2]])
                it.items.add(rings[equip[3]])
            }
        val boss = Player().also {
            it.items.add(boss)
            it.isPlayer = false
        }
        playGame(player, boss)
        return Pair(player, boss)
    }

    private fun equipCombinations(consumer: Consumer<IntArray>) {
        for (w in weapons.indices) {
            for (a in armors.indices) {
                for (r1 in rings.indices) {
                    for (r2 in rings.indices) {
                        if (rings[r1] != rings[r2]) {
                            consumer.accept(intArrayOf(w, a, r1, r2))
                        }
                    }
                }
            }
        }
    }

    private fun playGame(player: Player, boss: Player): Player {
        var curAttk = player
        var curDef = boss
        while (true) {
            curDef.hp -= maxOf(1, curAttk.dmg() - curDef.def())
            when {
                curDef.hp <= 0 -> return curAttk
                else -> {
                    val tmp = curAttk
                    curAttk = curDef
                    curDef = tmp
                }
            }
        }
    }

    class Player {
        var isPlayer = true
        var hp = 100
        val items = HashSet<Item>()

        fun dmg(): Int = items.sumOf { it.dps }
        fun def(): Int = items.sumOf { it.armor }
        fun cost(): Int = items.sumOf { it.cost }
    }

    data class Item(val name: String, val cost: Int, val dps: Int, val armor: Int)
}