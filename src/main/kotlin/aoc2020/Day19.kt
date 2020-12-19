package aoc2020

import java.io.File

fun main() {
    val day19 = Day19(File("files/2020/day19.txt").readText())
    println(day19.part1())
    val day19part2 = Day19(File("files/2020/day19Part2.txt").readText())
    println(day19part2.part2())
}

class Day19(content: String) {

    val rulesMap: HashMap<Int, Rule>
    val codes: List<String>

    init {
        val fileParts = content.split("\n\n")
        val rulesPart = fileParts[0]
        codes = fileParts[1].lines()

        rulesMap = HashMap()
        rulesPart.lines()
                .map { Rule(it) }
                .map { it.no to it }.toMap(rulesMap)

        for (rule in rulesMap.values) {
            for (listOfRuleNums in rule.links) {
                val linkedRuleList = ArrayList<Rule>()
                for (ruleNum in listOfRuleNums) {
                    val linkedRule = rulesMap[ruleNum]!!
                    linkedRuleList.add(linkedRule)
                }
                rule.rules.add(linkedRuleList)
            }
        }
    }

    fun part1(): Int {
        val rule0 = rulesMap[0]!!
        var count = 0
        val regex = "^${rule0.toRegex(rulesMap)}$".toRegex()
//        println(regex)
        for (code in codes) {
            if (regex.matches(code)) count++
        }
        return count
    }

    fun part2(): Int {
        val rule0 = rulesMap[0]!!
        var count = 0
        val regex = "^${rule0.toRegexPart2(rulesMap)}$".toRegex()
//        println(regex)
        for (code in codes) {
            if (regex.matches(code)) count++
        }
        return count
    }

    class Rule(s: String) {
        val no: Int
        val links = ArrayList<List<Int>>()
        val rules = ArrayList<List<Rule>>()
        var value: String = ""

        init {
            var parts = s.split(":")
            no = parts[0].toInt()
            when {
                parts[1].contains("a") -> value = "a"
                parts[1].contains("b") -> value = "b"
                else -> {
                    parts = parts[1].split("|")
                    parts.mapTo(links) { nums ->
                        nums.split(" ").filter { it.isNotEmpty() }
                                .map { it.toInt() }.toList()
                    }
                }
            }
        }

        fun toRegexPart2(rulesMap: Map<Int, Rule>): String {
            if (value.isNotEmpty()) {
                return value
            } else {
                var res = "("
                when (no) {
                    8 -> {
                        res += "${rules[0][0].toRegexPart2(rulesMap)}+"
                    }
                    11 -> {
                        val left = rules[0][0].toRegexPart2(rulesMap)
                        val right = rules[0][1].toRegexPart2(rulesMap)
                        res += "($left$right)"
                        for (i in 2..10) {
                            res += "|("
                            for (j in 1..i) {
                                res += left
                            }
                            for (j in 1..i) {
                                res += right
                            }
                            res += ")"
                        }
                    }
                    else -> {
                        res += rules.joinToString(separator = "|") {
                            "(${it.map { it.toRegexPart2(rulesMap) }.joinToString(separator = "") { it }})"
                        }
                    }
                }
                res += ")"
                return res
            }
        }

        fun toRegex(rulesMap: Map<Int, Rule>): String {
            return if (value.isNotEmpty()) {
                value
            } else {
                var res = "("
                res += rules.joinToString(separator = "|") {
                    "(${it.map { it.toRegex(rulesMap) }.joinToString(separator = "") { it }})"
                }
                res += ")"
                res
            }
        }

        override fun toString(): String {
            return "Rule $no $value links=$links)"
        }
    }
}