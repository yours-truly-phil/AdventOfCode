package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day17 {
    private fun retainedWater(input: String):Int {
        val clays = input.lines().map { parseLine(it) }.flatten().toHashSet()
        val spring = V2i(500, 0)

        val env = clays.map { it to '#' }.toMap().toMutableMap()
//        env[spring] = '+'

//        val springs = ArrayDeque<V2i>().also { it.add(spring) }
        val springs = ArrayList<V2i>().also { it.add(spring) }
        val maxY = env.maxOf { it.key.y }
        val minY = env.minOf { it.key.y }
        println("minY=$minY")
        val flowing = HashSet<V2i>()
        while (springs.isNotEmpty()) {
            springs.sorted()
            var source: V2i
            do {
                source = springs.removeFirst()
            } while (env.containsKey(source))

            var springDone = false
            while (!springDone) {
                var bottom = source.y
                while (!env.containsKey(V2i(source.x, bottom + 1))) {
                    flowing.add(V2i(source.x, bottom + 1))
                    bottom++
                    if (bottom > maxY) break
                }
                if (bottom == source.y) {

                    var overflowing = false
                    var top = source.y
                    while (!overflowing) {
                        flowing.add(V2i(source.x, top))
                        var left = source.x
                        var right = source.x
                        while (!env.containsKey(V2i(left - 1, top))) {
                            flowing.add(V2i(left - 1, top))
                            left--
                            if (!env.containsKey(V2i(left, top + 1))) {
                                overflowing = true
//                                springs.addLast(V2i(left, top))
                                springs.add(V2i(left, top))
                                break
                            }
                        }
                        while (!env.containsKey(V2i(right + 1, top))) {
                            flowing.add(V2i(right + 1, top))
                            right++
                            if (!env.containsKey(V2i(right, top + 1))) {
                                overflowing = true
//                                springs.addLast(V2i(right, top))
                                springs.add(V2i(right, top))
                                break
                            }
                        }
                        if (!overflowing) {
                            for (x in left..right) {
                                env[V2i(x, top)] = '~'
                            }
                            top--
                        }
                    }
                    break
                }
                if (bottom > maxY) break

                var left = source.x
                var right = source.x
                while (!env.containsKey(V2i(left - 1, bottom))) {
                    flowing.add(V2i(left - 1, bottom))
                    left--
                    if (!env.containsKey(V2i(left, bottom + 1))) {
                        springDone = true
//                        springs.addLast(V2i(left, bottom))
                        springs.add(V2i(left, bottom))
                        break
                    }
                }
                while (!env.containsKey(V2i(right + 1, bottom))) {
                    flowing.add(V2i(right + 1, bottom))
                    right++
                    if (!env.containsKey(V2i(right, bottom + 1))) {
                        springDone = true
//                        springs.addLast(V2i(right, bottom))
                        springs.add(V2i(right, bottom))
                        break
                    }
                }
                if (!springDone) {
                    for (x in left..right) {
                        env[V2i(x, bottom)] = '~'
                    }
                }
//                println("flowing=${flowing.size}")
//                println(envToString(env, flowing))
            }
        }

        println("flowing=${flowing.filter { it.y in minY..maxY }.count()}")
        println("retained=${env.filter { it.value == '~' }.count()}")
        println(envToString(env, flowing.filter { it.y <= maxY }.toSet()))

        return env.filter { it.value == '~' }.count()
    }
    private fun countTilesReachWater(input: String): Int {
        val clays = input.lines().map { parseLine(it) }.flatten().toHashSet()
        val spring = V2i(500, 0)

        val env = clays.map { it to '#' }.toMap().toMutableMap()
//        env[spring] = '+'

//        val springs = ArrayDeque<V2i>().also { it.add(spring) }
        val springs = ArrayList<V2i>().also { it.add(spring) }
        val maxY = env.maxOf { it.key.y }
        val minY = env.minOf { it.key.y }
        println("minY=$minY")
        val flowing = HashSet<V2i>()
        while (springs.isNotEmpty()) {
            springs.sorted()
            var source: V2i
            do {
                source = springs.removeFirst()
            } while (env.containsKey(source))

            var springDone = false
            while (!springDone) {
                var bottom = source.y
                while (!env.containsKey(V2i(source.x, bottom + 1))) {
                    flowing.add(V2i(source.x, bottom + 1))
                    bottom++
                    if (bottom > maxY) break
                }
                if (bottom == source.y) {

                    var overflowing = false
                    var top = source.y
                    while (!overflowing) {
                        flowing.add(V2i(source.x, top))
                        var left = source.x
                        var right = source.x
                        while (!env.containsKey(V2i(left - 1, top))) {
                            flowing.add(V2i(left - 1, top))
                            left--
                            if (!env.containsKey(V2i(left, top + 1))) {
                                overflowing = true
//                                springs.addLast(V2i(left, top))
                                springs.add(V2i(left, top))
                                break
                            }
                        }
                        while (!env.containsKey(V2i(right + 1, top))) {
                            flowing.add(V2i(right + 1, top))
                            right++
                            if (!env.containsKey(V2i(right, top + 1))) {
                                overflowing = true
//                                springs.addLast(V2i(right, top))
                                springs.add(V2i(right, top))
                                break
                            }
                        }
                        if (!overflowing) {
                            for (x in left..right) {
                                env[V2i(x, top)] = '~'
                            }
                            top--
                        }
                    }
                    break
                }
                if (bottom > maxY) break

                var left = source.x
                var right = source.x
                while (!env.containsKey(V2i(left - 1, bottom))) {
                    flowing.add(V2i(left - 1, bottom))
                    left--
                    if (!env.containsKey(V2i(left, bottom + 1))) {
                        springDone = true
//                        springs.addLast(V2i(left, bottom))
                        springs.add(V2i(left, bottom))
                        break
                    }
                }
                while (!env.containsKey(V2i(right + 1, bottom))) {
                    flowing.add(V2i(right + 1, bottom))
                    right++
                    if (!env.containsKey(V2i(right, bottom + 1))) {
                        springDone = true
//                        springs.addLast(V2i(right, bottom))
                        springs.add(V2i(right, bottom))
                        break
                    }
                }
                if (!springDone) {
                    for (x in left..right) {
                        env[V2i(x, bottom)] = '~'
                    }
                }
//                println("flowing=${flowing.size}")
//                println(envToString(env, flowing))
            }
        }

        println("flowing=${flowing.filter { it.y in minY..maxY }.count()}")
        println(envToString(env, flowing.filter { it.y <= maxY }.toSet()))

        return flowing.filter { it.y in minY..maxY }.count()
    }

    private fun envToString(env: MutableMap<V2i, Char>, flowing: Set<V2i>): String {
        val sb = StringBuilder()
        val min = V2i(env.minOf { it.key.x } - 1, env.minOf { it.key.y })
        val max = V2i(env.maxOf { it.key.x } + 1, env.maxOf { it.key.y })
        for (y in min.y..max.y) {
            for (x in min.x..max.x) {
                val loc = V2i(x, y)
                when {
                    env.containsKey(loc) -> sb.append(env[loc])
                    flowing.contains(loc) -> sb.append('|')
                    else -> sb.append('.')
                }
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    fun parseLine(line: String): List<V2i> {
        val xy = line.split(", ")
        if (xy[0].startsWith("x")) {
            val xe = xy[0]
            val ye = xy[1]
            val x = xe.split("=")[1].toInt()
            val yBounds = ye.split("=")[1].split("..")
            val yLow = yBounds[0].toInt()
            val yHigh = yBounds[1].toInt()
            val res = ArrayList<V2i>()
            for (y in yLow..yHigh) {
                res += V2i(x, y)
            }
            return res
        } else {
            val ye = xy[0]
            val xe = xy[1]
            val y = ye.split("=")[1].toInt()
            val xBounds = xe.split("=")[1].split("..")
            val xLow = xBounds[0].toInt()
            val xHigh = xBounds[1].toInt()
            val res = ArrayList<V2i>()
            for (x in xLow..xHigh) {
                res += V2i(x, y)
            }
            return res
        }
    }

    data class V2i(val x: Int, val y: Int) : Comparable<V2i> {
        override fun compareTo(other: V2i): Int {
            return y.compareTo(other.y)
        }

    }

    @Test
    fun sample() {
        assertEquals(57, countTilesReachWater("x=495, y=2..7\n" +
                "y=7, x=495..501\n" +
                "x=501, y=3..7\n" +
                "x=498, y=2..4\n" +
                "x=506, y=1..2\n" +
                "x=498, y=10..13\n" +
                "x=504, y=10..13\n" +
                "y=13, x=498..504"))
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(29, retainedWater("x=495, y=2..7\n" +
                "y=7, x=495..501\n" +
                "x=501, y=3..7\n" +
                "x=498, y=2..4\n" +
                "x=506, y=1..2\n" +
                "x=498, y=10..13\n" +
                "x=504, y=10..13\n" +
                "y=13, x=498..504"))
    }

    @Test
    fun urnInUrn() {
        assertEquals(80, countTilesReachWater("x=495, y=2..10\n" +
                "x=505, y=1..10\n" +
                "y=10, x=495..505\n" +
                "x=498, y=4..8\n" +
                "x=502, y=5..8\n" +
                "y=8, x=498..502"))
    }

    @Test
    fun part1() {
        assertEquals(30380, countTilesReachWater(File("files/2018/day17.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(25068, retainedWater(File("files/2018/day17.txt").readText()))
    }
}