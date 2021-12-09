package aoc2021

import com.google.common.collect.Collections2
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day8 {
    private fun countUniqueSegmentOutputNums(input: List<String>): Int = parseInput(input).sumOf {
        it.second.count { num -> num.length == 2 || num.length == 3 || num.length == 4 || num.length == 7 }
    }

    private val validNums = mapOf(
        "abcefg" to 0,
        "cf" to 1,
        "acdeg" to 2,
        "acdfg" to 3,
        "bcdf" to 4,
        "abdfg" to 5,
        "abdefg" to 6,
        "acf" to 7,
        "abcdefg" to 8,
        "abcdfg" to 9
    )

    private fun sumOutputValues(input: List<String>): Int {
        val permutations = Collections2.permutations(listOf("a", "b", "c", "d", "e", "f", "g"))
        return parseInput(input).sumOf { sumOutputValuesLine(it, permutations) }
    }

    private fun sumOutputValuesLine(
        input: Pair<List<String>, List<String>>,
        permutations: Collection<List<String>>
    ): Int {
        for (perm in permutations) {
            val transformedInput = input.first.map { transform(it, perm).toCharArray().sorted().joinToString("") }
            val transformedOutput = input.second.map { transform(it, perm).toCharArray().sorted().joinToString("") }
            val isMatch =
                transformedInput.all { validNums.containsKey(it) } && transformedOutput.all { validNums.containsKey(it) }
            if (isMatch) {
                return transformedOutput.map { validNums[it]!! }.joinToString("").toInt()
            }
        }
        return -1
    }

    private fun transform(num: String, perm: List<String>): String {
        return num.map {
            when (it) {
                'a' -> perm[0]
                'b' -> perm[1]
                'c' -> perm[2]
                'd' -> perm[3]
                'e' -> perm[4]
                'f' -> perm[5]
                'g' -> perm[6]
                else -> throw IllegalArgumentException("Invalid input")
            }
        }.joinToString("")
    }

    private fun parseInput(input: List<String>): List<Pair<List<String>, List<String>>> =
        input.map { it.split(" | ")[0] to it.split(" | ")[1] }
            .map { it.first.split(" ") to it.second.split(" ") }

    @Test
    fun part1() {
        assertEquals(
            26, countUniqueSegmentOutputNums(
                """
be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce""".trimIndent().lines()
            )
        )
        assertEquals(392, countUniqueSegmentOutputNums(File("files/2021/day8.txt").readLines()))
    }

    @Test
    fun part2() {
        assertEquals(
            61229, sumOutputValues(
                """
be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce""".trimIndent().lines()
            )
        )
        assertEquals(1004688, sumOutputValues(File("files/2021/day8.txt").readLines()))
    }
}