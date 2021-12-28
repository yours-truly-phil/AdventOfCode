package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import java.lang.Character.getNumericValue
import kotlin.test.assertEquals

class Day24 {
//    private fun playground(
//        n0: Int,
//        n1: Int,
//        n2: Int,
//        n3: Int,
//        n4: Int,
//        n5: Int,
//        n6: Int,
//        n7: Int,
//        n8: Int,
//        n9: Int,
//        n10: Int,
//        n11: Int,
//        n12: Int,
//        n13: Int
//    ): Int {
//
////        var x = 0
////        var y = 0
////        var z = 0
////        var w = 0
//
//        //inp w => n0
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 13
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 0
//        //mul y x
//        //add z y
////        val z0 = n0
//
//        //inp w => n1
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 11
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 3
//        //mul y x
//        //add z y
////        val z1 = z0 * 26 + n1 + 3
//
//        //inp w => n2
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 14
//        //eql x w
//        //eql x 0
////        x = 1
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 8
//        //mul y x
//        //add z y
////        z = (n0 * 26 + n1 + 3) * 26 + n2 + 8
////        val z2 = (n0 * 26 + n1 + 3) * 26 + n2 + 8
//
//        //inp w => n3
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 26
//        //add x -5
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 5
//        //mul y x
////        y = (n3 + 5) * if ((n2 + 3) != n3) 1 else 0
//        //add z y
//        /**
//         * should n3 be 3 larger than n2?
//         */
//        val z3 =
//            (((n0 * 26 + n1 + 3) * 26 + n2 + 8) / 26) * (25 * (if ((n2 + 3) != n3) 1 else 0) + 1) + (n3 + 5) * if ((n2 + 3) != n3) 1 else 0
//
//        //inp w => n4
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 14
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 13
//        //mul y x
//        //add z y
//        val z4 = z3 * 26 + n4 + 13
//
//        //inp w => n5
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 10
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 9
//        //mul y x
//        //add z y
//        val z5 = z4 * 26 + n5 + 9
//
//        //inp w => n6
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 12
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 6
//        //mul y x
//        //add z y
//        val z6 = z5 * 26 + n6 + 6
//
//        //inp w => n7
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 26
//        //add x -14
//        //eql x w
//        //eql x 0
////        x = if (z6 % 26 - 14 != n7) 1 else 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 1
//        //mul y x
//        //add z y
//        val z7 = (z6 / 26) * (25 * (if (z6 % 26 - 14 != n7) 1 else 0) + 1) + (n7 + 1) * if (z6 % 26 - 14 != n7) 1 else 0
//
//        //inp w => n8
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 26
//        //add x -8
//        //eql x w
//        //eql x 0
////        x = if (z7 % 26 - 8 != n8) 1 else 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 1
//        //mul y x
//        //add z y
//        val z8 = (z7 / 26) * (25 * (if (z7 % 26 - 8 != n8) 1 else 0) + 1) + (n8 + 1) * if (z7 % 26 - 8 != n8) 1 else 0
//
//        //inp w => n9
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 1
//        //add x 13
//        //eql x w
//        //eql x 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 2
//        //mul y x
//        //add z y
////        val z9 = z8 * 26 + n9 + 2
//
//        //inp w => n10
//        //mul x 0
//        //add x z
//        //mod x 26
////        x = (z8 * 26 + n9 + 2) % 26
////        x = (n9 + 2) % 26
//        //div z 26
////        z = (z8 * 26 + n9 + 2) / 26
////        z = z8
//        //add x 0
//        //eql x w
//        //eql x 0
////        x = if ((n9 + 2) % 26 != n10) 1 else 0
//        /**
//         * should n10 be 2 larger than n9?
//         */
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
////        z = z8 * (25 * (if ((n9 + 2) % 26 != n10) 1 else 0) + 1)
//        //mul y 0
//        //add y w
//        //add y 7
//        //mul y x
////        y = (n10 + 7) * if ((n9 + 2) % 26 != n10) 1 else 0
//        //add z y
//        val z10 = z8 * (25 * (if ((n9 + 2) % 26 != n10) 1 else 0) + 1) + (n10 + 7) * if ((n9 + 2) % 26 != n10) 1 else 0
//
//        //inp w => n11
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 26
////        z = z10 / 26
//        //add x -5
//        //eql x w
//        //eql x 0
////        x = if (z10 % 26 - 5 != n11) 1 else 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
////        z = (z10 / 26) * (25 * (if (z10 % 26 - 5 != n11) 1 else 0) + 1)
//        //mul y 0
//        //add y w
//        //add y 5
//        //mul y x
////        y = (n11 + 5) * if (z10 % 26 - 5 != n11) 1 else 0
//        //add z y
//        val z11 =
//            (z10 / 26) * (25 * (if (z10 % 26 - 5 != n11) 1 else 0) + 1) + (n11 + 5) * if (z10 % 26 - 5 != n11) 1 else 0
//
//        //inp w => n12
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 26
////        z = z11 / 26
//        //add x -9
//        //eql x w
//        //eql x 0
////        x = if (z11 % 26 - 9 != n12) 1 else 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
//        //mul y 0
//        //add y w
//        //add y 8
//        //mul y x
//        //add z y
//
//        val z12 =
//            z11 / 26 * (25 * (if (z11 % 26 - 9 != n12) 1 else 0) + 1) + (n12 + 8) * if (z11 % 26 - 9 != n12) 1 else 0
//
//        //inp w => n13
//        //mul x 0
//        //add x z
//        //mod x 26
//        //div z 26
////        z = z12 / 26
//        //add x -1
//        //eql x w
//        //eql x 0
////        x = if (z12 % 26 - 1 != n13) 1 else 0
//        //mul y 0
//        //add y 25
//        //mul y x
//        //add y 1
//        //mul z y
////        z = z12 / 26 * (25 * (if (z12 % 26 - 1 != n13) 1 else 0) + 1)
//        //mul y 0
//        //add y w
//        //add y 15
//        //mul y x
////        y = (n13 + 15) * if (z12 % 26 - 1 != n13) 1 else 0
//        //add z y
//        val z13 = z12 / 26 * (25 * (if (z12 % 26 - 1 != n13) 1 else 0) + 1) + (n13 + 15) * if (z12 % 26 - 1 != n13) 1 else 0
//        // if (z12 % 26 - 1 != n13)
////        z = z12 + n13 + 15 // won't be null so
//        // z12 % 26 - 1 == n13
//        /**
//         * n13 = z12 % 26 - 1
//         */
////        val z13 = z12 / 26
//
//        return z13
//    }

    private fun part1Disassembled(
        n0: Int,
        n1: Int,
        n2: Int,
        n3: Int,
        n4: Int,
        n5: Int,
        n6: Int,
        n7: Int,
        n8: Int,
        n9: Int,
        n10: Int,
        n11: Int,
        n12: Int,
        n13: Int
    ): Int {

        val z3 =
            (((n0 * 26 + n1 + 3) * 26 + n2 + 8) / 26) * (25 * (if (n2 + 3 != n3) 1 else 0) + 1) + (n3 + 5) * if (n2 + 3 != n3) 1 else 0
        val z4 = z3 * 26 + n4 + 13
        val z5 = z4 * 26 + n5 + 9
        val z6 = z5 * 26 + n6 + 6
        val z7 = (z6 / 26) * (25 * (if (z6 % 26 - 14 != n7) 1 else 0) + 1) + (n7 + 1) * if (z6 % 26 - 14 != n7) 1 else 0
        val z8 = (z7 / 26) * (25 * (if (z7 % 26 - 8 != n8) 1 else 0) + 1) + (n8 + 1) * if (z7 % 26 - 8 != n8) 1 else 0
        val z10 = z8 * (25 * (if (n9 + 2 != n10) 1 else 0) + 1) + (n10 + 7) * if (n9 + 2 != n10) 1 else 0
        val z11 =
            (z10 / 26) * (25 * (if (z10 % 26 - 5 != n11) 1 else 0) + 1) + (n11 + 5) * if (z10 % 26 - 5 != n11) 1 else 0
        val z12 =
            z11 / 26 * (25 * (if (z11 % 26 - 9 != n12) 1 else 0) + 1) + (n12 + 8) * if (z11 % 26 - 9 != n12) 1 else 0
//        return z12 / 26

        return z12 / 26 * (25 * (if (z12 % 26 - 1 != n13) 1 else 0) + 1) + (n13 + 15) * if (z12 % 26 - 1 != n13) 1 else 0
    }

    private fun solvePart1DisAssembled(): String {
        for (n0 in 9 downTo 1) {
            for (n1 in 9 downTo 1) {
                for (n2 in 6 downTo 1) {
//                    for (n3 in 9 downTo 1) {
                    for (n4 in 9 downTo 1) {
                        for (n5 in 9 downTo 1) {
                            for (n6 in 9 downTo 1) {
                                for (n7 in 9 downTo 1) {
                                    for (n8 in 9 downTo 1) {
                                        for (n9 in 7 downTo 1) {
//                                                for (n10 in 9 downTo 1) {
                                            for (n11 in 9 downTo 1) {
                                                for (n12 in 9 downTo 1) {
                                                    val n3 = n2 + 3
                                                    val n10 = n9 + 2
                                                    val res = part1Disassembled(
                                                        n0,
                                                        n1,
                                                        n2,
                                                        n3,
                                                        n4,
                                                        n5,
                                                        n6,
                                                        n7,
                                                        n8,
                                                        n9,
                                                        n10,
                                                        n11,
                                                        n12,
                                                        8
                                                    )
                                                    if (res == 0) {
                                                        return "$n0$n1$n2$n3$n4$n5$n6$n7$n8$n9$n10$n11${n12}8"
                                                    }
                                                }
                                            }
//                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    }
                }
            }
        }
        throw IllegalStateException("No solution found")
    }

    private fun solvePart2DisAssembled(): String {
        for (n0 in 1.. 9) {
            for (n1 in 1..9) {
                for (n2 in 1..6) {
//                    for (n3 in 9 downTo 1) {
                    for (n4 in 1..9) {
                        for (n5 in 1..9) {
                            for (n6 in 1..9) {
                                for (n7 in 1..9) {
                                    for (n8 in 1..9) {
                                        for (n9 in 1..7) {
//                                                for (n10 in 9 downTo 1) {
                                            for (n11 in 1..9) {
                                                for (n12 in 1..9) {
                                                    for (n13 in 1..9) {
                                                        val n3 = n2 + 3
                                                        val n10 = n9 + 2
                                                        val res = part1Disassembled(
                                                            n0,
                                                            n1,
                                                            n2,
                                                            n3,
                                                            n4,
                                                            n5,
                                                            n6,
                                                            n7,
                                                            n8,
                                                            n9,
                                                            n10,
                                                            n11,
                                                            n12,
                                                            n13
                                                        )
                                                        if (res == 0) {
                                                            return "$n0$n1$n2$n3$n4$n5$n6$n7$n8$n9$n10$n11$n12$n13"
                                                        }
                                                    }
                                                }
                                            }
//                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    }
                }
            }
        }
        throw IllegalStateException("No solution found")
    }

    class MONAD(modelNum: String, input: List<String>) {
        private var remain = modelNum
        val v = Vec4i(0, 0, 0, 0)

        val p = input.forEach {
            val parts = it.split(" ")
            when (parts[0]) {
                "inp" -> {
                    v.set(parts[1], getNumericValue(remain[0]))
                    remain = remain.substring(1)
                }
                "add" -> {
                    if (parts[2] in "xyzw") {
                        v.set(parts[1], v.get(parts[1]) + v.get(parts[2]))
                    } else {
                        v.set(parts[1], v.get(parts[1]) + parts[2].toInt())
                    }
                }
                "mul" -> {
                    if (parts[2] in "xyzw") {
                        v.set(parts[1], v.get(parts[1]) * v.get(parts[2]))
                    } else {
                        v.set(parts[1], v.get(parts[1]) * parts[2].toInt())
                    }
                }
                "div" -> {
                    if (parts[2] in "xyzw") {
                        v.set(parts[1], v.get(parts[1]) / v.get(parts[2]))
                    } else {
                        v.set(parts[1], v.get(parts[1]) / parts[2].toInt())
                    }
                }
                "mod" -> {
                    if (parts[2] in "xyzw") {
                        v.set(parts[1], v.get(parts[1]) % v.get(parts[2]))
                    } else {
                        v.set(parts[1], v.get(parts[1]) % parts[2].toInt())
                    }
                }
                "eql" -> {
                    if (parts[2] in "xyzw") {
                        v.set(parts[1], if (v.get(parts[1]) == v.get(parts[2])) 1 else 0)
                    } else {
                        v.set(parts[1], if (v.get(parts[1]) == parts[2].toInt()) 1 else 0)
                    }
                }
                else -> throw IllegalArgumentException("Unknown command: $it")
            }
        }
    }

    class Vec4i(var x: Int, var y: Int, var z: Int, var w: Int) {
        fun set(axis: String, value: Int) {
            when (axis) {
                "x" -> x = value
                "y" -> y = value
                "z" -> z = value
                "w" -> w = value
                else -> throw IllegalArgumentException("Unknown axis: $axis")
            }
        }

        fun get(axis: String): Int {
            return when (axis) {
                "x" -> x
                "y" -> y
                "z" -> z
                "w" -> w
                else -> throw IllegalArgumentException("Unknown axis: $axis")
            }
        }
    }

    @Test
    fun part1() {
        val monad = MONAD("99691891979938", File("files/2021/day24.txt").readLines())
        println(monad.v.z)
        assertEquals("99691891979938", solvePart1DisAssembled())
    }

    @Test
    fun part2() {
        val monad = MONAD("27141191213911", File("files/2021/day24.txt").readLines())
        println(monad.v.z)
        assertEquals("27141191213911", solvePart2DisAssembled())
    }
}