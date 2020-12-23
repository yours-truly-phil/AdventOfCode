package aoc2020

import java.io.File

fun main() {
    val day23 = Day23(File("files/2020/day23.txt").readText(), 9)
    println(day23.part1(100))
    val day23Part2 = Day23(File("files/2020/day23.txt").readText(), 1_000_000)
    println(day23Part2.part2(10_000_000))

}

class Day23(input: String, size: Int) {
    private val sortedCups: List<Cup>
    var selected: Cup

    init {
        val cupArr = IntArray(size)
        val parsedInput = input.map { it.toString().toInt() }.toIntArray()
        for (i in parsedInput.indices) {
            cupArr[i] = parsedInput[i]
        }
        for (i in parsedInput.size until size) {
            cupArr[i] = i + 1
        }

        val cups = cupArr.map { Cup(it) }
        for (i in cups.indices) {
            cups[i].next = cups[(i + 1) % cups.size]
        }
        selected = cups[0]
        sortedCups = cups.sorted()
    }

    fun part1(rounds: Int): String {
        play(rounds)
        var res = ""
        var cur = sortedCups[0]
        for (i in 1 until sortedCups.size) {
            cur = cur.next
            res += cur.num.toString()
        }
        return res
    }

    private fun repr(cup: Cup, size: Int): String {
        var res = ""
        var cur = cup
        for (i in 0 until size) {
            res += cur.num
            cur = cur.next
        }
        return res
    }

    private fun play(rounds: Int) {
        for (i in 0 until rounds) {
//            println("-- move ${i + 1} --\n${repr(selected, sortedCups.size)}")
            move()
        }
    }

    fun part2(rounds: Int): Long {
        play(rounds)
        return sortedCups[0].next.num.toLong() * sortedCups[0].next.next.num.toLong()
    }

    private fun move() {
        val destination = findDestination(selected)
        val firstPickedUp = selected.next
        selected.next = firstPickedUp.next.next.next
        firstPickedUp.next.next.next = destination.next
        destination.next = firstPickedUp
        selected = selected.next
    }

    private fun findDestination(sel: Cup): Cup {
        val pickedUp = setOf(sel.num, sel.next.num, sel.next.next.num, sel.next.next.next.num)
        var num = sel.num
        while (num in pickedUp) {
            num--
            if (num == 0) {
                num = sortedCups.size
            }
        }
        return findCup(num)
    }

    private fun findCup(num: Int): Cup {
        return sortedCups[num - 1]
    }

    data class Cup(val num: Int) : Comparable<Cup> {

        lateinit var next: Cup

        override fun compareTo(other: Cup): Int {
            return num.compareTo(other.num)
        }
    }
}