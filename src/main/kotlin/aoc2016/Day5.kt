package aoc2016

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    Day5()
        .apply { println(part1("abc")) }
        .apply { println(part1("uqwqemis")) }

}

class Day5 {
    fun part1(input: String): String {
        var pw = ""
        var i = 0
        while (pw.length < 8) {
            val md5 = md5(input.plus(i))
            if (md5.startsWith("00000")) {
                pw += md5[5]
            }
            i++
        }
        return pw
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}