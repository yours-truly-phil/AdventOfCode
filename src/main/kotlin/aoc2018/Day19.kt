package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day19 {
    private fun regValue(input: String, regNo: Int, mem: IntArray): Int {
        val lines = input.lines()
        val ipBind = lines[0].split(" ")[1].toInt()
        val instructions = lines.subList(1, lines.size)
            .map { it.split(" ") }
            .map { Instruction(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt()) }
            .toTypedArray()

        var ip = 0
        val limit = 2
        var count = 0
        var seven = false
        while (ip in instructions.indices) {
            val inst = instructions[ip]

//            if (count == limit) break
//            else println("$ip $inst ${mem.joinToString(", ", "[", "]")}")
//            count++

//            if (ip == 7) seven = true
//            if (seven) {
//                println("$ip $inst ${mem.joinToString(", ", "[", "]")}")
//                count++
//            }
//            if (count == limit) {
//                seven = false
//                count = 0
//            }

            mem[ipBind] = ip
            when (inst.type) {
                "seti" -> mem[inst.c] = inst.a
                "setr" -> mem[inst.c] = mem[inst.a]
                "addi" -> mem[inst.c] = mem[inst.a] + inst.b
                "addr" -> mem[inst.c] = mem[inst.a] + mem[inst.b]
                "muli" -> mem[inst.c] = mem[inst.a] * inst.b
                "mulr" -> mem[inst.c] = mem[inst.a] * mem[inst.b]
                "gtrr" -> if (mem[inst.a] > mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                "eqrr" -> if (mem[inst.a] == mem[inst.b]) mem[inst.c] = 1 else mem[inst.c] = 0
                else -> println("unknown instruction: $inst")
            }
            ip = mem[ipBind]
            ip++
        }
        return mem[regNo]
    }

    class Instruction(val type: String, val a: Int, val b: Int, val c: Int) {
        override fun toString(): String {
            return "$type $a $b $c"
        }
    }

    /*
#ip 1 -> a, ip, b, c, d, e
 0 addi 1 16 1 # ip += 16
 1 seti 1 1 5
 2 seti 1 4 2
 3 mulr 5 2 3
 4 eqrr 3 4 3   # register 4 is (always?) 10551261, this puts 1 in reg[3] if reg[3] == 10551261
 5 addr 3 1 1   # if prev is equal then it skips the next
 6 addi 1 1 1   # gets skipped if step 4 was true / if not skipped, skips the next
 7 addr 5 0 0   # only executed if step 4 was true: a += e (sums in reg[0] whatever e counts while numbers go from 0 to eq reg[3] to do sth)

    # the first couple of times 7 and 8 gets executed (before it starts taking too long to reach 7 again):
    # 7 addr 5 0 0 [0, 6, 10551261, 1, 10551261, 1]
    # 8 addi 2 1 2 [1, 7, 10551261, 1, 10551261, 1]

    # 7 addr 5 0 0 [1, 6, 3517087, 1, 10551261, 3]  # interesting: 10551261 / 3 = 3517087
    # 8 addi 2 1 2 [4, 7, 3517087, 1, 10551261, 3]   |
                                                     v
    # 7 addr 5 0 0 [4, 6, 1507323, 1, 10551261, 7]  # 3517087 / (7/3) = 1507323 <-- WHY (7/3)?? because e is 7 and was 3 last iteration?
    # 8 addi 2 1 2 [11, 7, 1507323, 1, 10551261, 7]  |
                                                     v
    # 7 addr 5 0 0 [11, 6, 502441, 1, 10551261, 21] # 1507323 / 3 = 502441 <-- ok div by 3 because e is 21 and was 7 last iteration = 3
    # 8 addi 2 1 2 [32, 7, 502441, 1, 10551261, 21]

    # next op 7 still isn't reached after over 10 minutes

 8 addi 2 1 2
 9 gtrr 2 4 3
10 addr 1 3 1
11 seti 2 7 1
12 addi 5 1 5
13 gtrr 5 4 3
14 addr 3 1 1
15 seti 1 8 1
16 mulr 1 1 1

all below here only executed once initially
which results in jump to 1 with [0, 0, 0, 10550400, 10551261, 0]
x 17 addi 4 2 4
x 18 mulr 4 4 4
x 19 mulr 1 4 4
x 20 muli 4 11 4
x 21 addi 3 1 3
x 22 mulr 3 1 3
x 23 addi 3 3 3
x 24 addr 4 3 4
x 25 addr 1 0 1
x 26 seti 0 3 1
x 27 setr 1 1 3
x 28 mulr 3 1 3
x 29 addr 1 3 3
x 30 mulr 1 3 3
x 31 muli 3 14 3
x 32 mulr 3 1 3
x 33 addr 4 3 4
x 34 seti 0 9 0
x 35 seti 0 4 1   -> whole block puts 10551261 in reg d and then doesn't get executed again, number probably unique to my input
     */

    @Test
    fun sample() {
        assertEquals(6, regValue("#ip 0\n" +
                "seti 5 0 1\n" +
                "seti 6 0 2\n" +
                "addi 0 1 0\n" +
                "addr 1 2 3\n" +
                "setr 1 0 0\n" +
                "seti 8 0 4\n" +
                "seti 9 0 5", 0, IntArray(6) { 0 }))
    }

    @Test
    fun part1() {
        assertEquals(1344, regValue(File("files/2018/day19.txt").readText(), 0, IntArray(6) { 0 }))
    }

    @Test
    fun part2() {
        assertEquals(1344, regValue(File("files/2018/day19.txt").readText(), 0, intArrayOf(1, 0, 0, 0, 0, 0)))
    }
}