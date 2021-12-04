package aoc2020

import java.io.File

fun main() {
    val day20Vis = Day20Vis()
    println(day20Vis.part1(File("files/2020/day20.txt").readText()))
    val monster = "                  # \n" +
            "#    ##    ##    ###\n" +
            " #  #  #  #  #  #   "
    println(day20Vis.part2(File("files/2020/day20.txt").readText(), monster))
}

class Day20Vis {

    fun part1(input: String): Long {
        parseTiles(input).also { return multiplyCornerPieceNames(it) }
    }

    fun part2(input: String, monsterInput: String): Int {
        Tile(drawFullLake(topLeft(parseTiles(input).onEach { it.shrink() })))
            .also {
                Monster(monsterInput)
                    .also { m -> return it.noSharps() - rotAndFlipUntilMonstersFound(it, m) * m.noSharps() }
            }
    }

    private fun rotAndFlipUntilMonstersFound(tile: Tile, monster: Monster): Int {
        repeat(4) {
            val count = tile.rot().countSeaMonster(monster)
            if (count > 0) return count
        }
        tile.flip()
        repeat(4) {
            val count = tile.rot().countSeaMonster(monster)
            if (count > 0) return count
        }
        return 0
    }

    private fun drawFullLake(topLeft: Tile): String {
        val sb = StringBuilder()
        sb.append("Tile 1:\n")
        var bottomReached = false
        val leftMostTiles = ArrayList<Tile>()
        var cur = topLeft
        while (!bottomReached) {
            leftMostTiles.add(cur)
            when {
                cur.neighbors.containsKey(2) -> cur = cur.neighbors[2]!!
                else -> bottomReached = true
            }
        }
        sb.append(leftMostTiles.joinToString("\n") { drawTileEastOfIt(it) })
        return sb.toString()
    }

    private fun drawTileEastOfIt(tile: Tile): String {
        return tile.lines
            .mapIndexed { idx, line ->
                var cur = tile
                val sb = StringBuilder()
                sb.append(line.joinToString(""))
                while (cur.neighbors.containsKey(1)) {
                    cur.neighbors[1]!!.also {
                        cur = it
                        sb.append(cur.lines[idx].joinToString(""))
                    }
                }
                sb.toString()
            }.joinToString("\n")
    }

    private fun topLeft(tiles: List<Tile>): Tile {
        return tiles.filter {
            !it.neighbors.containsKey(0) && !it.neighbors.containsKey(3)
                    && it.neighbors.containsKey(1) && it.neighbors.containsKey(2)
        }[0]
    }

    private fun parseTiles(input: String): List<Tile> {
        return input
            .split("\n\n")
            .map { Tile(it) }
            .also { setNeighbors(it) }
    }

    private fun multiplyCornerPieceNames(tiles: List<Tile>): Long {
        return tiles.filter { it.neighbors.size == 2 }
            .map { it.id.toLong() }
            .reduce { acc, l -> acc * l }
    }

    private fun setNeighbors(tiles: List<Tile>) {
        findNeighbors(tiles[0], tiles)
    }

    private fun findNeighbors(self: Tile, tiles: List<Tile>) {
        self.fixed = true
        tiles.forEach { tile ->
            when {
                tile.id != self.id -> (0 until 4)
                    .asSequence()
                    .filterNot { self.neighbors.containsKey(it) }
                    .forEach { checkAsNeighbor(self, tile, it, tiles) }
            }
        }
    }

    private fun checkAsNeighbor(self: Tile, other: Tile, dir: Int, tiles: List<Tile>) {
        val oppDir = (dir + 2) % 4
        if (self.side(dir) == other.side(oppDir)) {
            setNeighborRelation(self, dir, other, oppDir)
            findNeighbors(other, tiles)
            return
        } else if (!other.fixed) {
            tryRotAndFlipToSetNeighbor(other, self, dir, oppDir, tiles)
        }
    }

    private fun tryRotAndFlipToSetNeighbor(other: Tile, self: Tile, dir: Int, oppDir: Int, tiles: List<Tile>) {
        repeat(3) {
            if (rotTestMatch(other, self, dir, oppDir, tiles)) return
        }
        other.flip()
        repeat(4) {
            if (rotTestMatch(other, self, dir, oppDir, tiles)) return
        }
    }

    private fun rotTestMatch(other: Tile, self: Tile, dir: Int, oppDir: Int, tiles: List<Tile>): Boolean {
        other.rot()
        if (self.side(dir) == other.side(oppDir)) {
            setNeighborRelation(self, dir, other, oppDir)
            findNeighbors(other, tiles)
            return true
        }
        return false
    }

    private fun setNeighborRelation(self: Tile, dir: Int, other: Tile, oppDir: Int) {
        self.neighbors[dir] = other
        other.neighbors[oppDir] = self
    }

    class Monster(input: String) {
        val chars = input.lines().map { it.toCharArray() }.toTypedArray()

        fun width(): Int = chars[0].size
        fun height(): Int = chars.size
        fun noSharps(): Int = chars.sumOf { it.count { c -> c == '#' } }
    }

    class Tile(input: String) {
        val id: Int
        var lines: Array<CharArray>
        var fixed = false
        val neighbors = HashMap<Int, Tile>()

        init {
            input.lines()
                .also {
                    id = it[0]
                        .substring(
                            it[0].indexOf(" ") + 1,
                            it[0].indexOf(":")
                        ).toInt()
                }
                .subList(1, input.lines().size)
                .map { it.toCharArray() }
                .toTypedArray()
                .also { lines = it }
        }

        fun noSharps(): Int = lines.sumOf { it.count { c -> c == '#' } }

        fun rot(): Tile {
            val rotated = Array(lines.size) { CharArray(lines.size) }
            for (rowIdx in lines.indices) {
                for (charIdx in lines.indices) {
                    rotated[charIdx][lines.size - 1 - rowIdx] = lines[rowIdx][charIdx]
                }
            }
            lines = rotated
            return this
        }

        private fun width(): Int = lines[0].size
        private fun height(): Int = lines.size

        fun countSeaMonster(monster: Monster): Int {
            var count = 0
            for (y in 0 until height() - monster.height()) {
                for (x in 0 until width() - monster.width()) {
                    if (isSeaMonster(y, x, monster)) {
                        count++
                    }
                }
            }
            return count
        }

        private fun isSeaMonster(lakeY: Int, lakeX: Int, monster: Monster): Boolean {
            for (y in 0 until monster.height()) {
                for (x in 0 until monster.width()) {
                    if (monster.chars[y][x] == '#'
                        && lines[lakeY + y][lakeX + x] != '#'
                    ) {
                        return false
                    }
                }
            }
            return true
        }

        fun shrink(): Tile {
            lines = lines.copyOfRange(1, lines.size - 1)
            lines.indices.forEach {
                lines[it] = lines[it].copyOfRange(1, lines[it].size - 1)
            }
            return this
        }

        fun flip(): Tile {
            lines.forEach { it.reverse() }
            return this
        }

        fun side(dir: Int): String {
            return when (dir) {
                0 -> lines[0].joinToString("")
                1 -> lines.map { it.last() }.joinToString("")
                2 -> lines[lines.size - 1].joinToString("")
                3 -> lines.map { it.first() }.joinToString("")
                else -> throw IllegalArgumentException("Dir must be in [0,3], dir=$dir")
            }
        }

        override fun toString(): String {
            return StringBuilder()
                .apply { append("Tile $id\n") }
                .also {
                    it.append(lines.joinToString("\n") { chars ->
                        chars.joinToString("")
                    })
                }
                .also { it.append("\nNeighbors${neighbors.keys.joinToString(",")}") }
                .toString()
        }
    }
}
