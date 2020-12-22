package aoc2020

import java.io.File
import java.util.*

fun main() {
    val day22 = Day22(File("files/2020/day22.txt").readText())
    println(day22.part1())
    println(day22.part2())
}

class Day22(input: String) {
    private val deck1: Deck
    private val deck2: Deck

    init {
        val parts = input.split("\n\n")
        deck1 = Deck(parts[0])
        deck2 = Deck(parts[1])
    }

    fun part1(): Int {
        play()
        return getLeader().getPoints()
    }

    private fun play() {
        while (!isGameEnd()) {
            playRound()
        }
    }

    private fun playRound() {
        val card1 = deck1.playCard()
        val card2 = deck2.playCard()
        when {
            card1 > card2 -> {
                deck1.takeCards(card1, card2)
            }
            card2 > card1 -> {
                deck2.takeCards(card2, card1)
            }
            else -> {
                deck1.takeCards(card1)
                deck2.takeCards(card2)
            }
        }
    }

    private fun isGameEnd(): Boolean {
        return deck1.cards.size == 0 || deck2.cards.size == 0
    }

    private fun getLeader(): Deck {
        return if (deck1.getPoints() > deck2.getPoints()) deck1 else deck2
    }

    fun part2(): Any? {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "Day22(deck1=$deck1, deck2=$deck2)"
    }

    class Deck(input: String) {
        private val name: String
        val cards = LinkedList<Int>()

        init {
            val lines = input.lines()
            name = lines[0]
            for (i in 1 until lines.size) {
                cards.addLast(lines[i].toInt())
            }
        }

        fun playCard(): Int {
            return cards.removeFirst()
        }

        fun takeCards(card: Int) {
            cards.addLast(card)
        }

        fun takeCards(card1: Int, card2: Int) {
            cards.addLast(card1)
            cards.addLast(card2)
        }

        fun getPoints(): Int {
            return cards.mapIndexed { idx, card -> (cards.size - idx) * card }
                .sum()
        }

        override fun toString(): String {
            return "Deck(name='$name', points=${getPoints()}, cards=$cards)"
        }
    }
}