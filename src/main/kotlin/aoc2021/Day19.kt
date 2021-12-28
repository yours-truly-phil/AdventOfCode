package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day19 {
    private fun allPossibleRotations(set: Set<Vec3i>): Set<Set<Vec3i>> {
        val allRotations = set.map {
            val x = it.x
            val y = it.y
            val z = it.z
            listOf(
                // y is up
                Vec3i(z, x, y),
                Vec3i(-z, -x, y),
                Vec3i(x, -z, y),
                Vec3i(-x, z, y),
                // y is down
                Vec3i(x, z, -y),
                Vec3i(-x, -z, -y),
                Vec3i(z, -x, -y),
                Vec3i(-z, x, -y),
                // x is up
                Vec3i(y, z, x),
                Vec3i(-y, -z, x),
                Vec3i(z, -y, x),
                Vec3i(-z, y, x),
                // x is down
                Vec3i(z, y, -x),
                Vec3i(-z, -y, -x),
                Vec3i(y, -z, -x),
                Vec3i(-y, z, -x),
                // z is up
                Vec3i(x, y, z),
                Vec3i(-x, -y, z),
                Vec3i(y, -x, z),
                Vec3i(-y, x, z),
                // z is down
                Vec3i(y, x, -z),
                Vec3i(-y, -x, -z),
                Vec3i(x, -y, -z),
                Vec3i(-x, y, -z)
            )
        }
        return (0 until allRotations[0].size).map { rotation ->
            allRotations
                .asSequence()
                .map { it[rotation] }
                .toMutableSet()
        }.toSet()
    }

    private fun solvePart1(input: String, numOverlaps: Int = 12): Int {
        val scanners = input.split("\n\n").map { Scanner(it) }
        val fullMap = getFullMap(scanners, numOverlaps)
        return fullMap.size
    }

    @Suppress("SameParameterValue")
    private fun solvePart2(input: String, numOverlaps: Int = 12): Int {
        val scanners = input.split("\n\n").map { Scanner(it) }
        getFullMap(scanners, numOverlaps)

        var max = Int.MIN_VALUE
        for (a in scanners) {
            for (b in scanners) {
                if (a != b) {
                    max = maxOf(a.position.dist(b.position), max)
                }
            }
        }

        return max
    }

    private fun getFullMap(
        scanners: List<Scanner>,
        numOverlaps: Int
    ): MutableSet<Vec3i> {
        val first = scanners.first()
        val fullMap = first.beacons.toMutableSet()
        val matchedScanners = arrayListOf(first)
        val newMatches = mutableSetOf(first)

        while (newMatches.isNotEmpty()) {
            val a = newMatches.first()
            val unMatchedScanners = scanners.filterNot { matchedScanners.contains(it) }

            for (b in unMatchedScanners) {
                val aBeacons = a.beacons
                val bBeacons = b.beacons

                val allPossibleRotations = allPossibleRotations(bBeacons)
                for (rotatedBeacons in allPossibleRotations) {
                    for (moveTarget in aBeacons) {
                        for (rotatedBeacon in rotatedBeacons) {
                            val moveVec3i = Vec3i(
                                moveTarget.x - rotatedBeacon.x,
                                moveTarget.y - rotatedBeacon.y,
                                moveTarget.z - rotatedBeacon.z
                            )
                            val rotatedMovedBeacons =
                                rotatedBeacons.map {
                                    Vec3i(
                                        it.x + moveVec3i.x,
                                        it.y + moveVec3i.y,
                                        it.z + moveVec3i.z
                                    )
                                }
                                    .toSet()
                            val overlappingBeacons = overlaps(aBeacons, rotatedMovedBeacons)
                            if (overlappingBeacons.size == numOverlaps) {
                                b.beacons = rotatedMovedBeacons
                                newMatches.add(b)
                                fullMap.addAll(rotatedMovedBeacons)
                                b.position = moveVec3i
                            }
                        }
                    }
                }
            }
            newMatches.remove(a)
            matchedScanners.add(a)
        }
        return fullMap
    }

    private fun overlaps(a: Set<Vec3i>, b: Set<Vec3i>): Set<Vec3i> = a.intersect(b)

    data class Scanner(val input: String) {
        val id: String
        var position = Vec3i(0, 0, 0)
        var beacons: Set<Vec3i>

        init {
            val lines = input.lines()
            id = lines[0]
            beacons = lines.subList(1, lines.size).map {
                it.split(",").map(String::toInt).let { (x, y, z) -> Vec3i(x, y, z) }
            }.toHashSet()
        }
    }

    data class Vec3i(val x: Int, val y: Int, val z: Int) {
        fun dist(o: Vec3i) = abs(x - o.x) + abs(y - o.y) + abs(z - o.z)
        override fun toString(): String = "$x,$y,$z"
    }

    @Test
    fun allPossibleRotationsTest() {
        val beacons = listOf(Vec3i(0, 0, 0), Vec3i(1, 0, 0), Vec3i(0, 2, 0), Vec3i(0, 0, 3)).toSet()
        val allRotations = allPossibleRotations(beacons).toSet()
        assertEquals(24, allRotations.size)
    }

    @Test
    fun overlapTest() {
        val a = setOf(Vec3i(0, 0, 0), Vec3i(1, 1, 1), Vec3i(2, 2, 2))
        val b = setOf(Vec3i(1, 1, 1), Vec3i(2, 2, 2), Vec3i(3, 3, 3))
        val overlaps = overlaps(a, b)
        assertEquals(2, overlaps(a, b).size)
        assertTrue(overlaps.contains(Vec3i(1, 1, 1)))
        assertTrue(overlaps.contains(Vec3i(2, 2, 2)))
    }

    @Test
    fun part1() {
        assertEquals(
            3, solvePart1(
                """
Scan 0
0,0,0
1,2,3
2,3,4

Scan 1
1,1,1
2,3,4
3,4,5

Scan 2
1,1,-1
2,4,-3
3,5,-4""".trimIndent(), 3
            )
        )
        assertEquals(
            79, solvePart1(
                """
--- scanner 0 ---
404,-588,-901
528,-643,409
-838,591,734
390,-675,-793
-537,-823,-458
-485,-357,347
-345,-311,381
-661,-816,-575
-876,649,763
-618,-824,-621
553,345,-567
474,580,667
-447,-329,318
-584,868,-557
544,-627,-890
564,392,-477
455,729,728
-892,524,684
-689,845,-530
423,-701,434
7,-33,-71
630,319,-379
443,580,662
-789,900,-551
459,-707,401

--- scanner 1 ---
686,422,578
605,423,415
515,917,-361
-336,658,858
95,138,22
-476,619,847
-340,-569,-846
567,-361,727
-460,603,-452
669,-402,600
729,430,532
-500,-761,534
-322,571,750
-466,-666,-811
-429,-592,574
-355,545,-477
703,-491,-529
-328,-685,520
413,935,-424
-391,539,-444
586,-435,557
-364,-763,-893
807,-499,-711
755,-354,-619
553,889,-390

--- scanner 2 ---
649,640,665
682,-795,504
-784,533,-524
-644,584,-595
-588,-843,648
-30,6,44
-674,560,763
500,723,-460
609,671,-379
-555,-800,653
-675,-892,-343
697,-426,-610
578,704,681
493,664,-388
-671,-858,530
-667,343,800
571,-461,-707
-138,-166,112
-889,563,-600
646,-828,498
640,759,510
-630,509,768
-681,-892,-333
673,-379,-804
-742,-814,-386
577,-820,562

--- scanner 3 ---
-589,542,597
605,-692,669
-500,565,-823
-660,373,557
-458,-679,-417
-488,449,543
-626,468,-788
338,-750,-386
528,-832,-391
562,-778,733
-938,-730,414
543,643,-506
-524,371,-870
407,773,750
-104,29,83
378,-903,-323
-778,-728,485
426,699,580
-438,-605,-362
-469,-447,-387
509,732,623
647,635,-688
-868,-804,481
614,-800,639
595,780,-596

--- scanner 4 ---
727,592,562
-293,-554,779
441,611,-461
-714,465,-776
-743,427,-804
-660,-479,-426
832,-632,460
927,-485,-438
408,393,-506
466,436,-512
110,16,151
-258,-428,682
-393,719,612
-211,-452,876
808,-476,-593
-575,615,604
-485,667,467
-680,325,-822
-627,-443,-432
872,-547,-609
833,512,582
807,604,487
839,-516,451
891,-625,532
-652,-548,-490
30,-46,-14""".trimIndent(), 12
            )
        )
        assertEquals(430, solvePart1(File("files/2021/day19.txt").readText(), 12))
    }

    @Test
    fun part2() {
        assertEquals(11860, solvePart2(File("files/2021/day19.txt").readText(), 12))
    }
}