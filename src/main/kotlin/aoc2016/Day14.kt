package aoc2016

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    Day14().apply {
        println("sample=${part1("abc")}")
        println("part1=${part1("ahsbgdzn")}")
        println("strechsample=${stretchMd5("abc0", 2016, MessageDigest.getInstance("MD5")).joinToString("")}")
        println("part2sample=${part2("abc")}")
        println("part2=${part2("ahsbgdzn")}")
    }
}

class Day14 {
    fun part1(input: String): Int {
        val md = MessageDigest.getInstance("MD5")
        var idx = 0
        val result = ArrayList<Int>()
        val triplets = HashMap<Char, ArrayList<Int>>()
        while (result.size < 100) {
            val md5 = BigInteger(1, md.digest((input + idx).toByteArray())).toString(16).toCharArray()
            for (i in 0 until md5.size - 4) {
                if (md5[i] == md5[i + 1] && md5[i + 1] == md5[i + 2]
                    && md5[i + 2] == md5[i + 3] && md5[i + 3] == md5[i + 4]
                ) {
                    if (triplets.containsKey(md5[i])) {
                        for (idxTriplet in triplets[md5[i]]!!) {
                            if (idxTriplet >= idx - 1000) {
                                result.add(idxTriplet)
                            }
                        }
                        triplets.remove(md5[i])
                    }
                }
            }
            var i = 0
            while (i < md5.size - 2) {
                if (md5[i] == md5[i + 1] && md5[i] == md5[i + 2]) {
                    triplets.computeIfAbsent(md5[i]) { ArrayList() }
                    triplets[md5[i]]!! += idx
                    break
                }
                i++
            }
            idx++
        }
        result.sort()
        return result[63]
    }

    fun part2(input: String): Int {
        val md = MessageDigest.getInstance("MD5")
        var idx = 0
        val result = ArrayList<Int>()
        val triplets = HashMap<Char, ArrayList<Int>>()
        while (result.size < 100) {
            val md5 = stretchMd5(input + idx, 2016, md)
            for (i in 0 until md5.size - 4) {
                if (md5[i] == md5[i + 1] && md5[i + 1] == md5[i + 2]
                    && md5[i + 2] == md5[i + 3] && md5[i + 3] == md5[i + 4]
                ) {
                    if (triplets.containsKey(md5[i])) {
                        for (idxTriplet in triplets[md5[i]]!!) {
                            if (idxTriplet >= idx - 1000) {
                                result.add(idxTriplet)
                            }
                        }
                        triplets.remove(md5[i])
                    }
                }
            }
            var i = 0
            while (i < md5.size - 2) {
                if (md5[i] == md5[i + 1] && md5[i] == md5[i + 2]) {
                    triplets.computeIfAbsent(md5[i]) { ArrayList() }
                    triplets[md5[i]]!! += idx
                    break
                }
                i++
            }
            idx++
        }
        result.sort()
        return result[63]
    }

    fun stretchMd5(input: String, num: Int, md: MessageDigest): CharArray {
        var hash = input
        repeat(num + 1) {
            hash = BigInteger(1, md.digest(hash.toByteArray())).toString(16).padStart(32, '0')
        }
        return hash.toCharArray()
    }
}