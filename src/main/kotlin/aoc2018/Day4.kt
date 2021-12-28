package aoc2018

import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

class Day4 {
    private fun guardIdMulByMinute(input: String): Int {
        val sleeps = foo(input)
        val minutesAsleep = IntArray(60)
        val maxSleepGuard = sleeps.maxByOrNull { it.value.sumOf { s -> s.to - s.from } }!!
        maxSleepGuard.value
            .forEach {
                for (i in it.from until it.to) {
                    minutesAsleep[i] = minutesAsleep[i] + 1
                }
            }
        return maxSleepGuard.key * minutesAsleep.indexOf(minutesAsleep.maxOf { it })
    }

    private fun foo(input: String): HashMap<Int, MutableList<Sleep>> {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val lines = input.lines()
            .map {
                LocalDateTime.parse(it.substring(it.indexOf("[") + 1, it.indexOf("]")), dtf) to it
            }.sortedBy { it.first }

        val sleeps = HashMap<Int, MutableList<Sleep>>()
        var curGuard = 0
        var curSleep = Sleep(-1, -1, -1)
        lines.forEach {
            when {
                it.second.contains("Guard") -> {
                    curGuard = it.second.split("#")[1].split(" ")[0].toInt()
                    sleeps.computeIfAbsent(curGuard) { ArrayList() }
                }
                it.second.contains("falls") -> {
                    curSleep = Sleep(curGuard, it.first.dayOfYear, it.first.minute)
                        .also { sleep -> sleeps[curGuard]!!.add(sleep) }
                }
                it.second.contains("wakes") ->
                    curSleep.to = it.first.minute
            }
        }
        return sleeps
    }

    private fun guardMostFrequentlyAsleep(input: String): Int {
        val sleeps = foo(input)
        val sleepMap = HashMap<Int, IntArray>()
        sleeps.forEach { (t, u) ->
            sleepMap.computeIfAbsent(t) { IntArray(60) }
            u.forEach {
                for (i in it.from until it.to) {
                    sleepMap[t]!![i] = sleepMap[t]!![i] + 1
                }
            }
        }
        val maxSleep = sleepMap.maxByOrNull { it.value.maxOf { i -> i } }!!
        return maxSleep.key * maxSleep.value.indexOf(maxSleep.value.maxOf { it })
    }

    class Sleep(private val guard: Int, private val dayOfYear: Int, val from: Int) {
        var to: Int = -1

        override fun toString(): String {
            return "Sleep(guard=$guard, dayOfYear=$dayOfYear, from=$from, to=$to)"
        }
    }

    @Test
    fun sample() {
        assertEquals(240, guardIdMulByMinute(sampleInput))
    }

    @Test
    fun part2Sample() {
        assertEquals(4455, guardMostFrequentlyAsleep(sampleInput))
    }

    @Test
    fun part2() {
        assertEquals(65854, guardMostFrequentlyAsleep(File("files/2018/day4.txt").readText()))
    }

    @Test
    fun part1() {
        assertEquals(99911, guardIdMulByMinute(File("files/2018/day4.txt").readText()))
    }
}

const val sampleInput = """[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up"""