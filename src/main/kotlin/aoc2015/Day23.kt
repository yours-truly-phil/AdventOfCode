package aoc2015

import java.io.File

fun main() {
    Day23().also { println(it.part(File("files/2015/day23.txt").readText(), false)) }
        .also { println(it.part(File("files/2015/day23.txt").readText(), true)) }
}

class Day23 {
    fun part(input: String, part2: Boolean): Int {
        val lines = input.lines()
        var i = 0
        val reg = hashMapOf("a" to 0, "b" to 0)
        if (part2) reg["a"] = 1
        while (i in lines.indices) {
            val l = lines[i].split(" ")
            when {
                l[0] == "hlf" -> reg[l[1]] = reg[l[1]]!! / 2
                l[0] == "tpl" -> reg[l[1]] = reg[l[1]]!! * 3
                l[0] == "inc" -> reg[l[1]] = reg[l[1]]!! + 1
                l[0] == "jmp" -> i += l[1].toInt() - 1
                l[0] == "jie" -> if (reg[l[1].substring(0, l[1].indexOf(","))]!! % 2 == 0) i += l[2].toInt() - 1
                l[0] == "jio" -> if (reg[l[1].substring(0, l[1].indexOf(","))]!! == 1) i += l[2].toInt() - 1
            }
            i++
        }
        return reg["b"]!!
    }
}