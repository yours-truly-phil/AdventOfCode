package aoc2018

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.test.assertEquals

class Day9 {
    fun scoreOfWinner(noPlayers: Int, valueLastMarble: Int): Long {
        val start = System.currentTimeMillis()
        val scores = LongArray(noPlayers)
        var idx = 0
        var curPlayer = 0
        val marbles = ArrayDeque<Int>().also { it.add(0) }
        for (i in 1..valueLastMarble) {
            if (i % 23 == 0) {
                scores[curPlayer] += i.toLong()
                idx += (marbles.size - 7)
                idx %= marbles.size
                scores[curPlayer] += marbles.removeAt(idx).toLong()
            } else {
                idx += 2
                idx %= marbles.size
                marbles.add(idx, i)
            }
            curPlayer++
            curPlayer %= scores.size

            // maybe just rotate if idx gets too big
            if (idx > 150_000) {
                Collections.rotate(marbles, -idx)
                idx = 0
            }
        }
        println("${System.currentTimeMillis() - start}ms")
        return scores.maxOf { it }
    }

    @Test
    fun sample() {
        assertEquals(32, scoreOfWinner(9, 25))
        assertEquals(8317, scoreOfWinner(10, 1618))
        assertEquals(146373, scoreOfWinner(13, 7999))
        assertEquals(2764, scoreOfWinner(17, 1104))
        assertEquals(54718, scoreOfWinner(21, 6111))
        assertEquals(37305, scoreOfWinner(30, 5807))
    }

    @Test
    fun part1() {
        assertEquals(388131, scoreOfWinner(459, 72103))
    }

    @Test
    fun part2() {
        assertEquals(3239376988, scoreOfWinner(459, 72103 * 100))
    }
}