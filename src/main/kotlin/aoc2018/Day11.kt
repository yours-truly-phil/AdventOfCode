package aoc2018

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11 {
    private fun posSquareMaxPower(serial: Int, gridSize: Int, squareSize: Int): String {
        val powerLvls = computePowerLvls(gridSize, serial)

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
        println("idx: $maxIdx total power: $maxPower")
        return maxIdx
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

    fun powerLvl(pos: V2i, serial: Int): Int {
        val num = (((pos.x + 10) * pos.y + serial) * (pos.x + 10)).toString()
        return when {
            num.length < 3 -> -5
            else -> num[num.length - 3].toString().toInt() - 5
        }
    }

    data class V2i(val x: Int, val y: Int)

    @Test
    fun `calculate power lvl at pos`() {
        assertEquals(4, powerLvl(V2i(3, 5), 8))
        assertEquals(-5, powerLvl(V2i(122, 79), 57))
        assertEquals(0, powerLvl(V2i(217, 196), 39))
        assertEquals(4, powerLvl(V2i(101, 153), 71))
    }

    @Test
    fun `max power in 3x3 field`() {
        assertEquals("33,45", posSquareMaxPower(18, 300, 3))
        assertEquals("21,61", posSquareMaxPower(42, 300, 3))
    }

    @Test
    fun part1() {
        assertEquals("235,87", posSquareMaxPower(8199, 300, 3))
    }
}