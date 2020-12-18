import aoc2020.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    println(
        "Finished in ${
            measureTimeMillis {
                val day1 = GlobalScope.async { runDay1() }
                val day2 = GlobalScope.async { runDay2() }
                val day3 = GlobalScope.async { runDay3() }
                val day4 = GlobalScope.async { runDay4() }
                val day5 = GlobalScope.async { runDay5() }
                val day6 = GlobalScope.async { runDay6() }
                val day7 = GlobalScope.async { runDay7() }
                val day8 = GlobalScope.async { runDay8() }
                val day9 = GlobalScope.async { runDay9() }
                val day10 = GlobalScope.async { runDay10() }
                val day11 = GlobalScope.async { runDay11() }
                val day12 = GlobalScope.async { runDay12() }
                val day12Part2 = GlobalScope.async { runDay11Part2() }
                val day13 = GlobalScope.async { runDay13() }
                val day14 = GlobalScope.async { runDay14() }
                val day15 = GlobalScope.async { runDay15() }
                val day16 = GlobalScope.async { runDay16() }
                val day17 = GlobalScope.async { runDay17() }
                val day17part2 = GlobalScope.async { runDay17Part2() }
                runBlocking {
                    day1.await()
                    day2.await()
                    day3.await()
                    day4.await()
                    day5.await()
                    day6.await()
                    day7.await()
                    day8.await()
                    day9.await()
                    day10.await()
                    day11.await()
                    day12.await()
                    day12Part2.await()
                    day13.await()
                    day14.await()
                    day15.await()
                    day16.await()
                    day17.await()
                    day17part2.await()
                }
            } / 1000f
        }s"
    )
}