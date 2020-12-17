package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay17()
}

fun runDay17() {
    val lines = File("aoc2020/day17.txt").readLines()
    val cycles = 6

    val start = System.nanoTime()
    val world = World(lines, cycles)
    val dur = System.nanoTime() - start
    println("parsed world in ${dur / 1000}µs")

//    printWorld(world)

    println("day17part1=${micros(Callable { day17part1(world, cycles) })}")
}

fun day17part1(world: World, cycles: Int): Int {
    for (i in 0 until cycles) {
        world.step()
//        printWorld(world)
    }
    return world.worldMap.entries.filter { it.value.state[world.idx] }.count()
}

private fun printWorld(world: World) {

    for (z in 0 until world.depth) {
        println("z$z")
        for (y in 0 until world.width) {
            for (x in 0 until world.width) {
                if (world.world[z][y][x].state[world.idx]) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        println()
    }
}

class World(val lines: List<String>, cycles: Int) {
    val world = ArrayList<ArrayList<ArrayList<Element>>>()
    val worldMap = HashMap<Point3D, Element>()

    val height: Int
    val width: Int
    val depth: Int

    var idx = 0

    init {
        val inHeight = lines.size
        val inWidth = lines[0].length
        val inDepth = 1
        // add a bit of extra space
        height = inHeight + 2 * cycles + 2
        width = inWidth + 2 * cycles + 2
        depth = inDepth + 2 * cycles + 2

        for (z in 0 until depth) {
            world.add(ArrayList())
            for (y in 0 until height) {
                world[z].add(ArrayList())
                for (x in 0 until width) {

                    if (z == depth / 2
                        && y in (cycles + 1)..(cycles + inHeight)
                        && x in (cycles + 1)..(cycles + inWidth)
                    ) {
                        val element = Element()
                        if (lines[y - (cycles + 1)][x - (cycles + 1)] == '#') {
                            element.state[idx] = true
                        }
                        worldMap[Point3D(z, y, x)] = element
                    } else {
                        worldMap[Point3D(z, y, x)] = Element()
                    }
                    world[z][y].add(worldMap[Point3D(z, y, x)]!!)
                }
            }
        }
    }

    fun step() {
        val nextIdx = (idx + 1) % 2
        for (entry in worldMap.entries) {
            val point = entry.key
            if (point.x in 1 until width - 1
                && point.y in 1 until height - 1
                && point.z in 1 until depth - 1
            ) {
                val count = countActiveNeighbors(point)
                entry.value.state[nextIdx] = ((!entry.value.state[idx] && count == 3)
                        || (entry.value.state[idx] && count in 2..3))
            }
        }
        idx = nextIdx
    }

    private fun countActiveNeighbors(p: Point3D): Int {
        var count = 0
        for (z in p.z - 1..p.z + 1) {
            for (y in p.y - 1..p.y + 1) {
                for (x in p.x - 1..p.x + 1) {
                    val neighbor = Point3D(z, y, x)
                    if (p != neighbor
                        && x in 0 until width
                        && y in 0 until height
                        && z in 0 until depth
                    ) {
                        if (worldMap[neighbor]!!.state[idx]) {
                            count++
                        }
                    }
                }
            }
        }
        return count
    }
}

data class Point3D(val z: Int, val y: Int, val x: Int) {}

class Element {
    var state = booleanArrayOf(false, false)
}