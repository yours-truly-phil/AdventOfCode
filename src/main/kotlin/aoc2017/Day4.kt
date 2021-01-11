package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

class Day4 {
    fun noPassphrases(input: String): Int {
        val passphrases = input.lines().map { it.split(" ") }
        var count = 0
        for (phrase in passphrases) {
            val set = HashSet<String>()
            var valid = true
            for (word in phrase) {
                if (set.contains(word)) {
                    valid = false
                    break
                } else {
                    set.add(word)
                }
            }
            if (valid) {
                count++
            }
        }
        return count
    }

    @Test
    fun part1() {
        assertEquals(477, noPassphrases(File("files/2017/day4.txt").readText()))
    }
}