package aoc2015

fun main() {
    Day25().apply { println(part1(2947, 3029)) }
}

class Day25 {
    fun part1(row: Int, col: Int): Long {
        var y = 1
        var x = 1
        var num = 20151125L
        while (y != row || x != col) {
            if (y == 1) {
                y = x + 1
                x = 1
            } else {
                y--
                x++
            }
            num *= 252533
            num %= 33554393
        }
        return num
    }
}