package aoc2017

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.abs
import kotlin.test.assertEquals

class Day20 {
    private fun closestParticle(input: String): Int {
        val particles = input.lines()
            .mapIndexed { index, s -> Particle(index, s) }.toTypedArray()

        particles.sort()
        return particles.first().idx
    }

    class Particle(val idx: Int, input: String) : Comparable<Particle> {
        val p: V3i
        val v: V3i
        val a: V3i

        init {
            val parts = input.split(", ")
            val pp = parts[0].substring(parts[0].indexOf("<") + 1,
                parts[0].indexOf(">"))
                .split(",")
            p = V3i(pp[0].toLong(), pp[1].toLong(), pp[2].toLong())
            val pv = parts[1].substring(parts[1].indexOf("<") + 1,
                parts[1].indexOf(">"))
                .split(",")
            v = V3i(pv[0].toLong(), pv[1].toLong(), pv[2].toLong())
            val pa = parts[2].substring(parts[2].indexOf("<") + 1,
                parts[2].indexOf(">"))
                .split(",")
            a = V3i(pa[0].toLong(), pa[1].toLong(), pa[2].toLong())
        }

        override fun toString(): String {
            return "Particle(idx=$idx, p=$p, v=$v, a=$a)"
        }

        override fun compareTo(o: Particle): Int {
            if (a.dist() != o.a.dist()) {
                return a.dist().compareTo(o.a.dist())
            } else {
                return if (v.dist() > (v + a).dist()) {
                    if (o.v.dist() > (o.v + o.a).dist()) {
                        v.dist().compareTo(o.v.dist())
                    } else {
                        -1
                    }
                } else {
                    if (o.v.dist() > (o.v + o.a).dist()) {
                        1
                    } else {
                        -1
                    }
                }
            }
        }

        operator fun V3i.plus(o: V3i): V3i = V3i(x + o.x, y + o.y, z + o.z)
    }

    data class V3i(var x: Long, var y: Long, var z: Long) {
        fun dist(): Long = abs(x) + abs(y) + abs(z)
    }

    operator fun V3i.plusAssign(o: V3i) {
        x += o.x
        y += o.y
        z += o.z
    }

    operator fun V3i.plus(o: V3i): V3i = V3i(x + o.x, y + o.y, z + o.z)

    @Test
    fun sample() {
        assertEquals(0, closestParticle(
            "p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>\n" +
                    "p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>"))
    }

    @Test
    fun part1() {
        assertEquals(457, closestParticle(File("files/2017/day20.txt").readText()))
    }
}