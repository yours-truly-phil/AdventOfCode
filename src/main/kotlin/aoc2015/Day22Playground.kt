package aoc2015

/**
 * TODO have another look at it since it's still not working
 */
fun main() {
    Day22Playground().also { println(it.lowestManaForWin(50, 500, 71, 10, false)) }
        .also { println(it.lowestManaForWin(50, 500, 71, 10, true)) }
}

class Day22Playground {
    val allSpells = arrayListOf(
        Spell("Magic Missile", 53, 4, 0, 0, 0, 1),
        Spell("Drain", 73, 2, 2, 0, 0, 1),
        Spell("Shield", 113, 0, 0, 7, 0, 6),
        Spell("Poison", 173, 3, 0, 0, 0, 6),
        Spell("Recharge", 229, 0, 0, 0, 101, 5)
    )

    fun lowestManaForWin(playerHp: Int, playerMana: Int, bossHp: Int, bossDmg: Int, part2: Boolean): Int {
        return play(part2, true, Int.MAX_VALUE, 0, playerHp, playerMana, bossHp, bossDmg, ArrayList())
    }

    fun play(
        part2: Boolean,
        myTurn: Boolean,
        best: Int,
        manaSpent: Int,
        playerHp: Int,
        playerMana: Int,
        bossHpVal: Int,
        bossDamage: Int,
        curSpells: List<Spell>
    ): Int {
        var spent = manaSpent
        var hp = playerHp
        var mana = playerMana
        var spells = curSpells
        var bossHp = bossHpVal

        if (spent >= best) return best
        if (part2 && myTurn && hp == 1) return best

        mana += spells.sumBy { it.mana }
        var damage = spells.sumBy { it.dmg }
        val armor = spells.sumBy { it.def }

        bossHp -= damage
        if (bossHp <= 0) return spent


        spells.forEach {
            it.dur = it.dur - 1
        }
        spells = spells.filter { it.dur > 0 }

        if (myTurn) {
            if (part2) hp--

            val buyableSpells = allSpells.filter { it.cost <= mana && !spells.contains(it) }

            var newBest = best
            for (s in buyableSpells) {
                var extraDamage = 0
                var heal = 0
                val sp = ArrayList<Spell>()
                for(spell in spells) sp.add(spell)
                if (s.dur == 1) {
                    extraDamage = s.dmg
                    heal = s.heal
                } else {
                    sp.add(s)
                }
                spent += s.cost
                mana -= s.cost
                hp += heal

                bossHp -= extraDamage
                newBest = if (bossHp <= 0) minOf(newBest, spent)
                else minOf(newBest, play(part2, false, newBest, spent, hp, mana, bossHp, bossDamage, sp))
            }
            return newBest
        } else {
            damage = maxOf(bossDamage - armor, 1)
            hp -= damage
            return if (hp > 0) play(part2, true, best, spent, hp, mana, bossHp, bossDamage, spells)
            else best
        }
    }

    class Spell(
        val name: String,
        val cost: Int,
        val dmg: Int,
        val heal: Int,
        val def: Int,
        val mana: Int,
        var dur: Int
    ) {
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