package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day9 {
    private fun totalScore(input: String): Int {
        val cleanedUp = cleanup(input)
        val count = countGroups(cleanedUp)
        var total = 0
        count.forEach { total += it.key * it.value }
        return total
    }

    private fun countGroups(input: String): HashMap<Int, Int> {
        val counts = HashMap<Int, Int>()
        var lvl = 0
        for (c in input) {
            if (c == '{') {
                lvl++
                counts.computeIfAbsent(lvl) { 0 }
                counts[lvl] = counts[lvl]!! + 1
            } else if (c == '}') {
                lvl--
            }
        }
        return counts
    }

    private fun nonCanceledGarbage(s: String): Int {
        val removeExclamation = removeExclamation(removeIgnoredChars(s))
        val removeTrash = removeTrashLeaveOpenClose(removeExclamation)
        return removeExclamation.length - removeTrash.length
    }

    private fun removeTrashLeaveOpenClose(input: String): String {
        val sb = StringBuilder()
        var ignore = false
        for (c in input) {
            if (c == '<' && !ignore) {
                ignore = true
                sb.append(c)
            } else if (c == '>' && ignore) {
                ignore = false
                sb.append(c)
            } else {
                if (!ignore) {
                    sb.append(c)
                }
            }
        }
        return sb.toString()
    }

    private fun cleanup(input: String): String {
        return removeTrash(removeExclamation(removeIgnoredChars(input)))
    }

    private fun removeTrash(input: String): String {
        val sb = StringBuilder()
        var ignore = false
        for (c in input) {
            when {
                c == '<' && !ignore -> ignore = true
                c == '>' && ignore -> ignore = false
                else -> if (!ignore) sb.append(c)
            }
        }
        return sb.toString()
    }

    private fun removeIgnoredChars(input: String): String {
        val list = input.toMutableList()
        var i = 0
        while (i < list.size - 1) {
            if (list[i] == '!') {
                list.removeAt(i + 1)
            }
            i++
        }
        return list.joinToString("")
    }

    private fun removeExclamation(input: String): String {
        return input.replace("!", "")
    }

    @Test
    fun `count non-canceled characters within garbage`() {
        assertEquals(0, nonCanceledGarbage("<>"))
        assertEquals(17, nonCanceledGarbage("<random characters>"))
        assertEquals(3, nonCanceledGarbage("<<<<>"))
        assertEquals(2, nonCanceledGarbage("<{!>}>"))
        assertEquals(0, nonCanceledGarbage("<!!>"))
        assertEquals(0, nonCanceledGarbage("<!!!>>"))
        assertEquals(10, nonCanceledGarbage("<{o\"i!a,<{i<a>"))
    }

    @Test
    fun `count groups`() {
        val counts = countGroups("{{{},{},{{}}}}")
        assertEquals(1, counts[1])
        assertEquals(1, counts[2])
        assertEquals(3, counts[3])
        assertEquals(1, counts[4])
    }

    @Test
    fun `cleanup trash`() {
        assertEquals("", cleanup("<{o\"i!a,<{i<a>"))
        assertEquals("", cleanup("<random characters>"))
        assertEquals("{{},{},{},{}}", cleanup("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
    }

    @Test
    fun `remove ignored chars`() {
        assertEquals("<!!>", removeIgnoredChars("<!!!>>"))
        assertEquals("<!>", removeIgnoredChars("<!!>"))
    }

    @Test
    fun `remove exclamation mark`() {
        assertEquals("<>", removeExclamation("<!>"))
        assertEquals("<>", removeExclamation("<!!>"))
    }

    @Test
    fun `calculate score`() {
        assertEquals(1, totalScore("{}"))
        assertEquals(6, totalScore("{{{}}}"))
        assertEquals(5, totalScore("{{},{}}"))
        assertEquals(16, totalScore("{{{},{},{{}}}}"))
        assertEquals(1, totalScore("{<a>,<a>,<a>,<a>}"))
        assertEquals(9, totalScore("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
        assertEquals(9, totalScore("{{<!!>},{<!!>},{<!!>},{<!!>}}"))
        assertEquals(3, totalScore("{{<a!>},{<a!>},{<a!>},{<ab>}}"))
    }

    @Test
    fun part1() {
        assertEquals(10820, totalScore(File("files/2017/day9.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(5547, nonCanceledGarbage(File("files/2017/day9.txt").readText()))
    }
}