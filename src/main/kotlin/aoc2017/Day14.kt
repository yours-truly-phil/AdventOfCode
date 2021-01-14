package aoc2017

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14 {
    val day10 = Day10()
    private fun usedSquares(input: String): Int =
        constructGrid(input).map { it.filter { c -> c == '1' }.count() }.sum()

    fun hashToBinary(s: String): String =
        day10.knotHash(s).toBigInteger(16).toString(2).padStart(128, '0')

    fun countRegions(input: String): Int {
        val grid = constructGrid(input)
            .mapIndexed { iRow, row ->
                row.mapIndexed { iCol, c -> Pos(iRow, iCol, c == '1') }.toTypedArray()
            }.toTypedArray().flatten().toHashSet()
        val checked = HashSet<Pos>()
        var count = 0
        for (cell in grid) {
            if (cell.square && !checked.contains(cell)) {
                count++
                island(cell, grid, checked)
            }
        }
        return count
    }

    private fun island(cell: Pos, grid: HashSet<Pos>, checked: HashSet<Pos>) {
        if (!cell.square) return

        checked.add(cell)
        val up = Pos(cell.x, cell.y + 1, true)
        val down = Pos(cell.x, cell.y - 1, true)
        val left = Pos(cell.x - 1, cell.y, true)
        val right = Pos(cell.x + 1, cell.y, true)
        if (grid.contains(up) && !checked.contains(up)) island(up, grid, checked)
        if (grid.contains(down) && !checked.contains(down)) island(down, grid, checked)
        if (grid.contains(left) && !checked.contains(left)) island(left, grid, checked)
        if (grid.contains(right) && !checked.contains(right)) island(right, grid, checked)
    }

    data class Pos(val x: Int, val y: Int, val square: Boolean)

    fun constructGrid(input: String): ArrayList<String> {
        return ArrayList<String>()
            .apply { (0 until 128).mapTo(this) { hashToBinary("$input-$it") } }
    }

    @Test
    fun `hash to binary`() {
        assertEquals("1101010011110111" +
                "0110101111011100" +
                "1011111110000011" +
                "1000111110000100" +
                "0001011011001100" +
                "1111101010001011" +
                "1100011011010001" +
                "1111100111100110", hashToBinary("flqrgnkx-0"))
    }

    @Test
    fun sample() {
        assertEquals(8108, usedSquares("flqrgnkx"))
    }

    @Test
    fun part1() {
        assertEquals(8316, usedSquares("ljoxqyyw"))
    }

    @Test
    fun `part 2 sample()`() {
        assertEquals(1242, countRegions("flqrgnkx"))
    }

    @Test
    fun part2() {
        assertEquals(1074, countRegions("ljoxqyyw"))
    }
}