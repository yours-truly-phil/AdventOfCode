package aoc2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19 {
    private fun elfThatGetsAllPresents(input: Int): Int {
        var elf = parseElves(input)
        while (elf.next != elf) {
            elf.next = elf.next.next
            elf = elf.next
        }
        return elf.id
    }

    @Suppress("SameParameterValue")
    private fun elfWithAllPresentsCircle(input: Int): Int {
        var elf = parseElves(input)
        var total = input
        while (elf.next != elf) {
            var targetElf = elf
            var prev = elf
            repeat(total / 2) {
                prev = targetElf
                targetElf = targetElf.next
            }
            prev.next = targetElf.next
            total--
            elf = elf.next
        }
        return elf.id
    }

    private fun parseElves(input: Int): Elf {
        var elf = Elf(1)
        val first = elf
        for (i in 2..input) {
            val new = Elf(i)
            elf.next = new
            elf = new
        }
        elf.next = first
        elf = first
        return elf
    }

    // solution stolen, but it's just soo much better than my (very) long running removing from lists for part2
    @Suppress("SameParameterValue")
    private fun elfEraseOppositeInCircle(n: Int): Int {
        var w = 1
        (1 until n).forEach {
            w = w % it + 1
            if (w > (it + 1) / 2) w++
        }
        return w
    }

    class Elf(val id: Int) {
        lateinit var next: Elf
        override fun toString(): String {
            return "Elf($id)"
        }
    }

    @Test
    fun part2() {
        assertEquals(1420280, elfEraseOppositeInCircle(3014603))
    }

    @Test
    fun part1() {
        assertEquals(1834903, elfThatGetsAllPresents(3014603))
    }

    @Test
    fun sample() {
        assertEquals(3, elfThatGetsAllPresents(5))
        assertEquals(2, elfWithAllPresentsCircle(5))
    }
}