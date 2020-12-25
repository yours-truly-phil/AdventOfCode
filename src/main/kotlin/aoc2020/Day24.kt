package aoc2020

import aoc2020.TileColor.*
import java.io.File

fun main() {
    val day24 = Day24()
    println(day24.part1(File("files/2020/day24.txt").readText()))
    println(day24.part2(File("files/2020/day24.txt").readText(), 100))
}

class Day24 {
    private val directions = hashMapOf(
        "ne" to Pos(1, 1),
        "e" to Pos(0, 2),
        "se" to Pos(-1, 1),
        "sw" to Pos(-1, -1),
        "w" to Pos(0, -2),
        "nw" to Pos(1, -1)
    )

    fun part1(input: String): Int {
        return initTiles(input).filter { entry -> entry.value == BLACK }.count()
    }

    private fun initTiles(input: String): HashMap<Pos, TileColor> {
        val tiles = HashMap<Pos, TileColor>()
        input.lines().forEach { line ->
            findTargetCoordinates(line).also {
                tiles.computeIfPresent(it) { _, u ->
                    when (u) {
                        WHITE -> BLACK
                        BLACK -> WHITE
                        else -> NONE
                    }
                }
                tiles.computeIfAbsent(it) { BLACK }
            }
        }
        return tiles
    }

    private fun findTargetCoordinates(input: String): Pos {
        val target = Pos(0, 0)
        var idx = 0
        while (idx < input.length) when {
            input.substring(idx).startsWith("ne") -> {
                target += directions["ne"]!!
                idx += 2
            }
            input.substring(idx).startsWith("e") -> {
                target += directions["e"]!!
                idx++
            }
            input.substring(idx).startsWith("se") -> {
                target += directions["se"]!!
                idx += 2
            }
            input.substring(idx).startsWith("sw") -> {
                target += directions["sw"]!!
                idx += 2
            }
            input.substring(idx).startsWith("w") -> {
                target += directions["w"]!!
                idx++
            }
            input.substring(idx).startsWith("nw") -> {
                target += directions["nw"]!!
                idx += 2
            }
        }
        return target
    }

    fun part2(input: String, rounds: Int): Int {
        val tiles = initTiles(input)
        evolve(tiles, rounds)
        return tiles.filter { entry -> entry.value == BLACK }.count()
    }

    private fun evolve(tiles: HashMap<Pos, TileColor>, rounds: Int) {
        for (i in 1..rounds) {
            step(tiles)
            println("after day $i:")
            printTiles(tiles)
        }
    }

    private fun step(tiles: HashMap<Pos, TileColor>) {
        tiles.filter { it.value == BLACK }.forEach { createWhiteNeighborsIfAbsent(it.key, tiles) }

        val newValues = HashMap<Pos, TileColor>()
        tiles.forEach {
            val countBlack = neighbors(it.key, tiles).filter { col -> col == BLACK }.count()
            when {
                it.value == BLACK && (countBlack == 0 || countBlack > 2) -> newValues[it.key] = WHITE
                it.value == WHITE && countBlack == 2 -> newValues[it.key] = BLACK
                else -> newValues[it.key] = tiles[it.key]!!
            }
        }
        newValues.forEach { tiles[it.key] = it.value }
    }

    private fun createWhiteNeighborsIfAbsent(tile: Pos, tiles: HashMap<Pos, TileColor>) {
        tiles.computeIfAbsent(tile + directions["w"]!!) { WHITE }
        tiles.computeIfAbsent(tile + directions["ne"]!!) { WHITE }
        tiles.computeIfAbsent(tile + directions["se"]!!) { WHITE }
        tiles.computeIfAbsent(tile + directions["e"]!!) { WHITE }
        tiles.computeIfAbsent(tile + directions["sw"]!!) { WHITE }
        tiles.computeIfAbsent(tile + directions["nw"]!!) { WHITE }
    }

    private fun neighbors(tile: Pos, tiles: HashMap<Pos, TileColor>): List<TileColor> {
        val colors = ArrayList<TileColor>()
        colors += getNeighborColor(tile, "w", tiles)
        colors += getNeighborColor(tile, "ne", tiles)
        colors += getNeighborColor(tile, "se", tiles)
        colors += getNeighborColor(tile, "e", tiles)
        colors += getNeighborColor(tile, "sw", tiles)
        colors += getNeighborColor(tile, "nw", tiles)
        return colors
    }

    private fun getNeighborColor(tile: Pos, dir: String, tiles: HashMap<Pos, TileColor>): TileColor {
        val neighborLocation = tile + directions[dir]!!
        return if (!tiles.containsKey(neighborLocation)) {
            WHITE
        } else {
            tiles[neighborLocation]!!
        }
    }

    private fun printTiles(tiles: HashMap<Pos, TileColor>) {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        tiles.keys.forEach {
            minX = minOf(minX, it.x)
            maxX = maxOf(maxX, it.x)
            minY = minOf(minY, it.y)
            maxY = maxOf(maxY, it.y)
        }
        for (y in minY..maxY) {
            print("%3d | ".format(y))
            for (x in minX..maxX) {
                var color = NONE
                if (tiles.containsKey(Pos(y, x))) {
                    color = tiles[Pos(y, x)]!!
                }
                when (color) {
                    WHITE -> print("⬡")
                    BLACK -> print("⬢")
                    NONE -> print(" ")
                }
            }
            println()
        }
        println("Black Tiles: ${tiles.values.filter { it == BLACK }.count()}")
    }
}

enum class TileColor {
    WHITE, BLACK, NONE
}

data class Pos(var y: Int, var x: Int) {
    override fun toString(): String {
        return "Pos(y=$y, x=$x)"
    }
}

operator fun Pos.plusAssign(other: Pos) {
    this.y += other.y
    this.x += other.x
}

operator fun Pos.plus(other: Pos) = Pos(this.y + other.y, this.x + other.x)