package aoc2015

import com.google.common.collect.Sets
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day17Test {

    @Test
    fun part1() {
        Day17().perms(25, intArrayOf(20, 15, 10, 5, 5)).also { assertEquals(4, it) }
    }

    @Test
    fun guava() {
        val input = intArrayOf(20, 15, 10, 5, 5)
        Sets.powerSet(input.indices.toSet())
            .map { it.map { idx -> input[idx] } }
            .filter { it.isNotEmpty() }
            .forEach { println(it) }
    }
}