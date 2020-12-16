package aoc2020

import micros
import java.io.File
import java.util.concurrent.Callable

fun main() {
    runDay16()
}

fun runDay16() {
    val content = File("aoc2020/day16.txt").readText()

    val day16 = Day16(content)

    println("day16part1=${micros(Callable { day16.part1() })}")
    println("day16part2=${micros(Callable { day16.part2() })}")
}

class Day16(input: String) {
    private val constraints: List<Constraint>
    private val myTicket: Ticket
    private val nearbyTickets: List<Ticket>
    private var ticketNumsValidated = false

    init {
        val parts = input.split("\n\n")
        constraints = parts[0].lines().map { Constraint(it) }
        myTicket = Ticket(parts[1].split("\n")[1])
        nearbyTickets = parts[2].lines().subList(1, parts[2].lines().size).map { Ticket(it) }
    }

    fun part1(): Long {
        val invalidNums = validateTicketNums()
        ticketNumsValidated = true
        return invalidNums.sumOf { i: Int -> i.toLong() }
    }

    fun part2(): Long {
        if (!ticketNumsValidated) part1()

        val validTickets = nearbyTickets.filter { it.isValid }.plus(myTicket)

        val possibleIndices = HashMap<Int, Boolean>()
        for (i in myTicket.nums.indices) {
            possibleIndices[i] = true
        }

        val unIdentified = ArrayList<Constraint>()
        for (constraint in constraints) {
            for (i in myTicket.nums.indices) {
                constraint.indices.add(i)
            }
            for (ticket in validTickets) {
                for ((idx, num) in ticket.nums.withIndex()) {
                    if (!constraint.isValid(num)) {
                        constraint.indices.remove(idx)
                    }
                }
            }
            unIdentified.add(constraint)
        }

        return identifyIndexForConstraints(unIdentified)
            .filter { it.name.startsWith("departure") }
            .map { it.indices.elementAt(0).toLong() }
            .fold(1L) { acc, l -> acc * myTicket.nums[l.toInt()] }
    }

    private fun identifyIndexForConstraints(unIdentified: ArrayList<Constraint>): ArrayList<Constraint> {
        val identified = ArrayList<Constraint>()
        while (unIdentified.size > 0) {
            for (constraint in unIdentified) {
                if (constraint.indices.size == 1) {
                    val idx = constraint.indices.elementAt(0)
                    identified.add(constraint)
                    unIdentified.remove(constraint)
                    unIdentified.forEach { it.indices.remove(idx) }
                    break
                }
            }
        }
        return identified
    }

    private fun validateTicketNums(): ArrayList<Int> {
        val invalidNums = ArrayList<Int>()
        for (ticket in nearbyTickets) {
            for (num in ticket.nums) {
                var isValidNum = false
                for (constraint in constraints) {
                    if (constraint.isValid(num)) {
                        isValidNum = true
                    }
                }
                if (!isValidNum) {
                    invalidNums.add(num)
                    ticket.isValid = false
                }
            }
        }
        return invalidNums
    }
}

class Ticket(s: String) {
    val nums = s.split(",").map { it.toInt() }
    var isValid = true
}

class Constraint(s: String) {
    val name: String
    val indices = HashSet<Int>()
    private val ll: Int
    private val lu: Int
    private val ul: Int
    private val uu: Int

    init {
        val parts = s.split(":")
        name = parts[0]
        val numPairs = parts[1].split(" ")
        val numsLower = numPairs[1].split("-")
        ll = numsLower[0].toInt()
        lu = numsLower[1].toInt()
        val numsUpper = numPairs[3].split("-")
        ul = numsUpper[0].toInt()
        uu = numsUpper[1].toInt()
    }

    fun isValid(i: Int): Boolean {
        return i in ll..lu || i in ul..uu
    }
}