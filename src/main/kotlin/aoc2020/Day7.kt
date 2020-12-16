package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay7()
}

fun runDay7() {
    val lines = File("aoc2020/day7.txt").readLines()

    println("day7part1=${micros(Callable { day7part1(lines) })}")
    println("day7part2=${micros(Callable { day7part2(lines) })}")
}

fun day7part1(lines: List<String>): Int {
    return initBags(lines).values.count { bagCanCarryGold(it, it.color) }
}

fun day7part2(lines: List<String>): Int {
    return countBagAndChildren(initBags(lines), "shiny gold") - 1
}

fun countBagAndChildren(bags: Map<String, Bag>, color: String): Int {
    return bags[color]!!.contains.entries
        .map { it.value * countBagAndChildren(bags, it.key.color) }
        .fold(1, { acc, i -> acc + i })
}

private fun initBags(lines: List<String>): Map<String, Bag> {
    val bags = createBags(lines)
    fillBags(lines, bags)
    return bags
}

fun bagCanCarryGold(bag: Bag, sourceColor: String): Boolean {
    bag.contains.keys.forEach {
        if (it.color == "shiny gold") {
            return true
        } else {
            if (it.color != sourceColor) {
                if (bagCanCarryGold(it, sourceColor)) {
                    return true
                }
            }
        }
    }
    return false
}

fun fillBags(lines: List<String>, bags: Map<String, Bag>) {
    lines.forEach { line ->
        val bag = bags[parseColor(line)]!!
        parseContaining(line).forEach { bag.contains[bags[it.second]!!] = it.first }
    }
}

fun parseContaining(line: String): List<Pair<Int, String>> {
    val c = "contain "
    val l = line.substring(line.indexOf(c) + c.length, line.length - 1)
    if (l == "no other bags") return ArrayList()
    return l.split(", ")
        .map { it.split(" ") }
        .map { Pair(it[0].toInt(), "${it[1]} ${it[2]}") }
}

fun createBags(lines: List<String>): Map<String, Bag> {
    return lines.map { parseColor(it) }
        .map { Bag(it) }
        .map { it.color to it }
        .toMap()
}

fun parseColor(line: String): String {
    return line.substring(0, line.indexOf("bags") - 1)
}

class Bag(val color: String) {
    val contains = HashMap<Bag, Int>()
}