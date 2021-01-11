package aoc2017

import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.test.assertEquals

class Day3 {
    fun countSteps(num: Int): Int {
        var size = ceil(sqrt(num.toDouble())).toInt()
        if (size % 2 == 0) size++

        val br = size * size
        val stepsToMid = size / 2
        val side = size - 1
        val bl = br - side
        val tl = bl - side
        val tr = tl - side
        when (num) {
            in bl..br -> {
                val mid = br - stepsToMid
                val distToMid = abs(num - mid)
                println("$num bottom (mid=$mid dist=$distToMid)")
                return distToMid + stepsToMid
            }
            in tl..bl -> {
                val mid = bl - stepsToMid
                val distToMid = abs(num - mid)
                println("$num left (mid=$mid dist=$distToMid)")
                return distToMid + stepsToMid
            }
            in tr..tl -> {
                val mid = tl - stepsToMid
                val distToMid = abs(num - mid)
                println("$num top (mid=$mid dist=$distToMid)")
                return distToMid + stepsToMid
            }
            else -> {
                val mid = tr - stepsToMid
                val distToMid = abs(num - mid)
                println("$num right (mid=$mid dist=$distToMid)")
                return distToMid + stepsToMid
            }
        }
    }

    @Test
    fun sample() {
        assertEquals(0, countSteps(1))
        assertEquals(3, countSteps(12))
        assertEquals(2, countSteps(23))
        assertEquals(31, countSteps(1024))
    }

    @Test
    fun part1() {
        assertEquals(371, countSteps(368078))
    }

    @Test
    fun part2() {
        // solved by hand
        //(369601) 363010	349975	330785	312453	295229	279138	266330	130654
        //      	 6591	6444	6155	5733	5336	5022	2450	128204
        //      	13486	147 	142	    133	    122 	59  	2391	123363
        //	        14267	304 	5   	4   	2   	57  	2275	116247
        //      	15252	330 	10  	1   	1   	54  	2105	109476
        //      	16295	351 	11  	23  	25  	26  	1968	103128
        //      	17008	362 	747 	806 	880	    931 	957 	98098
        //      	17370	35487	37402	39835	42452	45220	47108	48065
        println(369601)
    }
}