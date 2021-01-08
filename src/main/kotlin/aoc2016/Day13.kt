package aoc2016

fun main() {
    Day13().apply {
        println("sample=${part1(Day13.P(7, 4), 10)}")
        println("part1=${part1(Day13.P(31, 39), 1352)}")
    }
}

class Day13 {
    fun part1(target: P, num: Int): Int {
        return numStepsTo(target, num)
    }

    fun numStepsTo(target: P, num: Int): Int {
        val paths = HashMap<P, Int>().apply { this[P(1, 1)] = 0 }
        while (!paths.containsKey(target)) {
            val p = paths.filter { !it.key.neighborsChecked }.minByOrNull { it.value }!!
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
        return paths[target]!!
    }

    fun isOpen(p: P, num: Int): Boolean {
        return (magicNum(p) + num)
            .toString(2)
            .count { it != '0' } % 2 == 0
    }

    fun magicNum(p: P): Long {
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