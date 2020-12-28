package aoc2015

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
    Day21().also { println(it.part1()) }
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

    fun part1(): Int {
        var minCost = Int.MAX_VALUE
        for (w in weapons.indices) {
            for (a in armors.indices) {
                for (r1 in rings.indices) {
                    for (r2 in rings.indices) {
                        if (rings[r1] != rings[r2]) {
                            findWinner(player = Player()
                                .also {
                                    it.items.add(weapons[w])
                                    it.items.add(armors[a])
                                    it.items.add(rings[r1])
                                    it.items.add(rings[r2])
                                }, boss = Player().also {
                                it.items.add(boss)
                                it.isPlayer = false
                            }).apply {
                                if (isPlayer) {
                                    minCost = minOf(minCost, cost())
                                }
                            }
                        }
                    }
                }
            }
        }
        return minCost
    }

    private fun findWinner(player: Player, boss: Player): Player {
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

        fun dmg(): Int = items.map { it.dps }.sum()
        fun def(): Int = items.map { it.armor }.sum()
        fun cost(): Int = items.map { it.cost }.sum()
    }

    data class Item(val name: String, val cost: Int, val dps: Int, val armor: Int)
}