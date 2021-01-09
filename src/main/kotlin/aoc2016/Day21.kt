package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day21 {
    fun scramble(s: String, input: String): String {
        val pw = s.toCharArray()
        input.lines().forEach {
            val parts = it.split(" ")
            when {
                parts[0] == "swap" -> {
                    when {
                        parts[1] == "position" -> swapPosition(pw, parts[2].toInt(), parts[5].toInt())
                        parts[1] == "letter" -> swapChars(pw, parts[2][0], parts[5][0])
                    }
                }
                parts[0] == "rotate" -> {
                    when {
                        parts[1] == "based" -> rotateBased(pw, parts[6][0])
                        parts[1] == "left" -> rotateLeft(pw, parts[2].toInt())
                        parts[1] == "right" -> rotateRight(pw, parts[2].toInt())
                    }
                }
                parts[0] == "reverse" -> {
                    reverse(pw, parts[2].toInt(), parts[4].toInt())
                }
                parts[0] == "move" -> {
                    move(pw, parts[2].toInt(), parts[5].toInt())
                }
            }
        }
        return pw.joinToString("")
    }

    fun unscrambleBruteforce(s: String, input: String): String {
        val perms = ArrayList<String>()
        perms(s, "", perms)
        perms.forEach {
            if (scramble(it, input) == s) {
                return it
            }
        }
        return "n/a"
    }

    fun perms(s: String, ans: String, res: ArrayList<String>) {
        if (s.isEmpty()) {
            res += ans
            return
        }

        for (i in s.indices) {
            val c = s[i]
            val ros = s.substring(0, i) + s.substring(i + 1)
            perms(ros, ans + c, res)
        }
    }

    fun unscramble(s: String, input: String): String {
        val pw = s.toCharArray()
        input.lines().reversed().forEach {
            val parts = it.split(" ")
            when {
                parts[0] == "swap" -> {
                    when {
                        parts[1] == "position" -> swapPosition(pw, parts[2].toInt(), parts[5].toInt())
                        parts[1] == "letter" -> swapChars(pw, parts[2][0], parts[5][0])
                    }
                }
                parts[0] == "rotate" -> {
                    when {
                        parts[1] == "based" -> rotateBased(pw, parts[6][0]) // TODO reverse
                        parts[1] == "left" -> rotateRight(pw, parts[2].toInt())
                        parts[1] == "right" -> rotateLeft(pw, parts[2].toInt())
                    }
                }
                parts[0] == "reverse" -> {
                    reverse(pw, parts[2].toInt(), parts[4].toInt())
                }
                parts[0] == "move" -> {
                    move(pw, parts[5].toInt(), parts[2].toInt())
                }
            }
        }
        return pw.joinToString("")
    }

    private fun move(pw: CharArray, i1: Int, i2: Int) {
        if (i1 <= i2) {
            val tmp = pw[i1]
            for (i in i1 until i2) {
                pw[i] = pw[i + 1]
            }
            pw[i2] = tmp
        } else {
            val tmp = pw[i1]
            for (i in i1 until pw.size - 1) {
                pw[i] = pw[i + 1]
            }
            for (i in pw.size - 1 downTo i2 + 1) {
                pw[i] = pw[i - 1]
            }
            pw[i2] = tmp
        }
    }

    private fun reverse(pw: CharArray, i1: Int, i2: Int) {
        var num = 0
        while (num <= (i2 - i1) / 2) {
            val tmp = pw[i1 + num]
            pw[i1 + num] = pw[i2 - num]
            pw[i2 - num] = tmp
            num++
        }
    }

    private fun rotateBasedReversed(pw: CharArray, c: Char) {
        // TODO
    }

    private fun rotateBased(pw: CharArray, c: Char) {
        var idx = pw.joinToString("").indexOf(c)
        if (idx >= 4) idx++
        rotateRight(pw, idx + 1)
    }

    private fun rotateRight(pw: CharArray, num: Int) {
        repeat(num) {
            val tmp = pw.last()
            for (i in pw.size - 1 downTo 1) {
                pw[i] = pw[i - 1]
            }
            pw[0] = tmp
        }
    }

    private fun rotateLeft(pw: CharArray, num: Int) {
        repeat(num) {
            val tmp = pw.first()
            for (i in 0 until pw.size - 1) {
                pw[i] = pw[i + 1]
            }
            pw[pw.size - 1] = tmp
        }
    }

    private fun swapChars(pw: CharArray, x: Char, y: Char) {
        for (i in pw.indices) {
            if (pw[i] == x) {
                pw[i] = y
            } else if (pw[i] == y) {
                pw[i] = x
            }
        }
    }

    private fun swapPosition(pw: CharArray, i1: Int, i2: Int) {
        val tmp = pw[i1]
        pw[i1] = pw[i2]
        pw[i2] = tmp
    }

    @Test
    fun `permutations of string`() {
        val perms = ArrayList<String>()
        perms("abcd", "", perms)
        assertTrue(perms.containsAll(
            arrayListOf("abcd", "abdc", "acbd", "acdb", "adbc", "adcb", "bacd",
                "badc", "bcad", "bcda", "bdac", "bdca", "cabd", "cadb", "cbad",
                "cbda", "cdab", "cdba", "dabc", "dacb", "dbac", "dbca", "dcab", "dcba")
        ))
    }

    @Test
    fun `move pos to lower idx pos`() {
        val arr = "abcdefg".toCharArray()
        move(arr, 5, 1)
        assertEquals("afbcdeg", arr.joinToString(""))
    }

    @Test
    fun `move pos to pos`() {
        val arr = "abcdefg".toCharArray()
        move(arr, 1, 5)
        assertEquals("acdefbg", arr.joinToString(""))
    }

    @Test
    fun `reverse from to index odd`() {
        val arr = "abcdefg".toCharArray()
        reverse(arr, 2, 5)
        assertEquals("abfedcg", arr.joinToString(""))
    }

    @Test
    fun `reverse from to index even`() {
        val arr = "abcdefg".toCharArray()
        reverse(arr, 2, 4)
        assertEquals("abedcfg", arr.joinToString(""))
    }

    @Test
    fun `rotate based on position of letter`() {
        val arr = "abdec".toCharArray()
        rotateBased(arr, 'b')
        assertEquals("ecabd", arr.joinToString(""))
        val arr2 = "ecabd".toCharArray()
        rotateBased(arr2, 'd')
        assertEquals("decab", arr2.joinToString(""))
    }

    @Test
    fun `rotate left`() {
        val arr = "abcd".toCharArray()
        rotateLeft(arr, 1)
        assertEquals("bcda", arr.joinToString(""))
    }

    @Test
    fun `rotate right`() {
        val arr = "abcd".toCharArray()
        rotateRight(arr, 1)
        assertEquals("dabc", arr.joinToString(""))
    }

    @Test
    fun `swap position`() {
        val arr = "abc".toCharArray()
        swapPosition(arr, 0, 2)
        assertEquals("cba", arr.joinToString(""))
    }

    @Test
    fun `swap chars`() {
        val arr = "abc".toCharArray()
        swapChars(arr, 'a', 'c')
        assertEquals("cba", arr.joinToString(""))
    }

    @Test
    fun sample() {
        val instructions = "swap position 4 with position 0\n" +
                "swap letter d with letter b\n" +
                "reverse positions 0 through 4\n" +
                "rotate left 1 step\n" +
                "move position 1 to position 4\n" +
                "move position 3 to position 0\n" +
                "rotate based on position of letter b\n" +
                "rotate based on position of letter d"
        assertEquals("decab", scramble("abcde", instructions))
    }

    @Test
    fun part1() {
        assertEquals("agcebfdh", scramble("abcdefgh", File("files/2016/day21.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals("afhdbegc", unscrambleBruteforce("fbgdceah", File("files/2016/day21.txt").readText()))
    }
}