package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay17Part2()
}

fun runDay17Part2() {
    val lines = File("aoc2020/day17.txt").readLines()
    val cycles = 6

    val start = System.nanoTime()
    val world = World4D(lines, cycles)
    val dur = System.nanoTime() - start
    println("parsed world in ${dur / 1000}µs")

    println("day17part2=${micros(Callable { day17part2(world, cycles) })}")
}

fun day17part2(world: World4D, cycles: Int): Int {
    for (i in 0 until cycles) {
        world.step()
    }
    return world.worldMap.entries.filter { it.value.state[world.idx] }.count()
}

class World4D(val lines: List<String>, cycles: Int) {
    val world = ArrayList<ArrayList<ArrayList<ArrayList<Element>>>>()
    val worldMap = HashMap<Point4D, Element>()

    val fourth: Int
    val depth: Int
    val height: Int
    val width: Int

    var idx = 0

    init {
        val inHeight = lines.size
        val inWidth = lines[0].length
        val inDepth = 1
        val inFourth = 1
        height = inHeight + 2 * cycles + 2
        width = inWidth + 2 * cycles + 2
        depth = inDepth + 2 * cycles + 2
        fourth = inFourth + 2 * cycles + 2

        for (w in 0 until fourth) {
            world.add(ArrayList())
            for (z in 0 until depth) {
                world[w].add(ArrayList())
                for (y in 0 until height) {
                    world[w][z].add(ArrayList())
                    for (x in 0 until width) {
                        if (w == fourth / 2 && z == depth / 2
                            && y in (cycles + 1)..(cycles + inHeight)
                            && x in (cycles + 1)..(cycles + inWidth)
                        ) {
                            val element = Element()
                            if (lines[y - (cycles + 1)][x - (cycles + 1)] == '#') {
                                element.state[idx] = true
                            }
                            worldMap[Point4D(w, z, y, x)] = element
                        } else {
                            worldMap[Point4D(w, z, y, x)] = Element()
                        }
                        world[w][z][y].add(worldMap[Point4D(w, z, y, x)]!!)
                    }
                }
            }
        }
    }

    fun step() {
        val nextIdx = (idx + 1) % 2
        for (entry in worldMap.entries) {
            val point = entry.key
            if (point.w in 1 until fourth - 1
                && point.z in 1 until depth - 1
                && point.y in 1 until height - 1
                && point.x in 1 until width - 1
            ) {
                val count = countActiveNeighbors(point)
                entry.value.state[nextIdx] = ((!entry.value.state[idx] && count == 3)
                        || (entry.value.state[idx] && count in 2..3))
            }
        }
        idx = nextIdx
    }

    private fun countActiveNeighbors(p: Point4D): Int {
        var count = 0
        for (w in p.w - 1..p.w + 1) {
            for (z in p.z - 1..p.z + 1) {
                for (y in p.y - 1..p.y + 1) {
                    for (x in p.x - 1..p.x + 1) {
                        val neighbor = Point4D(w, z, y, x)
                        if (p != neighbor
                            && w in 0 until fourth
                            && z in 0 until depth
                            && y in 0 until height
                            && x in 0 until width
                        ) {
                            if (worldMap[neighbor]!!.state[idx]) {
                                count++
                            }
                        }
                    }
                }
            }
        }
        return count
    }
}

class Point4D(val w: Int, val z: Int, val y: Int, val x: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point4D

        if (w != other.w) return false
        if (z != other.z) return false
        if (y != other.y) return false
        if (x != other.x) return false

        return true
    }

    override fun hashCode(): Int {
        var result = w
        result = 31 * result + z
        result = 31 * result + y
        result = 31 * result + x
        return result
    }

    override fun toString(): String {
        return "Point4D(w=$w, z=$z, y=$y, x=$x)"
    }
}