fun main() {
    val input = "0,1,5,10,3,12,19"

    var start = System.currentTimeMillis()
    println("part1: %d".format(findLastNumber(input, 2020)))
    var diff = System.currentTimeMillis() - start
    println("took $diff ms")

    start = System.currentTimeMillis()
    println("part2: %d".format(findLastNumber(input, 30000000)))
    diff = System.currentTimeMillis() - start
    println("took $diff ms")
}

fun findLastNumber(input: String, goal: Int): Int {
    val mem = Mem(input)

    while (mem.turn < goal) {
        mem.turn++
        if (mem.isNew) {
            step(mem, 0)
        } else {
            step(mem, mem.age)
        }
    }
    return mem.last
}

fun step(mem: Mem, value: Int) {
    mem.last = value
    if (mem.past.containsKey(value)) {
        mem.isNew = false
        mem.age = mem.turn - mem.past[value]!!
    } else {
        mem.isNew = true
    }
    mem.past[value] = mem.turn
}

class Mem(input: String) {
    val past = HashMap<Int, Int>()
    var isNew: Boolean = true
    var last: Int = 0
    var turn: Int = 0
    var age: Int = 0

    init {
        input.split(",")
            .map { s -> s.toInt() }
            .forEach { i ->
                run {
                    this.turn++
                    if (this.past.containsKey(i)) {
                        this.isNew = false
                    }
                    this.past[i] = this.turn
                    this.last = i
                }
            }
    }
}