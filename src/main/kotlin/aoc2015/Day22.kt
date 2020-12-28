package aoc2015

fun main() {
    Day22().also { println("Part1=${it.part1(71, 10, false)}") }
    Day22().also { println("Part2=${it.part1(71, 10, true)}") }
}

class Day22 {
    private val totalCasts = 20
    private val noSpells = 5

    fun part1(bossHp: Int, bossDmg: Int, part2: Boolean): Int {
        var minManaToWin = Int.MAX_VALUE
        var i = 0L
        while (i <= "${noSpells - 1}".repeat(totalCasts).toLong(5)) {
            val spellSequence = i.toString(noSpells).padStart(totalCasts, '0').toCharArray()
            try {
                playGame(
                    spellSequence,
                    Player().apply { hp = 50 }.apply { mana = 500 },
                    Boss().apply { hp = bossHp }.apply { dmg = bossDmg }, part2
                )
                    .also {
                        if (minManaToWin > it) {
                            minManaToWin = it
                            println("$it with spells: ${spellSequence.joinToString(",")}")
                        }
                    }
                i++
            } catch (e: InvalidMove) {
                i = nextMove(e.idx, spellSequence)
            }
        }
        return minManaToWin
    }

    private fun nextMove(idx: Int, sequence: CharArray): Long {
        if (idx < 0) return Long.MAX_VALUE
        var sVal = sequence[idx].toString().toInt()
        return if (sVal == noSpells - 1) {
            nextMove(idx - 1, sequence)
        } else {
            sVal++
            sequence[idx] = sVal.toString()[0]
            for (i in idx + 1 until sequence.size) {
                sequence[i] = '0'
            }
            sequence.joinToString("").toLong(noSpells)
        }
    }

    class InvalidMove(message: String?, val idx: Int) : Exception(message) {}

    private fun playGame(casts: CharArray, player: Player, boss: Boss, part2: Boolean): Int {
        var totalMana = 0
        for (i in casts.indices) {
            val cast = casts[i]

            if (part2) {
                player.hp -= 1
                if (player.hp <= 0) throw InvalidMove("invalid move", i)
            }
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
                    if (player.shieldTimer > 0) throw InvalidMove("invalid move", i)
                    player.shieldTimer = 6
                    player.mana -= 113
                    totalMana += 113
                }
                '3' -> {
                    if (player.poisonTimer > 0) throw InvalidMove("invalid move", i)
                    player.poisonTimer = 6
                    player.mana -= 173
                    totalMana += 173
                }
                '4' -> {
                    if (player.rechargeTimer > 0) throw InvalidMove("invalid move", i)
                    player.rechargeTimer = 5
                    player.mana -= 229
                    totalMana += 229
                }
            }
            if (player.mana < 0 || player.hp < 0) throw InvalidMove("invalid move", i)
            if (boss.hp < 0) return totalMana

            tick(player, boss)
            if (boss.hp < 0) return totalMana

            if (player.shieldTimer > 0) player.hp -= maxOf(1, boss.dmg - 7) else player.hp -= boss.dmg

            if (player.mana < 0 || player.hp < 0) throw InvalidMove("invalid move", i)
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