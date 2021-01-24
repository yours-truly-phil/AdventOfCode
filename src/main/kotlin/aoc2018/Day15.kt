package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day15 {
    fun outcome(input: String): Int {
        val (map, units) = parse(input)
        var oldState = printState(map, units)
        println(oldState)

        var rounds = 0
        while (units.filter { it.type == 'E' }.any { !it.isDead() } &&
            units.filter { it.type == 'G' }.any { !it.isDead() }) {
//            rounds++
            units.sort()
            units.forEach {
                when {
                    !it.isDead() -> {
                        it.step(map, units)
                    }
                }
            }
            val newState = printState(map, units)
            if (newState != oldState) {
                rounds++
            }
            oldState = newState
            println("Round $rounds")
            println(newState)
        }
        rounds--
        return rounds * units.filter { !it.isDead() }.sumOf { it.hp }
    }

    private fun parse(input: String): Pair<Array<CharArray>, ArrayList<Entity>> {
        val lines = input.lines()
        val height = lines.size
        val width = lines.maxOf { it.length }
        val map = Array(height) { CharArray(width) }
        val units = ArrayList<Entity>()
        lines.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                when (c) {
                    '#' -> map[y][x] = '#'
                    '.' -> map[y][x] = '.'
                    'G' -> {
                        map[y][x] = '.'
                        units += Entity(this, V2i(x, y), 200, 3, 'G')
                    }
                    'E' -> {
                        map[y][x] = '.'
                        units += Entity(this, V2i(x, y), 200, 3, 'E')
                    }
                }
            }
        }
        return Pair(map, units)
    }

    private fun printState(map: Array<CharArray>, units: ArrayList<Entity>): String {
        val sb = StringBuilder()
        val unitLocs = units.filter { !it.isDead() }.map { it.loc to it }.toMap()
        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                when {
                    c == '#' -> sb.append('#')
                    unitLocs.containsKey(V2i(x, y)) -> sb.append(unitLocs[V2i(x, y)]!!.type)
                    else -> sb.append('.')
                }
            }
            sb.append("\n")
        }
        units.filter { !it.isDead() }.forEach { sb.append("${it.type}(${it.hp})\n") }
        return sb.toString()
    }

    class Entity(val p: Day15, val loc: V2i, var hp: Int, var ap: Int, val type: Char) : Comparable<Entity> {
        fun isDead(): Boolean = hp <= 0

        fun step(map: Array<CharArray>, units: List<Entity>) {
            val others = units.filter { it != this }.toSet()

            val enemyLocs = others.filter { it.type == if (type == 'G') 'E' else 'G' }
                .filter { !it.isDead() }
                .map { it.loc to it }.toMap()

            move(others, map, enemyLocs)

            var minHp = Int.MAX_VALUE
            var minHpEnemy: Entity? = null
            if (enemyLocs.containsKey(loc.up())) {
                val enemy = enemyLocs[loc.up()]!!
                if (enemy.hp < minHp) {
                    minHp = enemy.hp
                    minHpEnemy = enemy
                }
            }
            if (enemyLocs.containsKey(loc.left())) {
                val enemy = enemyLocs[loc.left()]!!
                if (enemy.hp < minHp) {
                    minHp = enemy.hp
                    minHpEnemy = enemy
                }
            }
            if (enemyLocs.containsKey(loc.right())) {
                val enemy = enemyLocs[loc.right()]!!
                if (enemy.hp < minHp) {
                    minHp = enemy.hp
                    minHpEnemy = enemy
                }
            }
            if (enemyLocs.containsKey(loc.down())) {
                val enemy = enemyLocs[loc.down()]!!
                if (enemy.hp < minHp) {
                    minHpEnemy = enemy
                }
            }

            if (minHpEnemy == null) {
//                move(others, map, enemyLocs)
            } else {
                minHpEnemy.hp -= ap
            }
        }

        private fun move(
            others: Set<Entity>,
            map: Array<CharArray>,
            enemyLocs: Map<V2i, Entity>,
        ) {
            val unavailable = initUnavailableLocs(others, map)
            val paths = ArrayDeque<MutableList<V2i>>()
            val origin = ArrayList<V2i>().also { it += loc }
            paths.addLast(origin)
            while (paths.isNotEmpty()) {
                val path = paths.removeFirst()
                val loc = path.last()

                if (enemyLocs.containsKey(loc.up()) ||
                    enemyLocs.containsKey(loc.left()) ||
                    enemyLocs.containsKey(loc.right()) ||
                    enemyLocs.containsKey(loc.down())
                ) {
                    var goto = path[0]
                    if (path.size > 1) goto = path[1]
                    this.loc.x = goto.x
                    this.loc.y = goto.y
                    break
                }

                addPath(unavailable, loc.up(), path, paths)
                addPath(unavailable, loc.left(), path, paths)
                addPath(unavailable, loc.right(), path, paths)
                addPath(unavailable, loc.down(), path, paths)
            }
        }

        private fun initUnavailableLocs(
            others: Set<Entity>,
            map: Array<CharArray>,
        ): HashSet<V2i> {
            val unavailable = HashSet<V2i>().also {
                it += loc
                it.addAll(others.filter { entity -> !entity.isDead() }.map { entity -> entity.loc })
            }
            map.forEachIndexed { y, row ->
                row.forEachIndexed { x, c ->
                    when (c) {
                        '#' -> unavailable += V2i(x, y)
                    }
                }
            }
            return unavailable
        }

        private fun addPath(
            unavailable: HashSet<V2i>,
            to: V2i,
            pathFrom: MutableList<V2i>,
            paths: ArrayDeque<MutableList<V2i>>,
        ) {
            if (!unavailable.contains(to)) {
                val newPath = ArrayList<V2i>().also { it.addAll(pathFrom) }
                    .also { it.add(to) }
                paths.addLast(newPath)
                unavailable.add(to)
            }
        }

        override fun toString(): String = "$type(loc=$loc, hp=$hp, ap=$ap)"
        override fun compareTo(other: Entity): Int = loc.compareTo(other.loc)
    }

    data class V2i(var x: Int, var y: Int) : Comparable<V2i> {

        fun up(): V2i = V2i(x, y - 1)
        fun down(): V2i = V2i(x, y + 1)
        fun left(): V2i = V2i(x - 1, y)
        fun right(): V2i = V2i(x + 1, y)

        override fun compareTo(other: V2i): Int =
            this.toString().compareTo(other.toString())

        override fun toString(): String =
            "(${y.toString().padStart(2, '0')},${x.toString().padStart(2, '0')})"
    }

    @Test
    fun sample() {
//        assertEquals(27730, outcome("#######\n" +
//                "#.G...#\n" +
//                "#...EG#\n" +
//                "#.#.#G#\n" +
//                "#..G#E#\n" +
//                "#.....#\n" +
//                "#######"))
        assertEquals(39514, outcome("#######\n" +
                "#E..EG#\n" +
                "#.#G.E#\n" +
                "#E.##E#\n" +
                "#G..#.#\n" +
                "#..E#.#\n" +
                "#######"))
        assertEquals(36334, outcome("#######\n" +
                "#G..#E#\n" +
                "#E#E.E#\n" +
                "#G.##.#\n" +
                "#...#E#\n" +
                "#...E.#\n" +
                "#######"))
    }

    @Test
    fun part1() {
        assertEquals(228730, outcome(File("files/2018/day15.txt").readText()))
    }
}