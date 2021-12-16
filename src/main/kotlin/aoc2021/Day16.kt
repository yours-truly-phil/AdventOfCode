package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

fun hexToBinary(hex: String): String {
    val s = hex.toBigInteger(16).toString(2)
    return String.format("%${hex.length * 4}s", s).replace(" ", "0")
}

class Day16 {
    private fun solvePart1(input: String): Int {
        return sumVersions(hexToBinary(input)).versionSum
    }

    class Part1ParseResult(val remain: String, val versionSum: Int)

    private fun sumVersions(s: String): Part1ParseResult {
        val version = s.substring(0, 3).toInt(2)
        val typeId = s.substring(3, 6).toInt(2)
        var index = 6
        if (typeId == 4) {
            // literal value
            val res = arrayListOf<String>()
            while (index < s.length) {
                res.add(s.substring(index + 1, index + 5))
                index += 5
                if (s[index - 5] == '0') {
                    break
                }
            }
            return Part1ParseResult(s.substring(index), version)
        } else {
            // operator
            val lengthId = s.substring(6, 7).toInt(2)
            if (lengthId == 0) {
                // next 15 bits are total length in bits
                var lengthLeft = s.substring(7, 22).toInt(2)
                var remain = s.substring(22)
                var sum = 0
                while (lengthLeft > 0) {
                    val res = sumVersions(remain)
                    lengthLeft -= remain.length - res.remain.length
                    sum += res.versionSum
                    remain = res.remain
                }
                return Part1ParseResult(remain, sum + version)
            } else {
                // next 11 bits are number of sub-packets immediately contained by this packet
                val numSubPackets = s.substring(7, 18).toInt(2)
                var remain = s.substring(18)
                var sum = 0
                repeat(numSubPackets) {
                    val res = sumVersions(remain)
                    sum += res.versionSum
                    remain = res.remain
                }
                return Part1ParseResult(remain, sum + version)
            }
        }
    }

    class Part2ParseRes(val remain: String, val resultNum: Long)

    private fun solvePart2(input: String): Long {
        return parse(hexToBinary(input)).resultNum
    }

    private fun parse(s: String): Part2ParseRes {
        return when (val typeId = s.substring(3, 6).toInt(2)) {
            0 -> operator(s) { it.sum() }
            1 -> operator(s) { it.reduce { acc, l -> acc * l } }
            2 -> operator(s) { it.minOrNull()!! }
            3 -> operator(s) { it.maxOrNull()!! }
            4 -> {
                var index = 6
                val res = arrayListOf<String>()
                while (index < s.length) {
                    val bits = s.substring(index + 1, index + 5)
                    res.add(String.format("%4s", bits).replace(" ", "0"))
                    index += 5
                    if (s[index - 5] == '0') {
                        break
                    }
                }
                val remain = s.substring(index)
                val total = res.joinToString("").toLong(2)
                Part2ParseRes(remain, total)
            }
            5 -> operator(s) { if (it[0] > it[1]) 1 else 0 }
            6 -> operator(s) { if (it[0] < it[1]) 1 else 0 }
            7 -> operator(s) { if (it[0] == it[1]) 1 else 0 }
            else -> throw IllegalStateException("unknown typeId $typeId")
        }
    }

    private fun operator(s: String, packetFun: (packets: List<Long>) -> Long): Part2ParseRes {
        return if (s.substring(6, 7).toInt(2) == 0) {
            // next 15 bits are total length in bits
            var lengthLeft = s.substring(7, 22).toInt(2)
            var remain = s.substring(22)
            val packets: MutableList<Long> = mutableListOf()
            while (lengthLeft > 0) {
                val res = parse(remain)
                lengthLeft -= remain.length - res.remain.length
                packets.add(res.resultNum)
                remain = res.remain
            }
            Part2ParseRes(remain, packetFun(packets))
        } else {
            // next 11 bits are number of sub-packets immediately contained by this packet
            val numSubPackets = s.substring(7, 18).toInt(2)
            var remain = s.substring(18)
            val packets: MutableList<Long> = mutableListOf()
            repeat(numSubPackets) {
                val res = parse(remain)
                packets.add(res.resultNum)
                remain = res.remain
            }
            Part2ParseRes(remain, packetFun(packets))
        }
    }

    @Test
    fun part1() {
        assertEquals(16, solvePart1("8A004A801A8002F478"))
        assertEquals(12, solvePart1("620080001611562C8802118E34"))
        assertEquals(23, solvePart1("C0015000016115A2E0802F182340"))
        assertEquals(31, solvePart1("A0016C880162017C3686B18A3D4780"))
        assertEquals(920, solvePart1(File("files/2021/day16.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(3, solvePart2("C200B40A82"))
        assertEquals(10185143721112, solvePart2(File("files/2021/day16.txt").readText()))
    }
}