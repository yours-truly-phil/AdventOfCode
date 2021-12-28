package aoc2016

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day21 {
    private fun scramble(s: String, input: String): String {
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

    @Suppress("SameParameterValue")
    private fun unscramble(s: String, input: String): String {
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
                        parts[1] == "based" -> rotateBasedReversed(pw, parts[6][0])
                        parts[1] == "left" -> rotateLeftReversed(pw, parts[2].toInt())
                        parts[1] == "right" -> rotateRightReversed(pw, parts[2].toInt())
                    }
                }
                parts[0] == "reverse" -> {
                    reverse(pw, parts[2].toInt(), parts[4].toInt())
                }
                parts[0] == "move" -> {
                    reverseMove(pw, parts[2].toInt(), parts[5].toInt())
                }
            }
        }
        return pw.joinToString("")
    }

    @Suppress("SameParameterValue")
    private fun unscrambleBruteforce(s: String, input: String): String {
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

    private fun reverseMove(pw: CharArray, i1: Int, i2: Int) {
        move(pw, i2, i1)
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
        val idx = pw.joinToString("").indexOf(c)
        if (idx == 1) rotateLeft(pw, 1)
        if (idx == 3) rotateLeft(pw, 2)
        if (idx == 5) rotateLeft(pw, 3)
        if (idx == 7) rotateLeft(pw, 4)
        if (idx == 2) rotateRight(pw, 2)
        if (idx == 4) rotateRight(pw, 1)
        if (idx == 0) rotateLeft(pw, 1)
    }

    private fun rotateBased(pw: CharArray, c: Char) {
        var idx = pw.joinToString("").indexOf(c)
        if (idx >= 4) idx++
        rotateRight(pw, idx + 1)
    }

    private fun rotateRightReversed(pw: CharArray, num: Int) {
        rotateLeft(pw, num)
    }

    private fun rotateLeftReversed(pw: CharArray, num: Int) {
        rotateRight(pw, num)
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
    fun rotateBasedReversedTest() {
        var arr = "xbcdefgh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("hxbcdefg", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("xbcdefgh", arr.joinToString(""))

        arr = "axcdefgh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("ghaxcdef", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("axcdefgh", arr.joinToString(""))

        arr = "abxdefgh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("fghabxde", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("abxdefgh", arr.joinToString(""))

        arr = "abcxefgh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("efghabcx", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("abcxefgh", arr.joinToString(""))

        arr = "abcdxfgh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("cdxfghab", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("abcdxfgh", arr.joinToString(""))

        arr = "abcdexgh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("bcdexgha", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("abcdexgh", arr.joinToString(""))

        arr = "abcdefxh".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("abcdefxh", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("abcdefxh", arr.joinToString(""))

        arr = "abcdefgx".toCharArray()
        rotateBased(arr, 'x')
        assertEquals("xabcdefg", arr.joinToString(""))
        rotateBasedReversed(arr, 'x')
        assertEquals("abcdefgx", arr.joinToString(""))
    }

    @Test
    fun rotateRightReversedTest() {
        val arr = "abcdefgh".toCharArray()
        rotateRight(arr, 10)
        rotateRightReversed(arr, 10)
        assertEquals("abcdefgh", arr.joinToString(""))
    }

    @Test
    fun rotateLeftReversedTest() {
        val arr = "abcdefgh".toCharArray()
        rotateLeft(arr, 10)
        rotateLeftReversed(arr, 10)
        assertEquals("abcdefgh", arr.joinToString(""))
    }

    @Test
    fun permutationsOfStringTest() {
        val perms = ArrayList<String>()
        perms("abcd", "", perms)
        assertTrue(
            perms.containsAll(
                arrayListOf(
                    "abcd",
                    "abdc",
                    "acbd",
                    "acdb",
                    "adbc",
                    "adcb",
                    "bacd",
                    "badc",
                    "bcad",
                    "bcda",
                    "bdac",
                    "bdca",
                    "cabd",
                    "cadb",
                    "cbad",
                    "cbda",
                    "cdab",
                    "cdba",
                    "dabc",
                    "dacb",
                    "dbac",
                    "dbca",
                    "dcab",
                    "dcba"
                )
            )
        )
    }

    @Test
    fun reverseMoveTest() {
        val arr = "abcdefgh".toCharArray()
        move(arr, 1, 5)
        reverseMove(arr, 1, 5)
        assertEquals("abcdefgh", arr.joinToString(""))
    }

    @Test
    fun movePosToLowerIdxPosTest() {
        val arr = "abcdefg".toCharArray()
        move(arr, 5, 1)
        assertEquals("afbcdeg", arr.joinToString(""))
    }

    @Test
    fun movePosToPosTest() {
        val arr = "abcdefg".toCharArray()
        move(arr, 1, 5)
        assertEquals("acdefbg", arr.joinToString(""))
    }

    @Test
    fun reverseReverseTest() {
        val arr = "abcdefgh".toCharArray()
        reverse(arr, 3, 6)
        reverse(arr, 3, 6)
        assertEquals("abcdefgh", arr.joinToString(""))
    }

    @Test
    fun reverseFromToIndexOddTest() {
        val arr = "abcdefg".toCharArray()
        reverse(arr, 2, 5)
        assertEquals("abfedcg", arr.joinToString(""))
    }

    @Test
    fun reverseFromToIndexEvenTest() {
        val arr = "abcdefg".toCharArray()
        reverse(arr, 2, 4)
        assertEquals("abedcfg", arr.joinToString(""))
    }

    @Test
    fun rotateBasedOnPositionOfLetterTest() {
        val arr = "abdec".toCharArray()
        rotateBased(arr, 'b')
        assertEquals("ecabd", arr.joinToString(""))
        val arr2 = "ecabd".toCharArray()
        rotateBased(arr2, 'd')
        assertEquals("decab", arr2.joinToString(""))
    }

    @Test
    fun rotateLeftTest() {
        val arr = "abcd".toCharArray()
        rotateLeft(arr, 1)
        assertEquals("bcda", arr.joinToString(""))
    }

    @Test
    fun rotateRightTest() {
        val arr = "abcd".toCharArray()
        rotateRight(arr, 1)
        assertEquals("dabc", arr.joinToString(""))
    }

    @Test
    fun swapPositionTest() {
        val arr = "abc".toCharArray()
        swapPosition(arr, 0, 2)
        assertEquals("cba", arr.joinToString(""))
    }

    @Test
    fun swapCharsTest() {
        val arr = "abc".toCharArray()
        swapChars(arr, 'a', 'c')
        assertEquals("cba", arr.joinToString(""))
    }

    @Test
    fun sample() {
        val instructions =
            "swap position 4 with position 0\n" + "swap letter d with letter b\n" + "reverse positions 0 through 4\n" + "rotate left 1 step\n" + "move position 1 to position 4\n" + "move position 3 to position 0\n" + "rotate based on position of letter b\n" + "rotate based on position of letter d"
        assertEquals("decab", scramble("abcde", instructions))
    }

    @Test
    fun part1() {
        assertEquals("agcebfdh", scramble("abcdefgh", File("files/2016/day21.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals("afhdbegc", unscramble("fbgdceah", File("files/2016/day21.txt").readText()))
    }

    @Test
    fun part2Bruteforce() {
        assertEquals("afhdbegc", unscrambleBruteforce("fbgdceah", File("files/2016/day21.txt").readText()))
    }
}