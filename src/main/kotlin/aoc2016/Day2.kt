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
        val keys = hashMapOf("1" to Key("1").also { it.down = "3" },
            "2" to Key("2").also {
                it.right = "3"
                it.down = "6"
            },
            "3" to Key("3").also {
                it.up = "1"
                it.left = "2"
                it.right = "4"
                it.down = "7"
            },
            "4" to Key("4").also {
                it.left = "3"
                it.down = "8"
            },
            "5" to Key("5").also {
                it.right = "6"
            },
            "6" to Key("6").also {
                it.up = "2"
                it.left = "5"
                it.right = "7"
                it.down = "A"
            },
            "7" to Key("7").also {
                it.up = "3"
                it.left = "6"
                it.right = "8"
                it.down = "B"
            },
            "8" to Key("8").also {
                it.up = "4"
                it.left = "7"
                it.right = "9"
                it.down = "C"
            },
            "9" to Key("9").also {
                it.left = "8"
            },
            "A" to Key("A").also {
                it.up = "6"
                it.right = "B"
            },
            "B" to Key("B").also {
                it.up = "7"
                it.left = "A"
                it.right = "C"
                it.down = "D"
            },
            "C" to Key("C").also {
                it.up = "8"
                it.left = "B"
            },
            "D" to Key("D").also {
                it.up = "B"
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