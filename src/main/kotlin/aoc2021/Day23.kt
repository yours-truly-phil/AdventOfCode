package aoc2021

import org.junit.jupiter.api.Test

class Day23 {
    @Test
    fun part1() {
//        #############
//        #...........#
//        ###D#D#B#A###
//          #C#A#B#C#
//          #########
//
//        #############
//        #A..........# A -> 9
//        ###D#D#B#.###
//          #C#A#B#C#
//          #########
//
//        #############
//        #AB.B.......# B -> 110
//        ###D#D#.#.###
//          #C#A#.#C#
//          #########
//
//        #############
//        #AB.B.......# C -> 600
//        ###D#D#.#.###
//          #C#A#C#.#
//          #########
//
//        #############
//        #AB.B.....A.# D -> 7000
//        ###D#.#.#.### A -> 7
//          #C#.#C#D#
//          #########
//
//        #############
//        #A........A.# B -> 70
//        ###.#B#.#D### D -> 8000
//          #C#B#C#D#
//          #########
//
//        #############
//        #...........# C -> 700
//        ###A#B#C#D### A -> 12
//          #A#B#C#D#
//          #########
//
//        18 + 1000 + 39000 + 900 + 500 + 10 + 40 + 8 + 170 + 8 + 70 + 500 + 1800 = 44014
        println(9 + 110 + 600 + 7000 + 7 + 70 + 8000 + 700 + 12)
    }

    @Test
    fun part2() {
//        #############
//        #...........#
//        ###D#D#B#A###
//          #D#C#B#A#
//          #D#B#A#C#
//          #C#A#B#C#
//          #########
//
//        #############
//        #AA.........# A -> 18
//        ###D#D#B#.###
//          #D#C#B#.#
//          #D#B#A#C#
//          #C#A#B#C#
//          #########
//
//        #############
//        #AA.......CC# C -> 1000
//        ###D#D#B#.###
//          #D#C#B#.#
//          #D#B#A#.#
//          #C#A#B#.#
//          #########
//
//        #############
//        #AA.......CC# D -> 39000
//        ###.#.#B#D###
//          #.#C#B#D#
//          #.#B#A#D#
//          #C#A#B#D#
//          #########
//
//        #############
//        #.......C.CC# C -> 900
//        ###.#.#B#D### A -> 10
//          #.#C#B#D#
//          #A#B#A#D#
//          #A#A#B#D#
//          #########
//
//        #############
//        #BB.B...C.CC# B -> 210
//        ###.#.#.#D### A -> 9
//          #A#C#.#D#
//          #A#B#.#D#
//          #A#A#.#D#
//          #########
//
//        #############
//        #BB.B.......# C -> 2200
//        ###.#.#C#D###
//          #A#.#C#D#
//          #A#B#C#D#
//          #A#A#C#D#
//          #########
//
//        #############
//        #BB.B.A.B...# B -> 60
//        ###.#.#C#D### A -> 5
//          #A#.#C#D#
//          #A#.#C#D#
//          #A#.#C#D#
//          #########
//
//        #############
//        #...........# B -> 210
//        ###A#B#C#D### A -> 4
//          #A#B#C#D#
//          #A#B#C#D#
//          #A#B#C#D#
//          #########
//
//        18 + 1000 + 39000 + 900 + 10 + 210 + 9 + 2200 + 60 + 5 + 210 + 4 = 43626
        println(18 + 1000 + 39000 + 900 + 10 + 210 + 9 + 2200 + 60 + 5 + 210 + 4)
    }
}