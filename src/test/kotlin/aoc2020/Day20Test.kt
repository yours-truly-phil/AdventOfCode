package aoc2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day20Test {

    @Test
    fun invert10bits() {
        val s = "0010001110"
        var num = s.toInt(2)
        println(num.biString(s.length))
        num = num.biString(s.length).reversed().toInt(2)
        println(num.biString(s.length))
    }

    @Test
    fun part1() {
        val day20 = Day20("files/2020/day20Test.txt")
        day20.part1()
    }

    @Test
    fun getSideAsString() {
        val day20 = Day20("files/2020/day20Test.txt")
        day20.part1()
        val topLeft = day20.findTopLeft()
        println(topLeft.draw())
        println("north: ${topLeft.getSideAsString(0)}")
        println("east: ${topLeft.getSideAsString(1)}")
        println("south: ${topLeft.getSideAsString(2)}")
        println("west: ${topLeft.getSideAsString(3)}")
    }

    @Test
    fun rotateContent() {
        val day20 = Day20("files/2020/day20Test.txt")
        day20.part1()
        val topLeft = day20.findTopLeft()
        for (i in 0..4) {
            println(topLeft.draw())
            topLeft.rot90CW()
        }
    }

    @Test
    fun flipContent() {
        val day20 = Day20("files/2020/day20Test.txt")
        day20.part1()
        val topLeft = day20.findTopLeft()
        println(topLeft.draw())
        topLeft.flipV().flipH()
        println(topLeft.draw())
        topLeft.flipV()
        println(topLeft.draw())
    }

    @Test
    fun rotateCompareIntsAndString() {
        val raw = """
            Tile 1:
            #..
            .##
            ...""".trimIndent()
        val img = Day20.Image(raw)
        assertEquals(
            """
            #..
            .##
            ...
            
            """.trimIndent(), img.draw()
        )
        assertEquals("100", img.sides[0]!!.biString(3))
        assertEquals("010", img.sides[1]!!.biString(3))
        assertEquals("000", img.sides[2]!!.biString(3))
        assertEquals("100", img.sides[3]!!.biString(3))
        img.flipH()
        assertEquals(
            """
            ..#
            ##.
            ...
            
            """.trimIndent(), img.draw()
        )
        assertEquals("..#", img.getSideAsString(0))
        assertEquals("001", img.sides[0]!!.biString(3))
        assertEquals("#..", img.getSideAsString(1))
        assertEquals("100", img.sides[1]!!.biString(3))
        assertEquals("...", img.getSideAsString(2))
        assertEquals("000", img.sides[2]!!.biString(3))
        assertEquals(".#.", img.getSideAsString(3))
        assertEquals("010", img.sides[3]!!.biString(3))
        img.rot90CW()
        assertEquals(
            """
            .#.
            .#.
            ..#
            
            """.trimIndent(), img.draw()
        )
        assertEquals(".#.", img.getSideAsString(0))
        assertEquals("010", img.sides[0]!!.biString(3))
        assertEquals("..#", img.getSideAsString(1))
        assertEquals("001", img.sides[1]!!.biString(3))
        assertEquals("..#", img.getSideAsString(2))
        assertEquals("001", img.sides[2]!!.biString(3))
        assertEquals("...", img.getSideAsString(3))
        assertEquals("000", img.sides[3]!!.biString(3))
        img.rot90CW()
        assertEquals(
            """
            ...
            .##
            #..
            
            """.trimIndent(), img.draw()
        )
        assertEquals("...", img.getSideAsString(0))
        assertEquals("000", img.sides[0]!!.biString(3))
        assertEquals(".#.", img.getSideAsString(1))
        assertEquals("010", img.sides[1]!!.biString(3))
        assertEquals("#..", img.getSideAsString(2))
        assertEquals("100", img.sides[2]!!.biString(3))
        assertEquals("..#", img.getSideAsString(3))
        assertEquals("001", img.sides[3]!!.biString(3))
        img.flipV()
        assertEquals(
            """
            #..
            .##
            ...
            
            """.trimIndent(), img.draw()
        )
        assertEquals("#..", img.getSideAsString(0))
        assertEquals("100", img.sides[0]!!.biString(3))
        assertEquals(".#.", img.getSideAsString(1))
        assertEquals("010", img.sides[1]!!.biString(3))
        assertEquals("...", img.getSideAsString(2))
        assertEquals("000", img.sides[2]!!.biString(3))
        assertEquals("#..", img.getSideAsString(3))
        assertEquals("100", img.sides[3]!!.biString(3))
        val img2Raw = """
            Tile 3251:
            ...#.#.###
            ##.#...#.#
            .#..##...#
            ....##...#
            .#.......#
            ..#......#
            #..#......
            ##.#.#.#..
            #......#..
            ##.##.#...
        """.trimIndent()
        val img2 = Day20.Image(img2Raw)
        val stringRep = img2.draw()
        img2.flipH().rot90CW().flipH().rot90CW()
        assertEquals(stringRep, img2.draw())
        img2.flipV().flipH().rot90CW().flipV().rot90CW().flipH()
        assertEquals(stringRep, img2.draw())
    }

    @Test
    fun countSharps() {
        val input = """.####...#####..#...###..
#####..#..#.#.####..#.#.
.#.#...#.###...#.##.O#..
#.O.##.OO#.#.OO.##.OOO##
..#O.#O#.O##O..O.#O##.##
...#.#..##.##...#..#..##
#.##.#..#.#..#..##.#.#..
.###.##.....#...###.#...
#.####.#.#....##.#..#.#.
##...#..#....#..#...####
..#.##...###..#.#####..#
....#.##.#.#####....#...
..##.##.###.....#.##..#.
#...#...###..####....##.
.#.##...#.##.#.#.###...#
#.###.#..####...##..#...
#.###...#.##...#.##O###.
.O##.#OO.###OO##..OOO##.
..O#.O..O..O.#O##O##.###
#.#..##.########..#..##.
#.#####..#.#...##..#....
#....##..#.#########..##
#...#.....#..##...###.##
#..###....##.#...##.##.#""".trimIndent()
        assertEquals(273, input.filter { it == '#' }.count())
    }

    @Test
    fun findMonster() {

        val l = "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   "
        val lArr = l.lines().map { it.toCharArray() }.toTypedArray()
        val m = "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   "
        val mArr = m.lines().map { it.toCharArray() }.toTypedArray()
        val lake = Day20.Lake(lArr, mArr)
        assertEquals(1, lake.monsterMap.size)
    }

    @Test
    fun findMonsterBigLake() {
        val l = "#    ##    ##    ####    ##    ##    ###\n" +
                "#    ##    ##    ####    ##    ##    ###\n" +
                "#    ##    ##    ###                  # \n" +
                "#    ##    ##    ####    ##    ##    ###\n" +
                "#    ##    ##    ### #  #  #  #  #  #   \n" +
                "#    ##    ##    ###   ##########     # \n" +
                "#    ##    ##    ####  ############# ###\n" +
                "#    ##    ##    ### ########### #  #   "
        val lArr = l.lines().map { it.toCharArray() }.toTypedArray()
        val m = "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   "
        val mArr = m.lines().map { it.toCharArray() }.toTypedArray()
        val lake = Day20.Lake(lArr, mArr)
        assertEquals(2, lake.monsterMap.size)
    }

    @Test
    fun findMonsterAoCTest() {
        val l = """.#.#..#.##...#.##..#####
###....#.#....#..#......
##.##.###.#.#..######...
###.#####...#.#####.#..#
##.#....#.##.####...#.##
...########.#....#####.#
....#..#...##..#.#.###..
.####...#..#.....#......
#..#.##..#..###.#.##....
#.####..#.####.#.#.###..
###.#.#...#.######.#..##
#.####....##..########.#
##..##.#...#...#.#.#.#..
...#..#..#.#.##..###.###
.#.#....#.##.#...###.##.
###.#...#..#.##.######..
.#.#.###.##.##.#..#.##..
.####.###.#...###.#..#.#
..#.#..#..#.#.#.####.###
#..####...#.#.#.###.###.
#####..#####...###....##
#.##..#..#...#..####...#
.#.###..##..##..####.##.
...###...##...#...#..###""".trimIndent()
        var lArr = l.lines().map { it.toCharArray() }.toTypedArray()
        val m = "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   "
        val mArr = m.lines().map { it.toCharArray() }.toTypedArray()
        var lake = Day20.Lake(lArr, mArr)
//        assertEquals(2, lake.monsterMap.size)
        lArr = rot90CW(lArr)
        printArr(lArr)
        lArr = rot90CW(lArr)
        printArr(lArr)
        lArr = rot90CW(lArr)
        printArr(lArr)
        flipH(lArr)
        printArr(lArr)
        lake = Day20.Lake(lArr, mArr)
        println(lake.monsterMap.size)
        lArr = rot90CW(lArr)
        printArr(lArr)
        lake = Day20.Lake(lArr, mArr)
        println(lake.monsterMap.size)
        lArr = rot90CW(lArr)
        printArr(lArr)
        lake = Day20.Lake(lArr, mArr)
        println(lake.monsterMap.size)
        lArr = rot90CW(lArr)
        printArr(lArr)
        lake = Day20.Lake(lArr, mArr)
        println(lake.monsterMap.size)
    }

    fun printArr(arr: Array<CharArray>) {
        arr.forEach { println(it.joinToString("")) }
        println()
    }

    @Test
    fun part2() {
        val day20 = Day20("files/2020/day20Test.txt")
        day20.part2()
    }
}
