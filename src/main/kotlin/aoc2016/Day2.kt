package aoc2016

import java.io.File

fun main() {
    Day2().apply { println(part1(File("files/2016/day2.txt").readText())) }
        .apply { println(part2(File("files/2016/day2.txt").readText())) }
}

class Day2 {
    fun part1(input: String): String {
        var code = ""
        input.lines()
            .forEach {
                var key = 5
                it.forEach { dir ->
                    when (dir) {
                        'U' -> if (key > 3) key -= 3
                        'D' -> if (key < 7) key += 3
                        'L' -> if (key % 3 != 1) key--
                        'R' -> if (key % 3 != 0) key++
                    }
                }
                code += key.toString()
            }
        return code
    }

    fun part2(input: String): String {
        val keys = hashMapOf("1" to Key("1")
            .apply { down = "3" },
            "2" to Key("2").apply {
                right = "3"
                down = "6"
            },
            "3" to Key("3").apply {
                up = "1"
                left = "2"
                right = "4"
                down = "7"
            },
            "4" to Key("4").apply {
                left = "3"
                down = "8"
            },
            "5" to Key("5").apply {
                right = "6"
            },
            "6" to Key("6").apply {
                up = "2"
                left = "5"
                right = "7"
                down = "A"
            },
            "7" to Key("7").apply {
                up = "3"
                left = "6"
                right = "8"
                down = "B"
            },
            "8" to Key("8").apply {
                up = "4"
                left = "7"
                right = "9"
                down = "C"
            },
            "9" to Key("9").apply {
                left = "8"
            },
            "A" to Key("A").apply {
                up = "6"
                right = "B"
            },
            "B" to Key("B").apply {
                up = "7"
                left = "A"
                right = "C"
                down = "D"
            },
            "C" to Key("C").apply {
                up = "8"
                left = "B"
            },
            "D" to Key("D").apply {
                up = "B"
            })

        var code = ""
        input.lines().forEach {
            var key = keys["5"]
            it.forEach { dir ->
                when (dir) {
                    'U' -> key = keys[key!!.up]
                    'D' -> key = keys[key!!.down]
                    'L' -> key = keys[key!!.left]
                    'R' -> key = keys[key!!.right]
                }
            }
            code += key!!.name
        }
        return code
    }

    class Key(val name: String) {
        var up = this.name
        var down = this.name
        var left = this.name
        var right = this.name
    }
}