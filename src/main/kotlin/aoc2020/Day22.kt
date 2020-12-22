package aoc2020

import java.io.File
import java.util.*
import kotlin.collections.HashSet

fun main() {
    val day22 = Day22(File("files/2020/day22.txt").readText())
    println(day22.part1())
    println(day22.part2())
}

class Day22(val input: String) {
    private lateinit var deck1: Deck
    private lateinit var deck2: Deck

    fun init(input: String) {
        val parts = input.split("\n\n")
        deck1 = Deck(parts[0])
        deck2 = Deck(parts[1])
    }

    fun part1(): Int {
        init(input)
        play()
        return getLeader().getPoints()
    }

    private fun play() {
        while (!isGameEnd(deck1, deck2)) {
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

    private fun isGameEnd(d1: Deck, d2: Deck): Boolean {
        return d1.cards.size == 0 || d2.cards.size == 0
    }

    private fun getLeader(): Deck {
        return if (deck1.getPoints() > deck2.getPoints()) deck1 else deck2
    }

    fun part2(): Int {
        init(input)
        playPart2(deck1, deck2, HashSet(), 1)
        return getLeader().getPoints()
    }

    private fun playPart2(d1: Deck, d2: Deck, memo: HashSet<String>, lvl: Int): String {
        var round = 0
        var winner = d1.name
        while (!isGameEnd(d1, d2)) {
            round++
            if ("${d1.hash()}|${d2.hash()}" in memo) return d1.name
            else memo += "${d1.hash()}|${d2.hash()}"

            val card1 = d1.playCard()
            val card2 = d2.playCard()
            winner = d1.name
            if (shouldPlaySubGame(card1, d1, card2, d2)) {
                winner = playPart2(d1.copyDeck(card1), d2.copyDeck(card2), HashSet(), lvl + 1)
            } else if (card2 > card1) {
                winner = d2.name
            }
            if (winner == d1.name) {
                d1.takeCards(card1, card2)
            } else {
                d2.takeCards(card2, card1)
            }
        }
        return winner
    }

    private fun shouldPlaySubGame(c1: Int, d1: Deck, c2: Int, d2: Deck): Boolean {
        return c1 <= d1.noCardsInDeck() && c2 <= d2.noCardsInDeck()
    }

    override fun toString(): String {
        return "Day22(deck1=$deck1, deck2=$deck2)"
    }

    class Deck(input: String) {
        val name: String
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

        fun noCardsInDeck(): Int {
            return cards.size
        }

        fun getPoints(): Int {
            return cards.mapIndexed { idx, card -> (cards.size - idx) * card }
                .sum()
        }

        fun copyDeck(num: Int): Deck {
            val newDeck = Deck(name)
            for (i in 0 until num) {
                newDeck.cards.add(cards[i])
            }
            return newDeck
        }

        fun hash(): String {
            return cards.joinToString(",")
        }

        override fun toString(): String {
            return "Deck(name='$name', points=${getPoints()}, cards=$cards)"
        }
    }
}