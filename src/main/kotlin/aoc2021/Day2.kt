package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day2 {
    private fun part1(input: List<String>): Int {
        val pos = Vec2i(0, 0)
        input.forEach {
            val row = it.split(" ")
            val steps = row[1].toInt()
            when (row[0]) {
                "forward" -> pos.moveX(steps)
                "down" -> pos.moveY(steps)
                "up" -> pos.moveY(-steps)
            }
        }
        return pos.x * pos.y
    }

    private fun part2(input: List<String>): Int {
        val pos = Vec2i(0, 0)
        var aim = 0
        input.forEach {
            val row = it.split(" ")
            val num = row[1].toInt()
            when (row[0]) {
                "forward" -> {
                    pos.moveX(num)
                    pos.moveY(aim * num)
                }
                "down" -> aim += num
                "up" -> aim -= num
            }
        }
        return pos.x * pos.y
    }

    class Vec2i(var x: Int, var y: Int) {
        fun moveX(steps: Int) {
            x += steps
        }

        fun moveY(steps: Int) {
            y += steps
        }
    }

    @Test
    fun part1() {
        assertEquals(150, part1(("forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2").lines()))
        assertEquals(2027977, part1(File("files/2021/day2.txt").readLines()))
    }

    @Test
    fun part2() {
        assertEquals(900, part2(("forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2").lines()))
        assertEquals(1903644897, part2(File("files/2021/day2.txt").readLines()))
    }
}
