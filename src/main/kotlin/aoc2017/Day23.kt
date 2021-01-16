package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class Day23 {
    fun countMul(input: String, reg: HashMap<String, Long>): Int {
        val instructions = input.lines()
            .map { it.split(" ").toTypedArray() }.toTypedArray()
        var idx = 0
        var count = 0
        while (idx >= 0 && idx < instructions.size) {
            val inst = instructions[idx]
            if (inst[0] == "set") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[2]]!!
                } else {
                    reg[inst[1]] = inst[2].toLong()
                }
            } else if (inst[0] == "sub") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[1]]!! - reg[inst[2]]!!
                } else {
                    reg[inst[1]] = reg[inst[1]]!! - inst[2].toLong()
                }
            } else if (inst[0] == "mul") {
                count++
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[1]]!! * reg[inst[2]]!!
                } else {
                    reg[inst[1]] = reg[inst[1]]!! * inst[2].toLong()
                }
            } else if (inst[0] == "jnz") {
                if (reg.containsKey(inst[1])) {
                    if (reg[inst[1]] != 0L) {
                        idx += inst[2].toInt() - 1
                    }
                } else {
                    if (inst[1].toInt() != 0) {
                        idx += inst[2].toInt() - 1
                    }
                }
            }
            idx++
        }
        return count
    }

    fun exe() {
        var a = 1L
        var b = 0L
        var c = 0L
        var d = 0L
        var e = 0L
        var f = 0L
        var g = 0L
        var h = 0L
        b = 84L
        c = b

        b *= 100L
        b += 100000L
        c = b
        c += 17000L

        var first = true
        var line9 = true
        var line11 = true
        while (true) {
            if (line9) {
                f = 1
                d = 2
            }
            if (line11) {
                e = 2
            }
            g = d
            g *= e
            g -= b
            if (g == 0L) {
                f = 0
            }
            e++
            g = e
            g -= b
            if (g != 0L) {
                line11 = false
                line9 = false
                continue
            } else {
                if (first) {
                    println("a=$a b=$b c=$c d=$d e=$e f=$f g=$g h=$h")
                    first = false
                }
            }
            d++
            g = d
            g -= b
            if (g != 0L) {
                line11 = true
                line9 = false
                continue
            }
            if (f == 0L) {
                h++
            }
            g = b
            g -= c
            if (g == 0L) {
                println("done: h=$h")
                return
            } else {
                b += 17
                line9 = true
                line11 = true
                continue
            }
        }
    }

    fun tryThePrimeThingy(): Int {
        val tb = 108400
        val tc = 125400
        var count = 0
        var nums = 0
        for (i in tb..tc step 17) {
            nums++
            for (j in 2..i / 2) {
                if (i % j == 0) {
                    count++
                    break
                }
            }
        }
        println("nums=$nums")
        println("nonPrimes=$count")
        return count
    }

    fun shortenPart2WhileItsRunningAnyway() {

        var b = 0
        var c = 0
        var d = 0
        var e = 0
        var f = 0
        var h = 0
        b = 84
        b = 100 * b + 100000 // 108400
        c = b + 17000 // 125400
        while (true) {
            f = 1
            d = 2
            do {
                e = 2
                do {
                    if (d * e == b) {
                        f = 0 // if b is no prime -> leads to h++
                    }
                    e++
                } while (e != b)
                d++
            } while (d != b)
            if (f == 0) {
                h++ // if b is no prime
            }
            if (b == c) { // b increases each time by 17 starts with 108400 (to 125400)
                println("done: h=$h")
                return
            }
            b += 17 // all numbers between b until c incr by 17 that are not primes
        }
    }

    fun finalValRegH(input: String, reg: HashMap<String, Long>): Long {
        val instructions = input.lines()
            .map { it.split(" ").toTypedArray() }.toTypedArray()
        var idx = 0
        while (idx >= 0 && idx < instructions.size) {
            val inst = instructions[idx]

//            instructions.forEachIndexed { index, arr ->
//                print(index)
//                if (index == idx) print("*") else print(" ")
//                println(arr.joinToString(" "))
//            }

            if (inst[0] == "set") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[2]]!!
                } else {
                    reg[inst[1]] = inst[2].toLong()
                }
            } else if (inst[0] == "sub") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[1]]!! - reg[inst[2]]!!
                    if (inst[1] == "h") {
                        println("$reg")
                    }
                } else {
                    reg[inst[1]] = reg[inst[1]]!! - inst[2].toLong()
                }
            } else if (inst[0] == "mul") {
                if (reg.containsKey(inst[2])) {
                    reg[inst[1]] = reg[inst[1]]!! * reg[inst[2]]!!
                } else {
                    reg[inst[1]] = reg[inst[1]]!! * inst[2].toLong()
                }
            } else if (inst[0] == "jnz") {
                if (reg.containsKey(inst[1])) {
                    if (reg[inst[1]] != 0L) {
                        idx += inst[2].toInt() - 1
                    }
                } else {
                    if (inst[1].toInt() != 0) {
                        idx += inst[2].toInt() - 1
                    }
                }
            }
            idx++
        }
        return reg["h"]!!
    }

    @Test
    fun runProgram() {
//        exe()
//        shortenPart2WhileItsRunningAnyway()
        tryThePrimeThingy()
    }

    @Test
    fun part1() {
        assertEquals(6724, countMul(File("files/2017/day23.txt").readText(),
            hashMapOf("a" to 0L, "b" to 0L, "c" to 0L,
                "d" to 0L, "e" to 0L, "f" to 0L, "g" to 0L, "h" to 0L)))
    }

    @Test
    fun part2() {
//        assertEquals(-1, finalValRegH(File("files/2017/day23.txt").readText(),
//            hashMapOf("a" to 1L, "b" to 0L, "c" to 0L,
//                "d" to 0L, "e" to 0L, "f" to 0L, "g" to 0L, "h" to 0L)))
        assertEquals(903, tryThePrimeThingy())
    }
}