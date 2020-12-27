package aoc2015

import java.io.File


fun main() {
    Day17().also { println(it.part1(File("files/2015/day17.txt").readText())) }
}

class Day17 {
    fun part1(input: String): Int = perms(150, input.lines().map { it.toInt() }.toIntArray())

    fun perms(num: Int, nums: IntArray): Int = arrayListOf(ArrayList<Int>())
        .also { combinations(nums, it) }
        .filter { it.sum() == num }
        .count()

    private fun combinations(nums: IntArray, combinations: ArrayList<ArrayList<Int>>) {
        nums.indices.forEach { addNumToCombinations(combinations, nums, it) }
    }

    private fun addNumToCombinations(combinations: ArrayList<ArrayList<Int>>, nums: IntArray, i: Int) {
        (0 until combinations.size).forEach {
            val newComb = ArrayList<Int>()
            newComb.addAll(combinations[it])
            newComb.add(nums[i])
            combinations.add(newComb)
        }
    }
}