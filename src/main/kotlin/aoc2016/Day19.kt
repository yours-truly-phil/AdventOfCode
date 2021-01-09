package aoc2016

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19 {
    fun elfThatGetsAllPresents(input: Int): Int {
        var elf = Elf(1)
        val first = elf
        for (i in 2..input) {
            val new = Elf(i)
            elf.next = new
            elf = new
        }
        elf.next = first
        elf = first
        while (elf.next != elf) {
            elf.presents += elf.next.presents
            elf.next = elf.next.next
            elf = elf.next
        }
        return elf.id
    }

    class Elf(val id: Int) {
        var presents = 1
        lateinit var next: Elf
        override fun toString(): String {
            return "Elf($id, p=$presents)"
        }
    }

    @Test
    fun part1() {
        assertEquals(1834903, elfThatGetsAllPresents(3014603))
    }

    @Test
    fun sample() {
        assertEquals(3, elfThatGetsAllPresents(5))
    }
}