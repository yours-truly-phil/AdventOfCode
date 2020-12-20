package aoc2020

import java.io.File

fun main() {
    val day20 = Day20("files/2020/day20.txt")
    println(day20.part1())
    println(day20.part2())
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
//        findNeighborsStringCompare(images[0])
        images.forEach { println(it.toString()) }
        return images.filter { it.neighbors.size == 2 }.map { it.id.toLong() }.reduce { acc, l -> acc * l }
    }

    fun part2(): Long {
        val part1 = part1()
        if (part1 != 51214443014783L) {
            println("part 1 not working anymore: $part1")
        }

        drawGrid()

        return -1
    }

    private fun findNeighbors(self: Image) {
        self.rotateForbidden = true
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
        val otherSideIdx = r(side, 2)
        if (self.sides[side] == img.sides[otherSideIdx]) {
            // north and south fit, no rotation
            self.neighbors[side] = img
            img.neighbors[otherSideIdx] = self
            if (self.getSideAsString(side) != img.getSideAsString(otherSideIdx)) {
                println("no match sides $side and $otherSideIdx:\n$self\n${self.draw()}$img\n${img.draw()}")
            }
            findNeighbors(img)
            return
        } else if (!img.rotateForbidden) {
            for (i in 0 until 4) {
                if (self.sides[side] == img.rot90CW().sides[otherSideIdx]) {
                    findNeighbors(img)
                    return
                }
            }
            img.flipV()
//            img.flipH()
            for (i in 0 until 4) {
                if (self.sides[side] == img.rot90CW().sides[otherSideIdx]) {
                    findNeighbors(img)
                    return
                }
            }
        }
    }

    private fun findNeighborsStringCompare(self: Image) {
        self.rotateForbidden = true
        for (img in images) {
            if (img.id != self.id) {
                for (i in 0 until 4) {
                    if (!self.neighbors.containsKey(i)) {
                        checkAsNeighborStringCompare(self, img, i)
                    }
                }
            }
        }
    }

    private fun checkAsNeighborStringCompare(self: Image, img: Image, side: Int) {
        val otherSide = (side + 2) % 4
        val matchMe = self.getSideAsString(side)
        if (matchMe == img.getSideAsString(otherSide)) {
            self.neighbors[side] = img
            img.neighbors[otherSide] = self
        } else if (!img.rotateForbidden) {
            // we can rotate / flip img
            for (i in 0 until 4) {
                img.rot90CW()
                if (matchMe == img.getSideAsString(otherSide)) {
                    self.neighbors[side] = img
                    img.neighbors[otherSide] = self
                    findNeighborsStringCompare(img)
                }
            }
            // flip and rotate again
            img.flipV()
//            img.flipH()
            for (i in 0 until 4) {
                img.rot90CW()
                if (matchMe == img.getSideAsString(otherSide)) {
                    self.neighbors[side] = img
                    img.neighbors[otherSide] = self
                    findNeighborsStringCompare(img)
                }
            }
        }
    }

    fun r(cur: Int, steps: Int): Int {
        return (cur + steps) % 4
    }

    fun drawGrid() {
        val topLeft = findTopLeft()
        var cur = topLeft
        var bottomReached = false
        while (!bottomReached) {
            println(cur.drawSelfAndAllRight())
            if (cur.neighbors.containsKey(2)) {
                cur = cur.neighbors[2]!!
            } else {
                bottomReached = true
            }
        }
    }

    fun findTopLeft(): Image {
        return images.filter { it.neighbors.containsKey(1) && it.neighbors.containsKey(2) }[0]
    }

    class Image(str: String) {
        val id: Int
        var content: Array<CharArray>
        val sides = HashMap<Int, Int>()
        val neighbors = HashMap<Int, Image>()
        var rotateForbidden = false

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

        fun getSideAsString(i: Int): String {
            when (i) {
                0 -> {
                    // north
                    return content[0].joinToString("")
                }
                1 -> {
                    // east
                    var res = ""
                    for (row in content.indices) {
                        res += content[row][content.size - 1]
                    }
                    return res
                }
                2 -> {
                    // south
                    return content[content.size - 1].joinToString("")
                }
                3 -> {
                    // west
                    var res = ""
                    for (row in content.indices) {
                        res += content[row][0]
                    }
                    return res
                }
                else -> return ""
            }
        }

        fun flipH(): Image {
            val west = sides[3]!!
            sides[3] = sides[1]!!
            sides[1] = west
            sides[0] = sides[1]!!.biInv(content.size)
            sides[2] = sides[2]!!.biInv(content.size)
            for (i in content.indices) {
                content[i] = content[i].reversedArray()
            }
            return this
        }

        fun flipV(): Image {
            val north = sides[0]!!
            sides[0] = sides[2]!!
            sides[2] = north
            sides[1] = sides[1]!!.biInv(content.size)
            sides[3] = sides[3]!!.biInv(content.size)

            for (i in 0 until content.size / 2) {
                for (j in content[i].indices) {
                    val top = content[i][j]
                    content[i][j] = content[(content.size - 1) - i][j]
                    content[(content.size - 1) - i][j] = top
                }
            }

            return this
        }

        fun rot90CW(): Image {
            val zero = sides[0]!!
            // flip west to north
            sides[0] = sides[3]!!.biInv(content.size)
            sides[3] = sides[2]!!
            // flip east to south
            sides[2] = sides[1]!!.biInv(content.size)
            sides[1] = zero

            val rotated = Array(content.size) { CharArray(content.size) }
            for (rowIdx in content.indices) {
                for (charIdx in content.indices) {
                    rotated[charIdx][content.size - 1 - rowIdx] = content[rowIdx][charIdx]
                }
            }
            content = rotated
            return this
        }

        fun drawSelfAndAllRight(): String {
            val res = StringBuilder()
            for (rowIdx in content.indices) {
                for (el in content[rowIdx]) {
                    res.append(el)
                }
                var end = false
                var cur = this
                while (!end) {
                    if (cur.neighbors.containsKey(1)) {
                        cur = cur.neighbors[1]!!
                        for (el in cur.content[rowIdx]) {
                            res.append(el)
                        }
                    } else {
                        end = true
                    }
                }
                res.append("\n")
            }
            return res.toString()
        }

        fun draw(): String {
            val res = StringBuilder()
            for (row in content) {
                for (el in row) {
                    res.append(el)
                }
                res.append("\n")
            }
            return res.toString()
        }

        override fun toString(): String {
            return "$id: hasNeighbors=${neighbors.count()} " +
                    "north=${sides[0]!!.biString(content.size)} east=${sides[1]!!.biString(content.size)} " +
                    "south=${sides[2]!!.biString(content.size)} west=${sides[3]!!.biString(content.size)}"
        }
    }
}


fun Int.biString(length: Int): String {
    return String.format("%${length}s", this.toString(2)).replace(" ", "0")
}

fun Int.biInv(length: Int): Int {
    return this.biString(length).reversed().toInt(2)
}