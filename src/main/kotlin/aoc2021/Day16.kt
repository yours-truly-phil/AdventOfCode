package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day16 {
    private fun solvePart1(input: String): Int {
        val s = input.toBigInteger(16).toString(2)
        return parsePacket(String.format("%${input.length * 4}s", s).replace(" ", "0")).res
    }

    class ParseRes(val remain: String, val res: Int)

    private fun parsePacket(s: String): ParseRes {
        val version = s.substring(0, 3).toInt(2)
        println("version: $version")
        val typeId = s.substring(3, 6).toInt(2)
        var index = 6
        if (typeId == 4) {
            // literal value
            println("literal value version $version")
            val res = arrayListOf<String>()
            while (index < s.length) {
                res.add(s.substring(index + 1, index + 5))
                index += 5
                if (s[index - 5] == '0') {
                    break
                }
            }
            return ParseRes(s.substring(index), version)
        } else {
            // operator
            val lengthId = s.substring(6, 7).toInt(2)
            if (lengthId == 0) {
                // next 15 bits are total length in bits
                var lengthLeft = s.substring(7, 22).toInt(2)
                var remain = s.substring(22)
                var sum = 0
                while (lengthLeft > 0) {
                    val res = parsePacket(remain)
                    lengthLeft -= remain.length - res.remain.length
                    sum += res.res
                    remain = res.remain
                }
                return ParseRes(remain, sum + version)
            } else {
                // next 11 bits are number of sub-packets immediately contained by this packet
                val numSubPackets = s.substring(7, 18).toInt(2)
                var remain = s.substring(18)
                var sum = 0
                repeat(numSubPackets) { subPackNum ->
                    val res = parsePacket(remain)
                    sum += res.res
                    remain = res.remain
                }
                return ParseRes(remain, sum + version)
            }
        }
    }

    class ParseRes2(val remain: String, val res: Long)

    private fun solvePart2(input: String): Long {
        val s = input.toBigInteger(16).toString(2)
        return parsePacket2(String.format("%${input.length * 4}s", s).replace(" ", "0")).res
    }

    private fun parsePacket2(s: String): ParseRes2 {
        val version = s.substring(0, 3).toInt(2)
        println("version: $version")
        val typeId = s.substring(3, 6).toInt(2)
        var index = 6

        when (typeId) {
            0 -> {
                // sum packets
                return operator(s) { it.sum() }
            }
            1 -> {
                // product packets
                return operator(s) { it.reduce { acc, l -> acc * l } }
            }
            2 -> {
                // min
                return operator(s) { it.minOrNull()!! }
            }
            3 -> {
                // max
                return operator(s) { it.maxOrNull()!! }
            }
            4 -> {
                // literal value
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
                return ParseRes2(remain, total)
            }
            5 -> {
                // greater than (1 if value first sub > value of second sub, 0 otherwise)
                return operator(s) { if (it[0] > it[1]) 1 else 0 }
            }
            6 -> {
                // less than (1 if value first sub < value of second sub, 0 otherwise)
                return operator(s) { if (it[0] < it[1]) 1 else 0 }
            }
            7 -> {
                // equal (1 if value first sub == value of second sub, 0 otherwise)
                return operator(s) { if (it[0] == it[1]) 1 else 0 }
            }
        }
        throw IllegalStateException("should not reach here")
    }

    private fun operator(s: String, packetFun: (packets: List<Long>) -> Long): ParseRes2 {
        val lengthId = s.substring(6, 7).toInt(2)
        if (lengthId == 0) {
            // next 15 bits are total length in bits
            var lengthLeft = s.substring(7, 22).toInt(2)
            var remain = s.substring(22)
            val packets: MutableList<Long> = mutableListOf()
            while (lengthLeft > 0) {
                val res = parsePacket2(remain)
                lengthLeft -= remain.length - res.remain.length
                packets.add(res.res)
                remain = res.remain
            }
            val total = packetFun(packets)
            return ParseRes2(remain, total)
        } else {
            // next 11 bits are number of sub-packets immediately contained by this packet
            val numSubPackets = s.substring(7, 18).toInt(2)
            var remain = s.substring(18)
            val packets: MutableList<Long> = mutableListOf()
            repeat(numSubPackets) { subPackNum ->
                val res = parsePacket2(remain)
                packets.add(res.res)
                remain = res.remain
            }
            val total = packetFun(packets)
            return ParseRes2(remain, total)
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