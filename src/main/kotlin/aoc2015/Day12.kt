package aoc2015

import com.google.gson.Gson
import com.google.gson.JsonElement
import java.io.File

fun main() {
    Day12().also { println(it.part1(File("files/2015/day12.txt").readText())) }
        .also { println(it.part2(File("files/2015/day12.txt").readText())) }
}

class Day12 {
    fun part1(input: String): Int = Regex("[-]?\\d+")
        .findAll(input)
        .map { it.value.toInt() }
        .sum()

    fun part2(input: String): Int {
        Gson().fromJson(input, JsonElement::class.java).asJsonObject.also { return parse(it) }
    }

    private fun parse(input: JsonElement): Int {
        if (input.isJsonPrimitive && input.asJsonPrimitive.isNumber) return input.asJsonPrimitive.asInt
        if (input.isJsonPrimitive) return 0
        if (input.isJsonArray) return input.asJsonArray.sumOf { parse(it) }
        if (input.isJsonNull) return 0
        var res = 0
        if (input.isJsonObject) {
            for (entry in input.asJsonObject.entrySet()) {
                if (entry.value.isJsonPrimitive) {
                    if (entry.value.toString() == "\"red\"") {
                        return 0
                    }
                }
            }
            for (entry in input.asJsonObject.entrySet()) {
                res += parse(entry.value)
            }
        }
        return res
    }
}