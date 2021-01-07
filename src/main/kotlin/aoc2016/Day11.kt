package aoc2016


fun main() {
    val input = Day11.Move(intArrayOf(0, 0, 0, 1, 2, 1, 2, 1, 2, 1, 2), 0)
    Day11().apply { println(part1(input)) }
}

class Day11 {
    fun part1(input: Move): Int {
        return stepsMoveAllUp(input)
    }

    private fun stepsMoveAllUp(input: Move): Int {
        val memo = HashSet<Move>().apply { add(input) }
        val queue = ArrayDeque<Move>().apply { addAll(possibleMoves(input, memo)) }
        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            val possibleMoves = possibleMoves(next, memo)
            if (possibleMoves.any { allInFloor(it, 3) }) {
                return possibleMoves.first { allInFloor(it, 3) }.apply { println(this) }.noMoves
            }
            queue.addAll(possibleMoves)
        }
        return -1
    }

    fun isValidState(input: Move): Boolean {
        for (i in 0 until 4) {
            if (!isFloorValid(input.arr, i)) return false
        }
        return true
    }

    fun allInFloor(input: Move, floor: Int): Boolean {
        for (element in input.arr) {
            if (element != floor) return false
        }
        return true
    }

    fun isFloorValid(input: IntArray, floor: Int): Boolean {
        for (i in 2 until input.size step 2) {
            if (input[i] == floor) {
                val genIdx = i - 1
                if (input[genIdx] != floor) {
                    // no other generators can be on the floor
                    for (otherGen in 1 until input.size step 2) {
                        if (otherGen != genIdx) {
                            if (input[otherGen] == floor) {
                                // chip gets fried by other gen
                                return false
                            }
                        }
                    }
                }
            }
        }
        return true
    }

    fun possibleMoves(input: Move, memo: HashSet<Move>): List<Move> {
//        println("from: $input")
        val elevator = input.arr[0]
        val itemsInFloor = ArrayList<Int>().apply {
            (1 until input.arr.size).filterTo(this) { input.arr[it] == elevator }
        }
        val canBeMoved = getSubsets(itemsInFloor, 2).apply { addAll(getSubsets(itemsInFloor, 1)) }
        val result = ArrayList<Move>()
        // up
        if (elevator <= 2) {
            for (group in canBeMoved) {
                val new = Move(input.arr.clone()
                    .apply { for (index in group) this[index]++ }
                    .apply { this[0]++ }, input.noMoves + 1)
                if (new !in memo && isValidState(new)) {
                    result.add(new)
                    memo.add(new)
                }
            }
        }
        // down
        if (elevator >= 1) {
            for (group in canBeMoved) {
                val new = Move(input.arr.clone()
                    .apply { for (index in group) this[index]-- }
                    .apply { this[0]-- }, input.noMoves + 1)
                if (new !in memo && isValidState(new)) {
                    result.add(new)
                    memo.add(new)
                }
            }
        }
//        result.forEach { println("to:   $it") }
        return result
    }

    private fun getSubsets(
        superSet: ArrayList<Int>,
        k: Int,
        idx: Int,
        current: HashSet<Int>,
        solution: ArrayList<Set<Int>>,
    ) {
        //successful stop clause
        if (current.size == k) {
            solution.add(HashSet(current))
            return
        }
        //unsuccessful stop clause
        if (idx == superSet.size) return
        val x = superSet[idx]
        current.add(x)
        //"guess" x is in the subset
        getSubsets(superSet, k, idx + 1, current, solution)
        current.remove(x)
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx + 1, current, solution)
    }

    class Move(val arr: IntArray, val noMoves: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Move

            if (!arr.contentEquals(other.arr)) return false

            return true
        }

        override fun hashCode(): Int {
            return arr.contentHashCode()
        }

        override fun toString(): String {
            return "Move(arr=${arr.contentToString()}, noMoves=$noMoves)"
        }
    }

    private fun getSubsets(superSet: ArrayList<Int>, k: Int): ArrayList<Set<Int>> {
        val res: ArrayList<Set<Int>> = ArrayList()
        getSubsets(superSet, k, 0, HashSet(), res)
        return res
    }
}