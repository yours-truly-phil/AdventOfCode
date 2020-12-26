package aoc2015

import java.io.File
import kotlin.math.pow

fun main() {
    Day9().also { println(it.part1(File("files/2015/day9.txt").readText())) }
}

class Day9 {
    fun part1(input: String): Int {
        HashMap<String, City>()
            .also { cities ->
                input.lines()
                    .forEach { parseCities(it, cities) }
            }.apply { return bruteForceShortestDistanceAllCities(values.toList()) }
    }

    private fun bruteForceShortestDistanceAllCities(cities: List<City>): Int {
        var min = Int.MAX_VALUE
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
                if(dist < min) println("route=$route cost=$dist")
                min = minOf(min, dist)
            }
        }
        return min
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