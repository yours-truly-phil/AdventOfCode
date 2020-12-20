package aoc2020

import java.io.File

fun main() {
    val day20 = Day20("files/2020/day20.txt")
    println(day20.part1())
}

class Day20(path: String) {

    val images: List<Image>
    val imageMap: Map<Int, Image>

    init {
        val paragraphs = File(path).readText().split("\n\n")
        images = paragraphs.map { Image(it) }
        imageMap = images.map { it.id to it }.toMap()
    }

    fun part1(): Long {
        findNeighbors(images[0])
        images.forEach { println(it.toString()) }
        return images.filter { it.neighbors.size == 2 }.map { it.id.toLong() }.reduce { acc, l -> acc * l }
    }

    fun findNeighbors(self: Image) {
        self.posFound = true
        for (img in images) {
            if (img.id != self.id) {
                for (i in 0 until 4) {
                    if (!self.neighbors.containsKey(i)) {
                        checkAsNeighbor(self, img, i)
                    }
                }
            }
        }
    }

    private fun checkAsNeighbor(self: Image, img: Image, side: Int) {
        val other = r(side, 2)
        if (self.sides[side] == img.sides[other]) {
            // north and south fit, no rotation
            self.neighbors[side] = img
            img.neighbors[other] = self
            findNeighbors(img)
        } else if (!img.posFound) {
            for (i in 0 until 4) {
                if (self.sides[side] == img.rot90CW().sides[other]) {
                    findNeighbors(img)
                }
            }
            img.flipH()
            for (i in 0 until 4) {
                if (self.sides[side] == img.rot90CW().sides[other]) {
                    findNeighbors(img)
                }
            }
        }
    }

    fun r(cur: Int, steps: Int): Int {
        return (cur + steps) % 4
    }

    fun part2(): Long {
        return -1
    }

    class Image(str: String) {
        val id: Int
        val content: Array<CharArray>
        val sides = HashMap<Int, Int>()
        val neighbors = HashMap<Int, Image>()
        var posFound = false

        init {
            val parts = str.split("\n")
            id = parts[0].substring(5, parts[0].length - 1).toInt()
            content = parts.subList(1, parts.size).map { it.toCharArray() }.toTypedArray()

            // north
            var north = 0
            var south = 0
            var east = 0
            var west = 0
            for (i in content[0].indices) {
                north = north shl 1
                east = east shl 1
                south = south shl 1
                west = west shl 1
                when (content[0][i]) {
                    '#' -> north += 1
                }
                when (content[i][content[i].size - 1]) {
                    '#' -> east += 1
                }
                when (content[content.size - 1][i]) {
                    '#' -> south += 1
                }
                when (content[i][0]) {
                    '#' -> west += 1
                }
            }
            sides[0] = north
            sides[1] = east
            sides[2] = south
            sides[3] = west
        }

        fun getSideValues(): IntArray {
            return sides.values.toIntArray()
        }

        fun flipV(): Image {
            sides[0] = sides[0]!!.biInv()
            sides[2] = sides[2]!!.biInv()
            val west = sides[3]!!
            sides[3] = sides[1]!!
            sides[1] = west
            return this
        }

        fun flipH(): Image {
            val north = sides[0]!!
            sides[0] = sides[2]!!
            sides[2] = north
            sides[1] = sides[1]!!.biInv()
            sides[3] = sides[3]!!.biInv()
            return this
        }

        fun rot90CW(): Image {
            val zero = sides[0]!!
            // flip west to north
            sides[0] = sides[3]!!.biInv()
            sides[3] = sides[2]!!
            // flip east to south
            sides[2] = sides[1]!!.biInv()
            sides[1] = zero
            return this
        }

        override fun toString(): String {
            return "$id: hasNeighbors=${neighbors.count()} " +
                    "north=${sides[0]!!.biString()} east=${sides[1]!!.biString()} " +
                    "south=${sides[2]!!.biString()} west=${sides[3]!!.biString()}"
        }
    }
}


fun Int.biString(): String {
    return String.format("%10s", this.toString(2)).replace(" ", "0")
}

fun Int.biInv(): Int {
    return this.biString().reversed().toInt(2)
}