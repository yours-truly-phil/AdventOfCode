package aoc2015

import java.io.File
import java.util.function.BiFunction
import kotlin.math.pow

fun main() {
    Day9().also { println(it.part1(File("files/2015/day9.txt").readText())) }
        .also { println(it.part2(File("files/2015/day9.txt").readText())) }
}

class Day9 {
    fun part1(input: String): Int {
        parseCities(input).also { return bruteForceAllRoutes(it.values.toList(), Int.MAX_VALUE, ::minOf) }
    }

    private fun parseCities(input: String): HashMap<String, City> {
        return HashMap<String, City>()
            .also { cities ->
                input.lines()
                    .forEach { parseCities(it, cities) }
            }
    }

    fun part2(input: String): Int {
        parseCities(input).also { return bruteForceAllRoutes(it.values.toList(), 0, ::maxOf) }
    }

    private fun bruteForceAllRoutes(cities: List<City>, startVal: Int, func: BiFunction<Int, Int, Int>): Int {
        var res = startVal
        val numCombinations = cities.size.toDouble().pow(cities.size.toDouble()).toInt()
        for (i in 0 until numCombinations) {
            val route = i.toString(cities.size).padStart(cities.size, '0')
            var dist = 0
            val visited = arrayListOf(Integer.parseInt(route[0].toString()))
            for (idx in 1 until route.length) {
                val fromIdx = Integer.parseInt(route[idx - 1].toString())
                val toIdx = Integer.parseInt(route[idx].toString())
                if (visited.contains(toIdx)) {
                    dist = -1
                    break
                } else {
                    visited.add(toIdx)
                }
                val from = cities[fromIdx]
                val to = cities[toIdx]
                if (from.to.containsKey(to)) {
                    dist += from.to[to]!!
                } else {
                    dist = -1
                    break
                }
            }
            if (dist >= 0) {
                res = func.apply(res, dist)
            }
        }
        return res
    }


    private fun parseCities(it: String, cities: HashMap<String, City>) {
        val name = it.substring(0, it.indexOf(" "))
        val to = it.substring(it.indexOf(" to ") + 4, it.indexOf("=") - 1)
        val cost = it.substring(it.indexOf("=") + 2).toInt()
        cities.computeIfAbsent(name) { City(name) }
        cities.computeIfAbsent(to) { City(to) }
        cities[name]!!.to[cities[to]!!] = cost
        cities[to]!!.to[cities[name]!!] = cost
    }


    data class City(val name: String) {
        val to = HashMap<City, Int>()
    }
}