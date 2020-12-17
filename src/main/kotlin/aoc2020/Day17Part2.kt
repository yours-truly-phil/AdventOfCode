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
    private val world = ArrayList<ArrayList<ArrayList<ArrayList<Element>>>>()
    val worldMap = HashMap<Point4D, Element>()

    private val fourth: Int
    private val depth: Int
    private val height: Int
    private val width: Int

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

        (0 until fourth).forEach { w ->
            world.add(ArrayList())
            (0 until depth).forEach { z ->
                world[w].add(ArrayList())
                (0 until height).forEach { y ->
                    world[w][z].add(ArrayList())
                    (0 until width).forEach { x ->
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
        worldMap.entries.forEach {
            val point = it.key
            when {
                point.w in 1 until fourth - 1
                        && point.z in 1 until depth - 1
                        && point.y in 1 until height - 1
                        && point.x in 1 until width - 1 -> {
                    val count = countActiveNeighbors(point)
                    it.value.state[nextIdx] = ((!it.value.state[idx] && count == 3)
                            || (it.value.state[idx] && count in 2..3))
                }
            }
        }
        idx = nextIdx
    }

    private fun countActiveNeighbors(p: Point4D): Int {
        var count = 0
        (p.w - 1..p.w + 1).forEach { w ->
            (p.z - 1..p.z + 1).forEach { z ->
                (p.y - 1..p.y + 1).forEach { y ->
                    (p.x - 1..p.x + 1).forEach { x ->
                        val neighbor = Point4D(w, z, y, x)
                        when {
                            (p != neighbor
                                    && w in 0 until fourth
                                    && z in 0 until depth
                                    && y in 0 until height
                                    && x in 0 until width)
                                    && worldMap[neighbor]!!.state[idx] -> count++
                        }
                    }
                }
            }
        }
        return count
    }
}

data class Point4D(val w: Int, val z: Int, val y: Int, val x: Int)