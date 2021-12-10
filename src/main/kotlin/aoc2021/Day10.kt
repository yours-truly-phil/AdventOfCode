package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day10 {
    private fun solvePart1(input: List<String>): Long = input.mapNotNull { getFirstIllegalChar(it) }.sumOf {
        when (it) {
            ')' -> 3L
            ']' -> 57L
            '}' -> 1197L
            '>' -> 25137L
            else -> throw IllegalArgumentException("Unknown character: $it")
        }
    }

    private fun getFirstIllegalChar(line: String): Char? {
        val deque = ArrayDeque<Char>()
        line.forEach {
            when (it) {
                '(' -> deque.addFirst(')')
                '[' -> deque.addFirst(']')
                '{' -> deque.addFirst('}')
                '<' -> deque.addFirst('>')
                ')', ']', '}', '>' -> if (deque.removeFirst() != it) return it
                else -> throw IllegalArgumentException("Unexpected character: $it")
            }
        }
        return null
    }

    private fun solvePart2(input: List<String>): Long {
        val scores = input.filter { getFirstIllegalChar(it) == null }
            .map { getMissingChars(it) }
            .map { getTotalScore(it) }
            .sorted()
        return scores[scores.size / 2]
    }

    private fun getMissingChars(line: String): ArrayDeque<Char> {
        val deque = ArrayDeque<Char>()
        line.forEach {
            when (it) {
                '(' -> deque.addFirst(')')
                '[' -> deque.addFirst(']')
                '{' -> deque.addFirst('}')
                '<' -> deque.addFirst('>')
                ')', ']', '}', '>' -> deque.removeFirst()
                else -> throw IllegalArgumentException("Unexpected character: $it")
            }
        }
        return deque
    }

    private fun getTotalScore(deque: ArrayDeque<Char>): Long {
        var score = 0L
        while (deque.isNotEmpty()) {
            score *= 5
            val c = deque.removeFirst()
            score += when (c) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> throw IllegalArgumentException("Unexpected character: $c")
            }
        }
        return score
    }

    @Test
    fun part1() {
        assertEquals('}', getFirstIllegalChar("{([(<{}[<>[]}>{[]{[(<()>"))
        assertEquals(')', getFirstIllegalChar("[[<[([]))<([[{}[[()]]]"))
        assertEquals(']', getFirstIllegalChar("[{[{({}]{}}([{[{{{}}([]"))
        assertEquals(')', getFirstIllegalChar("[<(<(<(<{}))><([]([]()"))
        assertEquals('>', getFirstIllegalChar("<{([([[(<>()){}]>(<<{{"))
        assertEquals(
            26397, solvePart1(
                """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]""".trimIndent().lines()
            )
        )
        assertEquals(392097, solvePart1(File("files/2021/day10.txt").readLines()))
    }

    @Test
    fun part2() {
        assertEquals(
            288957, solvePart2(
                """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]""".trimIndent().lines()
            )
        )
        assertEquals(4263222782, solvePart2(File("files/2021/day10.txt").readLines()))
    }
}