import java.io.File
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.HashMap

fun main() {
    val lines = File("aoc2020/day14.txt").readLines()

    println("day14part1=${micros(Callable { day14part1(lines) })}")
    println("day14part2=${micros(Callable { day14part2(lines) })}")
}

fun day14part1(lines: List<String>): Long {
    val mem = HashMap<Int, Long>()
    var ones = 0L
    var zeros = Long.MAX_VALUE
    for (line in lines) {
        if (line[1] == 'a') {
            val mask = line.substring(7)
            ones = mask.replace("X", "0").toLong(2)
            zeros = mask.replace("X", "1").toLong(2)
        } else {
            val page = line.substring(4, line.indexOf("]")).toInt()
            val value = line.substring(line.lastIndexOf(" ") + 1).toLong()
            mem[page] = (value or ones) and zeros
        }
    }
    return mem.values.sum()
}

fun day14part2(lines: List<String>): Long {
    val result = HashMap<Long, Long>()
    var mask = "000000000000000000000000000000000000"
    for (line in lines) {
        if (line[1] == 'a') {
            mask = line.substring(7)
        } else {
            val address = "%36s".format(
                line.substring(4, line.indexOf("]"))
                    .toLong().toString(2)
            ).replace(" ", "0")
            val value = line.substring(line.lastIndexOf(" ") + 1).toLong()

            val possibleAddresses = getAddresses(address, mask)
            for (addr in possibleAddresses) {
                result[addr] = value
            }
        }
    }
    return result.values.sum()
}

fun getAddresses(address: String, mask: String): List<Long> {
    val sb = StringBuilder()
    for (i in address.indices) {
        if (mask[i] != '0') sb.append(mask[i])
        else sb.append(address[i])
    }
    val maskAddr = sb.toString()
    return possibleAddresses("", maskAddr)
}

fun possibleAddresses(pre: String, end: String): List<Long> {
    val result = LinkedList<Long>()
    if (!end.contains("X")) {
        result.add(pre.plus(end).toLong(2))
    } else {
        val preX = pre.plus(end.substring(0, end.indexOf("X")))
        val postX = end.substring(end.indexOf("X") + 1)

        for (addr in possibleAddresses(preX.plus("0"), postX)) {
            result.add(addr)
        }
        for (addr in possibleAddresses(preX.plus("1"), postX)) {
            result.add(addr)
        }
    }
    return result
}
