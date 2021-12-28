package aoc2020

import micros

fun main() {
    runDay15()
}

fun runDay15() {
    val input = "0,1,5,10,3,12,19"

    println("day15part1=${micros { findLastNumber(input, 2020) }}")
    println("day15part2=${micros { findLastNumber(input, 30000000) }}")
}

fun findLastNumber(input: String, goal: Int): Int {
    val mem = Mem(input)

    while (mem.turn < goal) {
        mem.turn++
        if (mem.isNew) {
            step(mem, 0)
        } else {
            step(mem, mem.age)
        }
    }
    return mem.last
}

fun step(mem: Mem, value: Int) {
    mem.last = value
    if (mem.past.containsKey(value)) {
        mem.isNew = false
        mem.age = mem.turn - mem.past[value]!!
    } else {
        mem.isNew = true
    }
    mem.past[value] = mem.turn
}

class Mem(input: String) {
    val past = HashMap<Int, Int>()
    var isNew: Boolean = true
    var last: Int = 0
    var turn: Int = 0
    var age: Int = 0

    init {
        input.split(",")
            .map { it.toInt() }
            .forEach {
                run {
                    this.turn++
                    if (this.past.containsKey(it)) {
                        this.isNew = false
                    }
                    this.past[it] = this.turn
                    this.last = it
                }
            }
    }
}