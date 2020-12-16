package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable
import java.util.function.Predicate

fun main() {
    runDay4()
}

fun runDay4() {
    val lines = File("aoc2020/day4.txt").readLines()

    println("day4part1=${micros(Callable { day4part1(lines) })}")
    println("day4part2=${micros(Callable { day4part2(lines) })}")
}

val passportChecks = listOf<Pair<String, Predicate<String>>>(
    "byr" to Predicate { it.length == 4 && it.toInt() in 1920..2002 },
    "iyr" to Predicate { it.length == 4 && it.toInt() in 2010..2020 },
    "eyr" to Predicate { it.length == 4 && it.toInt() in 2020..2030 },
    "hgt" to Predicate {
        it.endsWith("cm") && it.substring(0, it.indexOf("cm")).toInt() in 150..193
                || (it.endsWith("in") && it.substring(0, it.indexOf("in")).toInt() in 59..76)
    },
    "hcl" to Predicate { it.matches("^#[0-9a-f]{6}$".toRegex()) },
    "ecl" to Predicate {
        it == "amb" || it == "blu" || it == "brn" ||
                it == "gry" || it == "grn" || it == "hzl" || it == "oth"
    },
    "pid" to Predicate { it.matches("^[0-9]{9}$".toRegex()) }
)

fun day4part1(lines: List<String>): Long {
    return parsePassports(lines)
        .stream()
        .filter { isValidPassport(it) }
        .count()
}

fun day4part2(lines: List<String>): Long {
    return parsePassports(lines)
        .stream()
        .filter { isValidPassport2(it) }
        .count()
}

private fun isValidPassport(passport: Passport): Boolean {
    for (check in passportChecks) when {
        !passport.fields.containsKey(check.first) -> return false
    }
    return true
}

private fun isValidPassport2(passport: Passport): Boolean {
    for (check in passportChecks) when {
        !passport.fields.containsKey(check.first) -> return false
        passport.fields[check.first].isNullOrBlank() -> return false
        !check.second.test(passport.fields[check.first]!!) -> return false
    }
    return true
}

private fun parsePassports(lines: List<String>): ArrayList<Passport> {
    val passports = arrayListOf<Passport>()
    var cur = 0
    while (cur < lines.size) {
        val pass = Passport()
        while (cur < lines.size && lines[cur].isNotBlank()) {
            lines[cur].split(" ").forEach {
                val vals = it.split(":")
                pass.fields[vals[0]] = vals[1]
            }
            cur++
        }
        passports += pass
        cur++
    }
    return passports
}

class Passport {
    val fields = hashMapOf<String, String>()
}