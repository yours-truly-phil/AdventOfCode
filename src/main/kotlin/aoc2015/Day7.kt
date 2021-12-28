package aoc2015

import java.io.File

fun main() {
    Day7().also { println(it.part1(File("files/2015/day7.txt").readText(), "a")) }
        .also {
            println(
                it.part2(
                    File("files/2015/day7.txt").readText(),
                    "a",
                    "b",
                    it.part1(File("files/2015/day7.txt").readText(), "a")
                )
            )
        }
}

class Day7 {
    fun part1(input: String, wire: String): Int {
        parseToMap(input)
            .also { return wireValue(wire, it, hashMapOf()) }
    }

    private fun parseToMap(input: String) = input.lines()
        .map { it.split(" -> ") }.associate { it[1] to it[0] }

    fun part2(input: String, wire: String, override: String, value: Int): Int {
        parseToMap(input)
            .toMutableMap()
            .also { it[override] = value.toString() }
            .also { return wireValue(wire, it, hashMapOf()) }
    }

    private fun wireValue(wire: String, instructions: Map<String, String>, res: HashMap<String, Int>): Int {
        if (!res.containsKey(wire)) {
            if (wire.matches("\\d+".toRegex())) {
                return wire.toInt()
            }
            instructions[wire]!!.also { inst ->
                when {
                    inst.matches("\\d+".toRegex()) -> res[wire] = inst.toInt() and 0xffff
                    inst.contains("AND") -> inst.split(" AND ")
                        .also {
                            res[wire] = (wireValue(it[0], instructions, res) and wireValue(
                                it[1],
                                instructions,
                                res
                            )) and 0xffff
                        }
                    inst.contains("OR") -> inst.split(" OR ")
                        .also {
                            res[wire] =
                                (wireValue(it[0], instructions, res) or wireValue(it[1], instructions, res)) and 0xffff
                        }
                    inst.contains("LSHIFT") -> inst.split(" LSHIFT ")
                        .also { res[wire] = (wireValue(it[0], instructions, res) shl it[1].toInt()) and 0xffff }
                    inst.contains("RSHIFT") -> inst.split(" RSHIFT ")
                        .also { res[wire] = (wireValue(it[0], instructions, res) shr it[1].toInt()) and 0xffff }
                    inst.startsWith("NOT") -> res[wire] =
                        wireValue(inst.substring(4), instructions, res).inv() and 0xffff
                    else -> res[wire] = wireValue(inst, instructions, res) and 0xffff
                }
            }
        }
        return res[wire]!!
    }
}