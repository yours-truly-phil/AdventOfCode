package aoc2015

import java.io.File

fun main() {
    Day13().also { println(it.part1(File("files/2015/day13.txt").readText(), false)) }
        .also { println(it.part1(File("files/2015/day13.txt").readText(), true)) }
}

class Day13 {
    fun part1(input: String, part2: Boolean): Int {

        val people = input.lines()
            .map { it.split(" ")[0] }
            .distinct()
            .map { it to Person(it) }.toMap().toMutableMap()

        input.lines()
            .forEach {
                val parts = it.split(" ")
                val person = parts[0]
                val nextTo = parts[10].substring(0, parts[10].indexOf("."))
                var amount = parts[3].toInt()
                if (parts[2] == "lose") amount *= -1
                people[person]!!.happiness[nextTo] = amount
            }

        if (part2) {
            val me = Person("Phil")
            people[me.name] = me
            for (entry in people) {
                entry.value.happiness[me.name] = 0
                me.happiness[entry.key] = 0
            }
        }

        val listOfPeople = people.keys.toList()
        val permutations = pow(listOfPeople.size)
        var res = Int.MIN_VALUE
        for (i in 0 until permutations) {
            val seating = i.toString(listOfPeople.size).padStart(listOfPeople.size, '0')
            var possibleSeating = true
            for (p in listOfPeople.indices) {
                if (seating.filter { Integer.parseInt(it.toString()) == p }.count() != 1) {
                    possibleSeating = false
                    break
                }
            }
            if (!possibleSeating) continue

            var happiness = 0
            for (seatNo in seating.indices) {
                val idx = seating[seatNo].toString().toInt()
                val person = listOfPeople[idx]

                val neighborLeft =
                    listOfPeople[seating[(seatNo - 1 + seating.length) % seating.length].toString().toInt()]
                val neighborRight =
                    listOfPeople[seating[(seatNo + 1) % seating.length].toString().toInt()]
                happiness += people[person]!!.happiness[neighborLeft]!!
                happiness += people[person]!!.happiness[neighborRight]!!
            }
            res = maxOf(res, happiness)
        }
        return res
    }

    data class Person(val name: String) {
        val happiness = HashMap<String, Int>()
    }

    private fun pow(other: Int): Long {
        var res = 1L
        repeat(other) {
            res *= other
        }
        return res
    }
}

