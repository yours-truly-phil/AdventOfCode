package aoc2016

fun main() {
    Day13().apply {
        println("sample=${part1(Day13.P(7, 4), 10)}")
        println("part1=${part1(Day13.P(31, 39), 1352)}")
        println("part2=${part2(1352, 50)}")
    }
}

class Day13 {
    fun part1(target: P, num: Int): Int {
        return numStepsTo(target, num)
    }

    fun part2(num: Int, steps: Int): Int {
        return totalInRange(num, steps)
    }

    private fun totalInRange(num: Int, steps: Int): Int {
        val paths = HashMap<P, Int>().apply { this[P(1, 1)] = 0 }
        while (paths.filter { it.value < steps && !it.key.neighborsChecked }.isNotEmpty()) {
            val p = paths.filter { !it.key.neighborsChecked && it.value < steps }.minByOrNull { it.value }!!
            foo(p, num, paths)
        }
        return printGrid(paths, steps)
    }

    private fun printGrid(grid: HashMap<P, Int>, steps: Int): Int {
        var count = 0
        for (y in 0..grid.maxOf { it.key.y }) {
            for (x in 0..grid.maxOf { it.key.x }) {
                if (grid.containsKey(P(x, y))) {
                    print("%3d".format(grid[P(x, y)]))
                    if (grid[P(x, y)]!! <= steps) {
                        print("*")
                        count++
                    } else {
                        print(" ")
                    }
                } else {
                    print("### ")
                }
            }
            println()
        }
        return count
    }

    private fun numStepsTo(target: P, num: Int): Int {
        val paths = HashMap<P, Int>().apply { this[P(1, 1)] = 0 }
        while (!paths.containsKey(target)) {
            val p = paths.filter { !it.key.neighborsChecked }.minByOrNull { it.value }!!
            foo(p, num, paths)
        }
        return paths[target]!!
    }

    private fun foo(
        p: Map.Entry<P, Int>, num: Int, paths: HashMap<P, Int>
    ) {
        val neighbors = arrayOf(p.key.up(), p.key.down(), p.key.left(), p.key.right())
        for (neighbor in neighbors) {
            if (isOpen(neighbor, num) && neighbor.x >= 0 && neighbor.y >= 0) {
                if (paths.containsKey(neighbor)) {
                    if (paths[neighbor]!! > p.value + 1) {
                        paths[neighbor] = p.value + 1
                    }
                }
                paths.computeIfAbsent(neighbor) { p.value + 1 }
            }
        }
        p.key.neighborsChecked = true
    }

    private fun isOpen(p: P, num: Int): Boolean {
        return (magicNum(p) + num).toString(2).count { it != '0' } % 2 == 0
    }

    private fun magicNum(p: P): Long {
        return p.x * p.x + 3 * p.x + 2 * p.x * p.y + p.y + p.y * p.y
    }

    class P(val x: Long, val y: Long) {
        var neighborsChecked = false

        fun up() = P(x, y + 1)
        fun down() = P(x, y - 1)
        fun left() = P(x - 1, y)
        fun right() = P(x + 1, y)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as P

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x.hashCode()
            result = 31 * result + y.hashCode()
            return result
        }

        override fun toString(): String {
            return "P(x=$x y=$y $neighborsChecked)"
        }
    }
}