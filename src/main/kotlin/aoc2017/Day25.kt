package aoc2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day25 {
    @Suppress("SameParameterValue", "KotlinConstantConditions")
    private fun diagnosticChecksum(steps: Int): Int {
        val reg = HashMap<Int, Boolean>()
        var v = 'a'
        var i = 0
        repeat(steps) {
            reg.computeIfAbsent(i) { false }
            if (v == 'a') {
                if (!reg[i]!!) {
                    reg[i] = true
                    i++
                    v = 'b'
                } else {
                    reg[i] = false
                    i--
                    v = 'b'
                }
            } else if (v == 'b') {
                if (!reg[i]!!) {
                    reg[i] = false
                    i++
                    v = 'c'
                } else {
                    reg[i] = true
                    i--
                    v = 'b'
                }
            } else if (v == 'c') {
                if (!reg[i]!!) {
                    reg[i] = true
                    i++
                    v = 'd'
                } else {
                    reg[i] = false
                    i--
                    v = 'a'
                }
            } else if (v == 'd') {
                if (!reg[i]!!) {
                    reg[i] = true
                    i--
                    v = 'e'
                } else {
                    reg[i] = true
                    i--
                    v = 'f'
                }
            } else if (v == 'e') {
                if (!reg[i]!!) {
                    reg[i] = true
                    i--
                    v = 'a'
                } else {
                    reg[i] = false
                    i--
                    v = 'd'
                }
            } else if (v == 'f') {
                if (!reg[i]!!) {
                    reg[i] = true
                    i++
                    v = 'a'
                } else {
                    reg[i] = true
                    i--
                    v = 'e'
                }
            }
        }
        return reg.filter { it.value }.count()
    }

    @Test
    fun part1() {
        assertEquals(3732, diagnosticChecksum(12586542))
    }
}