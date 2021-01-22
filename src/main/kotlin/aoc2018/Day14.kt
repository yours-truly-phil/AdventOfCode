package aoc2018

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

fun main() {
    Day14().apply { println(appearsAfter("503761")) }
}

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

    fun appearsAfter(num: String): Int {
        val scores = ByteArray(500000000)
        scores[0] = 3
        scores[1] = 7
        var size = 2
        var i1 = 0
        var i2 = 1

        val lastNums = ArrayDeque<Int>()
        for (i in 2 until num.length) {
            lastNums += 0
        }
        lastNums += 3
        lastNums += 7

        while (true) {
            val s1 = scores[i1]
            val s2 = scores[i2]
            val sum = s1 + s2
            val new1 = sum % 10
            if (sum >= 10) {
                val new2 = (sum / 10) % 10
                scores[size] = new2.toByte()
                size++
                lastNums.removeFirst()
                lastNums.addLast(new2)
            }
            scores[size] = new1.toByte()
            size++
            lastNums.removeFirst()
            lastNums.addLast(new1)

            i1 += 1 + s1
            i1 %= size
            i2 += 1 + s2
            i2 %= size

            if (lastNums.joinToString("") == num) {
                break
            }
        }

        return size - num.length
    }

    @Test
    fun sample() {
        assertEquals("5158916779", scoresOfRecipesAfter(9))
        assertEquals("0124515891", scoresOfRecipesAfter(5))
        assertEquals("9251071085", scoresOfRecipesAfter(18))
        assertEquals("5941429882", scoresOfRecipesAfter(2018))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(9, appearsAfter("51589"))
        assertEquals(5, appearsAfter("01245"))
        assertEquals(18, appearsAfter("92510"))
        assertEquals(2018, appearsAfter("59414"))
    }

    @Test
    fun part1() {
        assertEquals("1044257397", scoresOfRecipesAfter(503761))
    }

    @Test
    fun part2() {
        assertEquals(-1, appearsAfter("503761"))
    }
}