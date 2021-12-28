package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.test.assertEquals

class Day13 {
    private fun locateCrash(input: String): String {
        val (track, cars) = parseCarsAndTrack(input)

        cars.forEach { println(it) }
        printTrackAndCars(cars, track)
        while (!isCrash(cars)) {
            step(cars, track)
//            printTrackAndCars(cars, track)
        }
        printTrackAndCars(cars, track)
        return crashLocation(cars)
    }

    private fun crashLocation(cars: List<Car>): String {
        val pos = HashSet<String>()
        return cars.map { "${it.x},${it.y}" }.first { !pos.add(it) }
    }

    private fun printTrackAndCars(cars: List<Car>, track: Array<CharArray>) {
        val out = Array(track.size) { CharArray(track[0].size) }
        for (y in track.indices) {
            for (x in track[y].indices) {
                out[y][x] = track[y][x]
            }
        }
        for (car in cars) {
            when (car.dir) {
                0 -> out[car.y][car.x] = '^'
                1 -> out[car.y][car.x] = '>'
                2 -> out[car.y][car.x] = 'v'
                3 -> out[car.y][car.x] = '<'
            }
        }
        out.forEach { println(it.joinToString("")) }
    }

    private fun lastCar(input: String): String {
        val (track, cars) = parseCarsAndTrack(input)

        while (cars.count { !it.crashed } != 1) {
            stepAndCheckCrash(cars, track)
        }

        val last = cars.find { !it.crashed }!!
        return "${last.x},${last.y}"
    }

    private fun stepAndCheckCrash(cars: MutableList<Car>, track: Array<CharArray>) {
        cars.sort()
        cars.forEach {
            stepCar(it, track)
            updateCrashes(cars)
        }
    }

    private fun updateCrashes(cars: List<Car>) {
        val pos = HashSet<String>()
        val crashedLocs = cars.filter { !it.crashed }.map { "${it.x},${it.y}" }.filter { !pos.add(it) }
        for (crash in crashedLocs) {
            cars.filter { "${it.x},${it.y}" == crash }.forEach { it.crashed = true }
        }
    }

    private fun isCrash(cars: List<Car>): Boolean {
        val pos = HashSet<String>()
        return cars.map { "${it.x},${it.y}" }
            .any { !pos.add(it) }
    }

    private fun parseCarsAndTrack(input: String): Pair<Array<CharArray>, ArrayList<Car>> {
        val longest = input.lines().maxOf { it.length }
        val track = input.lines()
            .map { it.padEnd(longest) }
            .map { it.toCharArray() }.toTypedArray()

        val cars = ArrayList<Car>()
        for (y in track.indices) {
            for (x in track[y].indices) {
                when (track[y][x]) {
                    '^' -> {
                        cars.add(Car(x, y, 0, 0, false))
                        track[y][x] = '|'
                    }
                    '>' -> {
                        cars.add(Car(x, y, 1, 0, false))
                        track[y][x] = '-'
                    }
                    'v' -> {
                        cars.add(Car(x, y, 2, 0, false))
                        track[y][x] = '|'
                    }
                    '<' -> {
                        cars.add(Car(x, y, 3, 0, false))
                        track[y][x] = '-'
                    }
                }
            }
        }
        return Pair(track, cars)
    }

    fun step(cars: List<Car>, track: Array<CharArray>) {
        cars.forEach {
            stepCar(it, track)
        }
    }

    private fun stepCar(it: Car, track: Array<CharArray>) {
        when (it.dir) {
            0 -> it.y--
            1 -> it.x++
            2 -> it.y++
            3 -> it.x--
        }
        when {
            track[it.y][it.x] == '+' -> {
                when (it.turns) {
                    0 -> {
                        it.dir += 3
                        it.dir %= 4
                    }
                    2 -> {
                        it.dir++
                        it.dir %= 4
                    }
                }
                it.turns++
                it.turns %= 3
            }
            track[it.y][it.x] == '\\' -> it.dir = 3 - it.dir
            track[it.y][it.x] == '/' -> {
                when (it.dir) {
                    0 -> it.dir = 1
                    1 -> it.dir = 0
                    2 -> it.dir = 3
                    3 -> it.dir = 2
                }
            }
        }
    }

    class Car(var x: Int, var y: Int, var dir: Int, var turns: Int, var crashed: Boolean) : Comparable<Car> {
        override fun toString(): String {
            return "Car(x=$x, y=$y, dir=$dir, turns=$turns)"
        }

        override fun compareTo(other: Car): Int {
            return "${y.toString().padStart(3, '0')},${x.toString().padStart(3, '0')}"
                .compareTo("${other.y.toString().padStart(3, '0')},${other.x.toString().padStart(3, '0')}")
        }
    }

    @Test
    fun sample() {
        assertEquals("7,3", locateCrash("/->-\\        \n" +
                "|   |  /----\\\n" +
                "| /-+--+-\\  |\n" +
                "| | |  | v  |\n" +
                "\\-+-/  \\-+--/\n" +
                "  \\------/   "))
    }

    @Test
    fun part2Sample() {
        assertEquals("6,4", lastCar("/>-<\\  \n" +
                "|   |  \n" +
                "| /<+-\\\n" +
                "| | | v\n" +
                "\\>+</ |\n" +
                "  |   ^\n" +
                "  \\<->/"))
    }

    @Test
    fun part1() {
        assertEquals("100,21", locateCrash(File("files/2018/day13.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals("113,109", lastCar(File("files/2018/day13.txt").readText()))
    }
}