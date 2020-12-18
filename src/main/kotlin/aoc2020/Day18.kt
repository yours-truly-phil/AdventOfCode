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
//    day18.part1(lines)
    println("day18part1=${micros(Callable { day18.part1(lines) })}")
    val day18Part2 = Day18Part2()
    println("day18part2=${micros(Callable { day18Part2.part2(lines) })}")
}

class Day18 {
    fun part1(lines: List<String>): Long {
        return lines.map { calculate(it) }.sum()
    }

    enum class Op {
        PLUS, MINUS, MULTI
    }

    /**
     * expression needs to start with '(' and returns idx of its closing ')'
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

    fun calculate(expression: String): Long {
        val charArray = expression.toCharArray()
        var curIdx = 0
        var curOp = PLUS
        var result = 0L
        var curChar: Char
        while (curIdx < charArray.size) {
            curChar = charArray[curIdx]
            when {
                curChar == '+' -> curOp = PLUS
                curChar == '-' -> curOp = MINUS
                curChar == '*' -> curOp = MULTI
                curChar.toString()
                        .matches("^'0'|[1-9][0-9]*\$".toRegex()) -> {
                    val num = curChar.toString().toInt()
                    when (curOp) {
                        PLUS -> result += num
                        MINUS -> result -= num
                        MULTI -> result *= num
                    }
                }
                curChar == '(' -> {
                    val endIdx = curIdx + findParanthesisEnd(expression.substring(curIdx))
                    val substring = expression.substring(curIdx + 1, endIdx)
                    curIdx = endIdx
                    when (curOp) {
                        PLUS -> result += calculate(substring)
                        MINUS -> result -= calculate(substring)
                        MULTI -> result *= calculate(substring)
                    }
                }
            }
            curIdx++
        }
        return result
    }
}

class Day18Part2 {
    fun part2(lines: List<String>): Long {
        return lines.map { calculate(it) }.sum()
    }

    fun calculate(str: String): Long {
        val charList = str.toMutableList()
        var i = 0
        while (i < charList.size) {
            when {
                charList[i] == '+' -> {
                    val idxLeft = idxNewBracketLeft(charList, i)
                    charList.add(idxLeft, '(')
                    i++
                    val idxRight = idxNewBracketRight(charList, i)
                    charList.add(idxRight + 1, ')')
                    i++
                }
            }
            i++
        }

        return Day18().calculate(charList.joinToString(""))
    }

    fun idxNewBracketRight(charList: List<Char>, idx: Int): Int {
        var countOpen = 0
        var pastFirstNum = false
        for (i in idx + 1 until charList.size) when {
            charList[i] != ' ' -> {
                when {
                    charList[i] == '(' -> countOpen++
                    charList[i] == ')' -> countOpen--
                    charList[i].toString()
                            .matches("^'0'|[1-9][0-9]*\$".toRegex()) -> {
                        pastFirstNum = true
                    }
                }

                if (countOpen == 0 && pastFirstNum) {
                    return i
                }
            }
        }
        return -1
    }

    fun idxNewBracketLeft(charList: List<Char>, idx: Int): Int {
        var countClosing = 0
        var pastFirstNum = false
        for (i in idx - 1 downTo 0) when {
            charList[i] != ' ' -> {
                when {
                    charList[i] == ')' -> countClosing++
                    charList[i] == '(' -> countClosing--
                    charList[i].toString()
                            .matches("^'0'|[1-9][0-9]*\$".toRegex()) -> {
                        pastFirstNum = true
                    }
                }

                if (countClosing == 0 && pastFirstNum) {
                    return i
                }
            }
        }
        return -1
    }
}