package aoc2015

import java.io.File

fun main() {
    Day15().also { println(it.part1(File("files/2015/day15.txt").readText())) }
}

class Day15 {
    fun part1(input: String): Long {
        val ingredients = input.lines().map { Ingredient(it) }
        var total = 0L
        for (x1 in 0..100 - 3) {
            for (x2 in 0..100 - x1 - 2) {
                for (x3 in 0 until 100 - x1 - x2) {
                    val x4 = 100 - x1 - x2 - x3
                    val res = res(ingredients, arrayOf(x1, x2, x3, x4))
                    if(total < res) {
                        println("x1=$x1 x2=$x2 x3=$x3 x4=$x4 total=${x1 + x2 + x3 + x4} res=$res")
                        total = res
                    }
                }
            }
        }

        return total
    }

    fun res(ingr: List<Ingredient>, x: Array<Int>): Long {
        var cap = 0L
        var dur = 0L
        var flav = 0L
        var tex = 0L
        for (i in x.indices) {
            cap += x[i] * ingr[i].cap
            dur += x[i] * ingr[i].dur
            flav += x[i] * ingr[i].fla
            tex += x[i] * ingr[i].tex
        }
        cap = maxOf(0, cap)
        dur = maxOf(0, dur)
        flav = maxOf(0, flav)
        tex = maxOf(0, tex)
        return cap * dur * flav * tex
    }

    class Ingredient(input: String) {
        val name: String
        val cap: Int
        val dur: Int
        val fla: Int
        val tex: Int
        val cal: Int

        init {
            input.split(" ")
                .also {
                    name = it[0].split(":")[0]
                    cap = it[2].split(",")[0].toInt()
                    dur = it[4].split(",")[0].toInt()
                    fla = it[6].split(",")[0].toInt()
                    tex = it[8].split(",")[0].toInt()
                    cal = it[10].split(",")[0].toInt()
                }
        }
    }
}