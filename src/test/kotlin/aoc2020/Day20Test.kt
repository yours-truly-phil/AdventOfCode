package aoc2020

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class Day20Test {

    @Test
    fun invert10bits() {
        val s = "0010001110"
        var num = s.toInt(2)
        println(num.biString())
        num = num.biString().reversed().toInt(2)
        println(num.biString())
    }

    @Test
    fun part1() {
        val day20 = Day20("files/2020/day20Test.txt")
        day20.part1()
    }
}