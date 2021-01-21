package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
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
        return cars.map { "${it.x},${it.y}" }.filter { !pos.add(it) }.first()
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
                        cars.add(Car(x, y, 0, 0))
                        track[y][x] = '|'
                    }
                    '>' -> {
                        cars.add(Car(x, y, 1, 0))
                        track[y][x] = '-'
                    }
                    'v' -> {
                        cars.add(Car(x, y, 2, 0))
                        track[y][x] = '|'
                    }
                    '<' -> {
                        cars.add(Car(x, y, 3, 0))
                        track[y][x] = '-'
                    }
                }
            }
        }
        return Pair(track, cars)
    }

    fun step(cars: List<Car>, track: Array<CharArray>) {
        cars.forEach {
            when (it.dir) {
                0 -> it.y--
                1 -> it.x++
                2 -> it.y++
                3 -> it.x--
            }
            when {
                track[it.y][it.x] == '+' -> {
                    when (it.turns % 3) {
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
    }

    class Car(var x: Int, var y: Int, var dir: Int, var turns: Int) {
        override fun toString(): String {
            return "Car(x=$x, y=$y, dir=$dir, turns=$turns)"
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
    fun part1() {
        assertEquals("100,21", locateCrash(File("files/2018/day13.txt").readText()))
    }
}