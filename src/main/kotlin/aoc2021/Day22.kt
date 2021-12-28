package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.absoluteValue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day22 {
    private fun solvePart1(input: String): Int {
        val instructions = parseInstructions(input)
        val bounds = Cube(Vec3i(-50, -50, -50), Vec3i(100, 100, 100))
        val region = hashMapOf<Vec3i, Boolean>()
        instructions.forEach { inst ->
            if (inst.cube.within(bounds)) {
                val p = inst.cube.pos
                val cube = inst.cube
                for (x in p.x..cube.x2()) {
                    for (y in p.y..cube.y2()) {
                        for (z in p.z..cube.z2()) {
                            region[Vec3i(x, y, z)] = inst.onOff
                        }
                    }
                }
            }
        }
        return region.filter { it.value }.size
    }

    private fun parseInstructions(input: String): List<Instruction> {
        val instructions = input.lines().map {
            val (onOff, coords) = it.split(" ")
            val fromToCoords = coords.split(",")
                .map { coord -> coord.substring(2).split("..").map(String::toInt) }
            val fromX = minOf(fromToCoords[0][0], fromToCoords[0][1])
            val widthX = (fromToCoords[0][1] - fromToCoords[0][0]).absoluteValue + 1
            val fromY = minOf(fromToCoords[1][0], fromToCoords[1][1])
            val widthY = (fromToCoords[1][1] - fromToCoords[1][0]).absoluteValue + 1
            val fromZ = minOf(fromToCoords[2][0], fromToCoords[2][1])
            val widthZ = (fromToCoords[2][1] - fromToCoords[2][0]).absoluteValue + 1
            val cube = Cube(Vec3i(fromX, fromY, fromZ), Vec3i(widthX, widthY, widthZ), onOff == "on")
            Instruction(onOff == "on", cube)
        }
        return instructions
    }

    private fun solvePart2(input: String): Long {
        val instructions = parseInstructions(input)
        var onCubes = HashSet<Cube>()
        onCubes.add(instructions.first().cube)
        instructions.subList(1, instructions.lastIndex + 1).forEach { inst ->

            onCubes = onCubes.flatMap { aWithoutB(it, inst.cube) }.toHashSet()

            if (inst.cube.on) {
                onCubes.add(inst.cube)
            }
        }
        return onCubes.filter { it.on }.sumOf { it.volume() }
    }

    private fun aWithoutB(a: Cube, b: Cube): List<Cube> {
        val res = mutableListOf<Cube>()

        val topOrigin = Vec3i(a.pos.x, b.y2() + 1, a.pos.z)
        val top = Cube(topOrigin, Vec3i(a.size.x, a.y2() - topOrigin.y + 1, a.size.z), a.on)
        val topIntersect = intersection(a, top, a.on)
        if (topIntersect != null) res += topIntersect

        val leftOrigin = Vec3i(a.pos.x, b.pos.y, a.pos.z)
        val left = Cube(leftOrigin, Vec3i(b.pos.x - leftOrigin.x, b.size.y, a.size.z), a.on)
        val leftIntersect = intersection(a, left, a.on)
        if (leftIntersect != null) res += leftIntersect

        val backOrigin = Vec3i(b.pos.x, b.pos.y, b.z2() + 1)
        val back = Cube(backOrigin, Vec3i(b.size.x, b.size.y, a.z2() - b.z2()), a.on)
        val backIntersect = intersection(a, back, a.on)
        if (backIntersect != null) res += backIntersect

        val frontOrigin = Vec3i(b.pos.x, b.pos.y, a.pos.z)
        val front = Cube(frontOrigin, Vec3i(b.size.x, b.size.y, b.pos.z - a.pos.z), a.on)
        val frontIntersect = intersection(a, front, a.on)
        if (frontIntersect != null) res += frontIntersect

        val rightOrigin = Vec3i(b.x2() + 1, b.pos.y, a.pos.z)
        val right = Cube(rightOrigin, Vec3i(a.x2() - b.x2(), b.size.y, a.size.z), a.on)
        val rightIntersect = intersection(a, right, a.on)
        if (rightIntersect != null) res += rightIntersect

        val bottom = Cube(a.pos, Vec3i(a.size.x, b.pos.y - a.pos.y, a.size.z), a.on)
        val bottomIntersect = intersection(a, bottom, a.on)
        if (bottomIntersect != null) res += bottomIntersect

        return res
    }

    private fun intersection(a: Cube, b: Cube, isOn: Boolean): Cube? {
        val origin = Vec3i(maxOf(a.pos.x, b.pos.x), maxOf(a.pos.y, b.pos.y), maxOf(a.pos.z, b.pos.z))
        val size = Vec3i(
            minOf(a.x2(), b.x2()) - origin.x + 1,
            minOf(a.y2(), b.y2()) - origin.y + 1,
            minOf(a.z2(), b.z2()) - origin.z + 1
        )
        val res = Cube(origin, size, isOn)
        if (res.exists()) return res
        return null
    }

    @Test
    fun testIntersection() {
        val a = Cube(Vec3i(-2, -2, -2), Vec3i(5, 3, 5), true)
        val b = Cube(Vec3i(0, 0, 0), Vec3i(1, 5, 5), true)
        assertEquals(Cube(Vec3i(0, 0, 0), Vec3i(1, 1, 3), true), intersection(a, b, true))
        assertEquals(Cube(Vec3i(0, 0, 0), Vec3i(1, 1, 3), true), intersection(b, a, true))

        assertEquals(
            null, intersection(
                Cube(Vec3i(-1, 0, 0), Vec3i(1, 1, 1), true),
                Cube(Vec3i(0, 0, 0), Vec3i(1, 1, 1), true), true
            )
        )
    }

    @Test
    fun aWithoutBTests() {
        val on = Cube(Vec3i(10, 10, 10), Vec3i(3, 3, 3), true)
        val off = Cube(Vec3i(9, 9, 9), Vec3i(3, 3, 3), false)
        var remain = aWithoutB(on, off)
        assertEquals(3, remain.size)
        assertTrue(remain.contains(Cube(Vec3i(10, 12, 10), Vec3i(3, 1, 3), true)))
        assertTrue(remain.contains(Cube(Vec3i(10, 10, 12), Vec3i(2, 2, 1), true)))
        assertTrue(remain.contains(Cube(Vec3i(12, 10, 10), Vec3i(1, 2, 3), true)))

        val onCube = Cube(Vec3i(-20, -36, -26), Vec3i(54, 45, 55), true)
        val switchOn = Cube(Vec3i(-20, -21, -26), Vec3i(54, 45, 55), true)
        remain = aWithoutB(switchOn, onCube)
        assertTrue(remain.contains(Cube(Vec3i(-20, 9, -26), Vec3i(54, 15, 55), true)))


        var a = Cube(Vec3i(-1, -1, -1), Vec3i(3, 3, 3), true)
        var b = Cube(Vec3i(0, 0, 0), Vec3i(1, 1, 1), false)
        var res = aWithoutB(a, b)
        assertTrue(res.contains(Cube(Vec3i(-1, 1, -1), Vec3i(3, 1, 3), true)), "top")
        assertTrue(res.contains(Cube(Vec3i(-1, 0, -1), Vec3i(1, 1, 3), true)), "left")
        assertTrue(res.contains(Cube(Vec3i(0, 0, 1), Vec3i(1, 1, 1), true)), "back")
        assertTrue(res.contains(Cube(Vec3i(0, 0, -1), Vec3i(1, 1, 1), true)), "front")
        assertTrue(res.contains(Cube(Vec3i(1, 0, -1), Vec3i(1, 1, 3), true)), "right")
        assertTrue(res.contains(Cube(Vec3i(-1, -1, -1), Vec3i(3, 1, 3), true)), "bottom")
        assertEquals(26, res.filter { it.on }.sumOf { it.volume() }, "volume")

        a = Cube(Vec3i(0, 0, 0), Vec3i(10, 10, 10), true)
        b = Cube(Vec3i(1, -1, -1), Vec3i(8, 10, 10), true)
        res = aWithoutB(a, b)
        assertTrue(res.contains(Cube(Vec3i(0, 9, 0), Vec3i(10, 1, 10), true)))
        assertTrue(res.contains(Cube(Vec3i(0, 0, 0), Vec3i(1, 9, 10), true)))
        assertTrue(res.contains(Cube(Vec3i(9, 0, 0), Vec3i(1, 9, 10), true)))
        assertTrue(res.contains(Cube(Vec3i(1, 0, 9), Vec3i(8, 9, 1), true)))

        a = Cube(Vec3i(0, 0, 0), Vec3i(10, 10, 10), true)
        b = Cube(Vec3i(-1, 1, 1), Vec3i(10, 10, 10), true)
        res = aWithoutB(a, b)
        assertTrue(res.size == 3)
        assertTrue(res.contains(Cube(Vec3i(0, 1, 0), Vec3i(9, 9, 1), true)))
        assertTrue(res.contains(Cube(Vec3i(9, 1, 0), Vec3i(1, 9, 10), true)))
        assertTrue(res.contains(Cube(Vec3i(0, 0, 0), Vec3i(10, 1, 10), true)))

        a = Cube(Vec3i(1, 1, 1), Vec3i(1, 1, 1), true)
        b = Cube(Vec3i(0, 0, 0), Vec3i(3, 3, 3), true)
        res = aWithoutB(a, b)
        assertTrue(res.isEmpty())
    }

    @Test
    fun splitMoveAroundTest() {
        val main = Cube(Vec3i(0, 0, 0), Vec3i(3, 3, 3), true)
        for (x in 0..2) {
            for (y in 0..2) {
                for (z in 0..2) {
                    val res = aWithoutB(main, Cube(Vec3i(x, y, z), Vec3i(1, 1, 1), false))
                    assertTrue(res.sumOf { it.volume() } == 26L, "volume")
                }
            }
        }
    }

    @Test
    fun splitPartial() {
        val a = Cube(Vec3i(-1, -1, -1), Vec3i(3, 3, 3), true)
        val b = Cube(Vec3i(1, 1, 1), Vec3i(3, 3, 3), false)
        val res = aWithoutB(a, b)

        assertTrue(res.size == 3, "size")

        assertTrue(res.contains(Cube(Vec3i(-1, 1, -1), Vec3i(2, 1, 3), true)), "left")
        assertTrue(res.contains(Cube(Vec3i(1, 1, -1), Vec3i(1, 1, 2), true)), "front")
        assertTrue(res.contains(Cube(Vec3i(-1, -1, -1), Vec3i(3, 2, 3), true)), "bottom")

        assertEquals(26, res.filter { it.on }.sumOf { it.volume() }, "volume")
    }

    @Test
    fun splitOutside() {
        val a = Cube(Vec3i(-1, -1, -1), Vec3i(3, 3, 3), true)
        val b = Cube(Vec3i(10, 10, 10), Vec3i(1, 1, 1), false)
        val res = aWithoutB(a, b)
        assertTrue(res.size == 1, "size")
        assertTrue(res.contains(a), "bottom")
        assertEquals(27, res.filter { it.on }.sumOf { it.volume() }, "volume")
    }

    data class Instruction(val onOff: Boolean, val cube: Cube)
    data class Cube(val pos: Vec3i, val size: Vec3i, val on: Boolean = false) {
        fun within(other: Cube): Boolean {
            return pos.x in other.pos.x until other.pos.x + other.size.x
                    && pos.y in other.pos.y until other.pos.y + other.size.y
                    && pos.z in other.pos.z until other.pos.z + other.size.z
        }

        fun x2() = pos.x + size.x - 1
        fun y2() = pos.y + size.y - 1
        fun z2() = pos.z + size.z - 1
        fun exists(): Boolean = size.x > 0 && size.y > 0 && size.z > 0
        fun volume(): Long = if (exists()) {
            1L * size.x * size.y * size.z
        } else {
            0L
        }

        private fun fbl() = pos
        private fun fbr() = Vec3i(x2(), pos.y, pos.z)
        private fun ftl() = Vec3i(pos.x, y2(), pos.z)
        private fun ftr() = Vec3i(x2(), y2(), pos.z)
        private fun bbl() = Vec3i(pos.x, pos.y, z2())
        private fun bbr() = Vec3i(x2(), pos.y, z2())
        private fun btl() = Vec3i(pos.x, y2(), z2())
        private fun btr() = Vec3i(x2(), y2(), z2())

        fun intersects(o: Cube): Boolean {
            return within(o.fbl()) || within(o.fbr()) || within(o.ftl()) || within(o.ftr())
                    || within(o.bbl()) || within(o.bbr()) || within(o.btl()) || within(o.btr())
        }

        private fun within(o: Vec3i): Boolean {
            return o.x in pos.x..x2()
                    && o.y in pos.y..y2()
                    && o.z in pos.z..z2()
        }
    }

    data class Vec3i(val x: Int, val y: Int, val z: Int)

    @Test
    fun testIntersect() {
        assertTrue(
            Cube(Vec3i(0, 0, 0), Vec3i(2, 2, 2), true)
                .intersects(Cube(Vec3i(1, 1, 1), Vec3i(10, 10, 10), true))
        )
        assertFalse(
            Cube(Vec3i(0, 0, 0), Vec3i(1, 10, 1), true)
                .intersects(Cube(Vec3i(1, 1, 1), Vec3i(10, 10, 10), true))
        )
    }

    @Test
    fun part1() {
        assertEquals(
            39, solvePart1(
                """
on x=10..12,y=10..12,z=10..12
on x=11..13,y=11..13,z=11..13
off x=9..11,y=9..11,z=9..11
on x=10..10,y=10..10,z=10..10""".trimIndent()
            )
        )
        assertEquals(
            590784, solvePart1(
                """
on x=-20..26,y=-36..17,z=-47..7
on x=-20..33,y=-21..23,z=-26..28
on x=-22..28,y=-29..23,z=-38..16
on x=-46..7,y=-6..46,z=-50..-1
on x=-49..1,y=-3..46,z=-24..28
on x=2..47,y=-22..22,z=-23..27
on x=-27..23,y=-28..26,z=-21..29
on x=-39..5,y=-6..47,z=-3..44
on x=-30..21,y=-8..43,z=-13..34
on x=-22..26,y=-27..20,z=-29..19
off x=-48..-32,y=26..41,z=-47..-37
on x=-12..35,y=6..50,z=-50..-2
off x=-48..-32,y=-32..-16,z=-15..-5
on x=-18..26,y=-33..15,z=-7..46
off x=-40..-22,y=-38..-28,z=23..41
on x=-16..35,y=-41..10,z=-47..6
off x=-32..-23,y=11..30,z=-14..3
on x=-49..-5,y=-3..45,z=-29..18
off x=18..30,y=-20..-8,z=-3..13
on x=-41..9,y=-7..43,z=-33..15
on x=-54112..-39298,y=-85059..-49293,z=-27449..7877
on x=967..23432,y=45373..81175,z=27513..53682""".trimIndent()
            )
        )
        assertEquals(570915, solvePart1(File("files/2021/day22.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(
            39, solvePart2(
                """
on x=10..12,y=10..12,z=10..12
on x=11..13,y=11..13,z=11..13
off x=9..11,y=9..11,z=9..11
on x=10..10,y=10..10,z=10..10
""".trimIndent()
            )
        )
        assertEquals(
            570915, solvePart2(
                """
on x=-20..29,y=-31..16,z=-20..27
on x=-43..1,y=-20..33,z=-45..2
on x=-36..17,y=-6..38,z=-39..14
on x=-30..15,y=-36..15,z=-28..23
on x=-18..29,y=-19..28,z=-7..46
on x=-30..19,y=-13..36,z=0..46
on x=-26..24,y=-38..11,z=-43..11
on x=-38..10,y=-48..3,z=-14..31
on x=-8..41,y=-9..45,z=-43..9
on x=-10..39,y=-10..35,z=-19..32
off x=5..21,y=28..42,z=33..48
on x=-15..38,y=-14..40,z=-19..35
off x=-31..-20,y=5..22,z=-7..10
on x=-19..27,y=-38..14,z=-2..44
off x=-39..-29,y=-25..-6,z=-5..13
on x=-15..33,y=-41..7,z=-19..29
off x=-46..-35,y=23..39,z=39..48
on x=-34..13,y=-4..41,z=-20..30
off x=-26..-12,y=-24..-5,z=27..39
on x=-38..16,y=-43..10,z=-10..44
""".trimIndent()
            )
        )
        assertEquals(
            590784, solvePart2(
                """
on x=-20..26,y=-36..17,z=-47..7
on x=-20..33,y=-21..23,z=-26..28
on x=-22..28,y=-29..23,z=-38..16
on x=-46..7,y=-6..46,z=-50..-1
on x=-49..1,y=-3..46,z=-24..28
on x=2..47,y=-22..22,z=-23..27
on x=-27..23,y=-28..26,z=-21..29
on x=-39..5,y=-6..47,z=-3..44
on x=-30..21,y=-8..43,z=-13..34
on x=-22..26,y=-27..20,z=-29..19
off x=-48..-32,y=26..41,z=-47..-37
on x=-12..35,y=6..50,z=-50..-2
off x=-48..-32,y=-32..-16,z=-15..-5
on x=-18..26,y=-33..15,z=-7..46
off x=-40..-22,y=-38..-28,z=23..41
on x=-16..35,y=-41..10,z=-47..6
off x=-32..-23,y=11..30,z=-14..3
on x=-49..-5,y=-3..45,z=-29..18
off x=18..30,y=-20..-8,z=-3..13
on x=-41..9,y=-7..43,z=-33..15""".trimIndent()
            )
        )
        assertEquals(
            2758514936282235L, solvePart2(
                """
on x=-5..47,y=-31..22,z=-19..33
on x=-44..5,y=-27..21,z=-14..35
on x=-49..-1,y=-11..42,z=-10..38
on x=-20..34,y=-40..6,z=-44..1
off x=26..39,y=40..50,z=-2..11
on x=-41..5,y=-41..6,z=-36..8
off x=-43..-33,y=-45..-28,z=7..25
on x=-33..15,y=-32..19,z=-34..11
off x=35..47,y=-46..-34,z=-11..5
on x=-14..36,y=-6..44,z=-16..29
on x=-57795..-6158,y=29564..72030,z=20435..90618
on x=36731..105352,y=-21140..28532,z=16094..90401
on x=30999..107136,y=-53464..15513,z=8553..71215
on x=13528..83982,y=-99403..-27377,z=-24141..23996
on x=-72682..-12347,y=18159..111354,z=7391..80950
on x=-1060..80757,y=-65301..-20884,z=-103788..-16709
on x=-83015..-9461,y=-72160..-8347,z=-81239..-26856
on x=-52752..22273,y=-49450..9096,z=54442..119054
on x=-29982..40483,y=-108474..-28371,z=-24328..38471
on x=-4958..62750,y=40422..118853,z=-7672..65583
on x=55694..108686,y=-43367..46958,z=-26781..48729
on x=-98497..-18186,y=-63569..3412,z=1232..88485
on x=-726..56291,y=-62629..13224,z=18033..85226
on x=-110886..-34664,y=-81338..-8658,z=8914..63723
on x=-55829..24974,y=-16897..54165,z=-121762..-28058
on x=-65152..-11147,y=22489..91432,z=-58782..1780
on x=-120100..-32970,y=-46592..27473,z=-11695..61039
on x=-18631..37533,y=-124565..-50804,z=-35667..28308
on x=-57817..18248,y=49321..117703,z=5745..55881
on x=14781..98692,y=-1341..70827,z=15753..70151
on x=-34419..55919,y=-19626..40991,z=39015..114138
on x=-60785..11593,y=-56135..2999,z=-95368..-26915
on x=-32178..58085,y=17647..101866,z=-91405..-8878
on x=-53655..12091,y=50097..105568,z=-75335..-4862
on x=-111166..-40997,y=-71714..2688,z=5609..50954
on x=-16602..70118,y=-98693..-44401,z=5197..76897
on x=16383..101554,y=4615..83635,z=-44907..18747
off x=-95822..-15171,y=-19987..48940,z=10804..104439
on x=-89813..-14614,y=16069..88491,z=-3297..45228
on x=41075..99376,y=-20427..49978,z=-52012..13762
on x=-21330..50085,y=-17944..62733,z=-112280..-30197
on x=-16478..35915,y=36008..118594,z=-7885..47086
off x=-98156..-27851,y=-49952..43171,z=-99005..-8456
off x=2032..69770,y=-71013..4824,z=7471..94418
on x=43670..120875,y=-42068..12382,z=-24787..38892
off x=37514..111226,y=-45862..25743,z=-16714..54663
off x=25699..97951,y=-30668..59918,z=-15349..69697
off x=-44271..17935,y=-9516..60759,z=49131..112598
on x=-61695..-5813,y=40978..94975,z=8655..80240
off x=-101086..-9439,y=-7088..67543,z=33935..83858
off x=18020..114017,y=-48931..32606,z=21474..89843
off x=-77139..10506,y=-89994..-18797,z=-80..59318
off x=8476..79288,y=-75520..11602,z=-96624..-24783
on x=-47488..-1262,y=24338..100707,z=16292..72967
off x=-84341..13987,y=2429..92914,z=-90671..-1318
off x=-37810..49457,y=-71013..-7894,z=-105357..-13188
off x=-27365..46395,y=31009..98017,z=15428..76570
off x=-70369..-16548,y=22648..78696,z=-1892..86821
on x=-53470..21291,y=-120233..-33476,z=-44150..38147
off x=-93533..-4276,y=-16170..68771,z=-104985..-24507""".trimIndent()
            )
        )
        assertEquals(1268313839428137, solvePart2(File("files/2021/day22.txt").readText()))
    }
}