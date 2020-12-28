package aoc2015

fun main() {
    Day22().also { println(it.part1(71, 10)) }
}

class Day22 {
    private val totalCasts = 12
    private val noSpells = 5

    fun part1(bossHp: Int, bossDmg: Int): Int {
        var minManaToWin = Int.MAX_VALUE
        (0.."${noSpells - 1}".repeat(totalCasts).toLong(5)).forEach { spellSequence ->
            playGame(
                spellSequence.toString(noSpells).padStart(totalCasts, '0').toCharArray(),
                Player().apply { hp = 50 }.apply { mana = 500 },
                Boss().apply { hp = bossHp }.apply { dmg = bossDmg })
                .also {
                    if (minManaToWin > it) {
                        minManaToWin = it
                        println(it)
                    }
                }
        }

        return minManaToWin
    }

    private fun playGame(casts: CharArray, player: Player, boss: Boss): Int {
        var totalMana = 0
        for (i in casts.size - 1 downTo 0) {
            val cast = casts[i]

            tick(player, boss)
            when (cast) {
                '0' -> {
                    player.mana -= 53
                    totalMana += 53
                    boss.hp -= 4
                }
                '1' -> {
                    player.mana -= 73
                    totalMana += 73
                    boss.hp -= 2
                    player.hp += 2
                }
                '2' -> {
                    if (player.shieldTimer > 0) return Int.MAX_VALUE
                    player.shieldTimer = 6
                    player.mana -= 113
                    totalMana += 113
                }
                '3' -> {
                    if (player.poisonTimer > 0) return Int.MAX_VALUE
                    player.poisonTimer = 6
                    player.mana -= 173
                    totalMana += 173
                }
                '4' -> {
                    if (player.rechargeTimer > 0) return Int.MAX_VALUE
                    player.rechargeTimer = 5
                    player.mana -= 229
                    totalMana += 229
                }
            }
            if (player.mana < 0 || player.hp < 0) return Int.MAX_VALUE
            if (boss.hp < 0) return totalMana


            tick(player, boss)

            if (player.shieldTimer > 0) player.hp -= maxOf(1, boss.dmg - 7) else player.hp -= boss.dmg

            if (player.mana < 0 || player.hp < 0) return Int.MAX_VALUE
            if (boss.hp < 0) return totalMana
        }
        return Int.MAX_VALUE
    }

    private fun tick(player: Player, boss: Boss) {
        if (player.rechargeTimer > 0) player.mana += 101
        if (player.poisonTimer > 0) boss.hp -= 3
        player.shieldTimer--
        player.poisonTimer--
        player.rechargeTimer--
    }

    class Boss {
        var hp = 0
        var dmg = 0
    }

    class Player {
        var hp = 0
        var mana = 0
        var shieldTimer = 0
        var poisonTimer = 0
        var rechargeTimer = 0
    }
}