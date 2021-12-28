package aoc2018

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11 {
    @Suppress("SameParameterValue")
    private fun posSquareMaxPower(serial: Int, gridSize: Int, squareSize: Int): String {
        val powerLvls = computePowerLvls(gridSize, serial)

        val (maxIdx, maxPower) = maxPowerForSquareSize(powerLvls, squareSize)
        println("idx: $maxIdx total power: $maxPower")
        return maxIdx
    }

    private fun maxPowerForSquareSize(
        powerLvls: Array<IntArray>,
        squareSize: Int,
    ): Pair<String, Int> {
        var maxIdx = "-1,-1"
        var maxPower = Int.MIN_VALUE
        for (x in 0..powerLvls.size - squareSize) {
            for (y in 0..powerLvls[x].size - squareSize) {
                var squareLvl = 0
                for (xIdx in x until x + squareSize) {
                    for (yIdx in y until y + squareSize) {
                        squareLvl += powerLvls[yIdx][xIdx]
                    }
                }
                if (squareLvl > maxPower) {
                    maxIdx = "${y + 1},${x + 1}"
                    maxPower = squareLvl
                }
            }
        }
        return Pair(maxIdx, maxPower)
    }

    @Suppress("SameParameterValue")
    private fun maxSquare(serial: Int, gridSize: Int): String {
        val powerLvls = computePowerLvls(gridSize, serial)

        var maxIdx = "-1,-1"
        var maxPower = Int.MIN_VALUE
        var maxSize = -1
        for (size in 1..50) {
            val (idx, power) = maxPowerForSquareSize(powerLvls, size)
            if (power > maxPower) {
                maxSize = size
                maxPower = power
                maxIdx = idx
            }
        }

        return "$maxIdx,$maxSize"
    }

    private fun computePowerLvls(gridSize: Int, serial: Int): Array<IntArray> {
        val powerLvls = Array(gridSize) { IntArray(gridSize) }
        for (x in 1..gridSize) {
            for (y in 1..gridSize) {
                powerLvls[x - 1][y - 1] = powerLvl(V2i(x, y), serial)
            }
        }
        return powerLvls
    }

    private fun powerLvl(pos: V2i, serial: Int): Int {
        val num = (((pos.x + 10) * pos.y + serial) * (pos.x + 10)).toString()
        return when {
            num.length < 3 -> -5
            else -> num[num.length - 3].toString().toInt() - 5
        }
    }

    data class V2i(val x: Int, val y: Int)

    @Test
    fun calculatePowerLvlAtPos() {
        assertEquals(4, powerLvl(V2i(3, 5), 8))
        assertEquals(-5, powerLvl(V2i(122, 79), 57))
        assertEquals(0, powerLvl(V2i(217, 196), 39))
        assertEquals(4, powerLvl(V2i(101, 153), 71))
    }

    @Test
    fun maxPowerIn3x3Field() {
        assertEquals("33,45", posSquareMaxPower(18, 300, 3))
        assertEquals("21,61", posSquareMaxPower(42, 300, 3))
    }

    @Test
    fun maxSquare() {
        assertEquals("90,269,16", maxSquare(18, 300))
        assertEquals("232,251,12", maxSquare(42, 300))
    }

    @Test
    fun part1() {
        assertEquals("235,87", posSquareMaxPower(8199, 300, 3))
    }

    @Test
    fun part2() {
        assertEquals("234,272,18", maxSquare(8199, 300))
    }
}