package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day25 {
    @Suppress("DuplicatedCode")
    private fun solve(input: String): Int {
        val map = input.lines().map { it.toCharArray() }.toTypedArray()
        val tmp = Array(map.size) { CharArray(map[0].size) }
        var count = 0
        var changed = true
        while (changed) {
            changed = false
            for (y in map.indices) {
                var x = 0
                while (x in map[y].indices) {
                    val cur = map[y][x]
                    val nextX = (x + 1) % map[y].size
                    if (cur == '>' && map[y][nextX] == '.') {
                        changed = true
                        tmp[y][x] = '.'
                        tmp[y][nextX] = '>'
                        x++
                    } else {
                        tmp[y][x] = cur
                    }
                    x++
                }
            }
            for (x in tmp[0].indices) {
                var y = 0
                while (y in tmp.indices) {
                    val cur = tmp[y][x]
                    val nextY = (y + 1) % tmp.size
                    if (cur == 'v' && tmp[nextY][x] == '.') {
                        changed = true
                        map[y][x] = '.'
                        map[nextY][x] = 'v'
                        y++
                    } else {
                        map[y][x] = cur
                    }
                    y++
                }
            }
            count++
        }
        return count
    }

    @Test
    fun part1() {
        assertEquals(
            58, solve(
                """
v...>>.vv>
.vv>>.vv..
>>.>v>...v
>>v>>.>.v.
v>v.vv.v..
>.>>..v...
.vv..>.>v.
v.v..>>v.v
....v..v.>""".trimIndent()
            )
        )
        assertEquals(441, solve(File("files/2021/day25.txt").readText()))
    }
}