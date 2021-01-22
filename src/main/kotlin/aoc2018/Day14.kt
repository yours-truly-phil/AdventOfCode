package aoc2018

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14 {
    fun scoresOfRecipesAfter(offset: Int): String {

        val scores = ArrayList<Int>(offset + 10)
        scores += 3
        scores += 7
        var i1 = 0
        var i2 = 1
        while (scores.size < offset + 10) {
            val s1 = scores[i1]
            val s2 = scores[i2]
            val sum = s1 + s2
            val new1 = sum % 10
            if (sum >= 10) {
                val new2 = (sum / 10) % 10
                scores += new2
            }
            scores += new1
            i1 += 1 + s1
            i1 %= scores.size
            i2 += 1 + s2
            i2 %= scores.size
        }
        return scores.subList(offset, offset + 10).joinToString("")
    }

    @Test
    fun sample() {
        assertEquals("5158916779", scoresOfRecipesAfter(9))
        assertEquals("0124515891", scoresOfRecipesAfter(5))
        assertEquals("9251071085", scoresOfRecipesAfter(18))
        assertEquals("5941429882", scoresOfRecipesAfter(2018))
    }

    @Test
    fun part1() {
        assertEquals("1044257397", scoresOfRecipesAfter(503761))
    }
}