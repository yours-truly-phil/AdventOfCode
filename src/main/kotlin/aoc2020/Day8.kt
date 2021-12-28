package aoc2020

import aoc2020.Type.*
import micros
import java.io.File
import java.util.*

fun main() {
    runDay8()
}

fun runDay8() {
    val lines = File("files/2020/day8.txt").readLines()
    val instructions = lines.map { Instruction(it) }.toTypedArray()

    println("day8part1=${micros { day8part1(instructions) }}")
    println("day8part2=${micros { day8part2(instructions) }}")
}

fun day8part1(instructions: Array<Instruction>): Int {
    return findLoop(instructions, 0, 0)
}

fun day8part2(arr: Array<Instruction>): Int {
    for (inst in arr) when (inst.type) {
        JMP -> {
            arr.forEach { it.count = 0 }
            inst.type = NOP
            try {
                return executeToEnd(arr, 0, 0)
            } catch (e: Exception) {
                inst.type = JMP
            }
        }
        NOP -> {
            arr.forEach { it.count = 0 }
            inst.type = JMP
            try {
                return executeToEnd(arr, 0, 0)
            } catch (e: Exception) {
                inst.type = NOP
            }
        }
        ACC -> {
            // do nothing
        }
    }
    throw Exception("no solution found")
}

fun executeToEnd(arr: Array<Instruction>, no: Int, acc: Int): Int {
    if (no == arr.size - 1) return acc
    val inst = arr[no]
    if (inst.count > 0) throw Exception("loop! $inst")

    inst.count++
    return when (inst.type) {
        ACC -> executeToEnd(arr, no + 1, acc + inst.value)
        JMP -> executeToEnd(arr, no + inst.value, acc)
        NOP -> executeToEnd(arr, no + 1, acc)
    }
}

fun findLoop(arr: Array<Instruction>, no: Int, acc: Int): Int {
    val inst = arr[no]
    return if (inst.count > 0) acc
    else executeInstructions(arr, no, acc, inst)
}

fun executeInstructions(arr: Array<Instruction>, no: Int, acc: Int, inst: Instruction): Int {
    inst.count++
    return when (inst.type) {
        ACC -> findLoop(arr, no + 1, acc + inst.value)
        JMP -> findLoop(arr, no + inst.value, acc)
        NOP -> findLoop(arr, no + 1, acc)
    }
}

enum class Type {
    ACC, JMP, NOP
}

class Instruction(line: String) {
    var type: Type
    val value: Int
    var count = 0

    init {
        val parts = line.split(" ")
        type = valueOf(parts[0].uppercase(Locale.getDefault()))
        value = parts[1].toInt()
    }
}