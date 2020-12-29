package aoc2015

fun main() {
    Day22().also { println(it.lowestManaForWin(50, 500, 71, 10, false)) }
        .also { println(it.lowestManaForWin(50, 500, 71, 10, true)) }
}

class Day22 {
    private val allSpells = arrayListOf(
        Spell("Magic Missile", 53, 4, 0, 0, 0, 1),
        Spell("Drain", 73, 2, 2, 0, 0, 1),
        Spell("Shield", 113, 0, 0, 7, 0, 6),
        Spell("Poison", 173, 3, 0, 0, 0, 6),
        Spell("Recharge", 229, 0, 0, 0, 101, 5)
    )

    fun lowestManaForWin(playerHp: Int, playerMana: Int, bossHp: Int, bossDmg: Int, part2: Boolean): Int {
        val player = Player().apply { hp = playerHp }.apply { mana = playerMana }
        val boss = Player().apply { hp = bossHp }.apply { dmg = bossDmg }
        return play(part2, true, Int.MAX_VALUE, 0, player, boss, ArrayList())
    }

    private fun play(
        part2: Boolean,
        myTurn: Boolean,
        best: Int,
        spent: Int,
        player: Player,
        boss: Player,
        spells: ArrayList<Spell>
    ): Int {
        if (spent >= best) return best
        if (part2 && myTurn && player.hp == 1) return best

        player.mana += spells.sumBy { it.mana }
        boss.hp -= spells.sumBy { it.dmg }
        player.armor = spells.sumBy { it.armor }
        if (boss.hp <= 0) return spent

        spells.forEach { it.dur-- }
        spells.removeIf { it.dur <= 0 }

        if (myTurn) {
            if (part2) player.hp--
            var newBest = best
            allSpells.filter { it.cost <= player.mana }
                .filterNot { it.name in spells.map { s -> s.name } }
                .forEach {
                    val activeSpells = copySpells(spells)
                    val newPlayer = player.copy()
                    val newBoss = boss.copy()
                    newPlayer.mana -= it.cost
                    val newSpent = spent + it.cost
                    if (it.dur == 1) {
                        newPlayer.hp += it.heal
                        newBoss.hp -= it.dmg
                    } else {
                        activeSpells.add(it.copy())
                    }
                    newBest = if (newBoss.hp <= 0) minOf(newBest, newSpent)
                    else minOf(newBest, play(part2, false, newBest, newSpent, newPlayer, newBoss, activeSpells))
                }
            return newBest
        } else {
            player.hp -= maxOf(1, boss.dmg - player.armor)
            return when {
                player.hp <= 0 -> best
                else -> play(part2, true, best, spent, player, boss, spells)
            }
        }
    }

    private fun copySpells(spells: ArrayList<Spell>): ArrayList<Spell> =
        ArrayList<Spell>().apply { spells.forEach { spell -> add(spell.copy()) } }

    class Player {
        var hp = 0
        var mana = 0
        var dmg = 0
        var armor = 0

        fun copy(): Player = Player().also {
            it.hp = hp
            it.mana = mana
            it.dmg = dmg
            it.armor = armor
        }
    }

    class Spell(
        val name: String,
        val cost: Int,
        val dmg: Int,
        val heal: Int,
        val armor: Int,
        val mana: Int,
        var dur: Int
    ) {
        fun copy(): Spell {
            return Spell(name, cost, dmg, heal, armor, mana, dur)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Spell

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }
}