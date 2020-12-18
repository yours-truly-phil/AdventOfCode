package aoc2020

import aoc2020.Day18.Op.*
import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay18()
}

fun runDay18() {
    val lines = File("aoc2020/day18.txt").readLines()

    val day18 = Day18()
    day18.part1(lines)
    println("day18part1=${micros(Callable { day18.part1(lines) })}")
}

class Day18 {
    fun part1(lines: List<String>): Long {
        return lines.map { calculate(it) }.sum()
    }

    enum class Op {
        PLUS, MINUS, MULTI
    }

    fun calculate(expression: String): Long {
        val charArray = expression.toCharArray()
        var curIdx = 0
        var curOp = PLUS
        var result = 0L
        var curChar: Char
        while (curIdx < charArray.size) {

            curChar = charArray[curIdx]
            if (curChar == '+') curOp = PLUS
            else if (curChar == '-') curOp = MINUS
            else if (curChar == '*') curOp = MULTI
            else if (curChar.toString()
                    .matches("^'0'|[1-9][0-9]*\$".toRegex())
            ) {
                val num = curChar.toString().toInt()
                when (curOp) {
                    PLUS -> result += num
                    MINUS -> result -= num
                    MULTI -> result *= num
                }
            } else if (curChar == '(') {
                val endIdx = curIdx + findParanthesisEnd(expression.substring(curIdx))
                val substring = expression.substring(curIdx + 1, endIdx)
                curIdx = endIdx
                when (curOp) {
                    PLUS -> result += calculate(substring)
                    MINUS -> result -= calculate(substring)
                    MULTI -> result *= calculate(substring)
                }
            }
            curIdx++
        }
        return result
    }

    /**
     * expression needs to start with '('
     */
    fun findParanthesisEnd(expression: String): Int {
        val charArr = expression.toCharArray()
        var countOpen = 0
        for (i in charArr.indices) {
            if (charArr[i] == '(') countOpen++
            else if (charArr[i] == ')') {
                countOpen--
                if (countOpen == 0) {
                    return i
                }
            }
        }
        throw RuntimeException("missing $countOpen closing ')' in $expression")
    }
}