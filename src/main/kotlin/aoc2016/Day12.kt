package aoc2016

import java.io.File

fun main() {
    Day12().apply { println(part1(File("files/2016/day12.txt").readText())) }
}

class Day12 {
    fun part1(input: String): Int {
        val register = hashMapOf("a" to 0, "b" to 0, "c" to 0, "d" to 0)
        val instructions = input.lines().map { it.split(" ") }
        runInstructions(instructions, register)
        return register["a"]!!
    }

    private fun runInstructions(instructions: List<List<String>>, register: HashMap<String, Int>) {
        var index = 0
        while (true) {
            val inst = instructions[index]
            if (inst[0] == "cpy") {
                if (inst[1] in register.keys) {
                    register[inst[2]] = register[inst[1]]!!
                } else {
                    register[inst[2]] = inst[1].toInt()
                }
            } else if (inst[0] == "inc") {
                register[inst[1]] = register[inst[1]]!! + 1
            } else if (inst[0] == "dec") {
                register[inst[1]] = register[inst[1]]!! - 1
            } else if (inst[0] == "jnz") {
                if (register[inst[1]] != 0) {
                    index += inst[2].toInt() - 1
                }
            }
            index++
            if (index >= instructions.size) break
        }
    }
}