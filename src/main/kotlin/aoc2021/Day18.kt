package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.roundToInt
import kotlin.test.assertEquals

class Day18 {
    private fun solvePart1(input: String): Int {
        val lines = input.lines()
        var cur = parseLine(lines.first())
        for (i in 1 until lines.size) {
            val s = "[${cur},${lines[i]}]"
            cur = parseLine(s)
            reduce(cur)
        }
        println(cur)
        return cur.resolve()
    }

    private fun solvePart2(input: String): Int {
        val lines = input.lines()
        var curMax = Int.MIN_VALUE
        for (i in lines.indices) {
            for (y in lines.indices) {
                if (i != y) {
                    val s = "[${lines[i]},${lines[y]}]"
                    val cur = parseLine(s)
                    reduce(cur)
                    curMax = maxOf(curMax, cur.resolve())
                }
            }
        }
        return curMax
    }

    private fun reduce(node: SFNode) {
        while (true) {
            var allExploded = false
            while (!allExploded) {
                val nodes = arrayListOf<SFNode>()
                node.collectChildren(nodes)

                val toExplode = nodes.firstOrNull { it.depth >= 5 && it.value != null }?.parent
                if (toExplode == null) {
                    allExploded = true
                } else {
                    toExplode.explode()
                }
            }
            val nodes = arrayListOf<SFNode>()
            node.collectChildren(nodes)

            val toSplit = nodes.firstOrNull { it.value != null && it.value!! > 9 }
            if (toSplit == null) {
                break
            } else {
                toSplit.split()
            }
        }
    }

    private fun parseLine(line: String): SFNode {
        val parent = SFNode(true, null, null, null, null, 0)
        var cur = parent
        var isLeft = true
        var depth = 0
        for (i in line.indices) {
            when (val c = line[i].toString()) {
                "[" -> {
                    if (isLeft) {
                        cur.left = SFNode(true, null, null, cur, null, depth)
                        cur = cur.left!!
                    } else {
                        cur.right = SFNode(false, null, null, cur, null, depth)
                        cur = cur.right!!
                        isLeft = true
                    }
                    depth += 1
                }
                "]" -> {
                    cur = cur.parent!!
                    depth -= 1
                }
                "," -> isLeft = false
                else -> when {
                    isLeft -> {
                        cur.left = SFNode(true, null, null, cur, c.toInt(), depth)
                    }
                    else -> {
                        cur.right = SFNode(false, null, null, cur, c.toInt(), depth)
                    }
                }
            }
        }
        val result = parent.left!!
        result.parent = null
        return result
    }

    class SFNode(
        private val isLeftNode: Boolean,
        var left: SFNode?,
        var right: SFNode?,
        var parent: SFNode?,
        var value: Int?,
        val depth: Int
    ) {
        override fun toString(): String {
            return value?.toString() ?: "[${left.toString()},${right.toString()}]"
        }

        fun resolve(): Int {
            return if (value != null) {
                value!!
            } else {
                3 * left!!.resolve() + 2 * right!!.resolve()
            }
        }

        fun split() {
            val oldValue = value!!
            value = null
            left = SFNode(true, null, null, this, oldValue / 2, depth + 1)
            right = SFNode(false, null, null, this, (oldValue / 2.0).roundToInt(), depth + 1)
        }

        fun explode() {
            val leftValue = left!!.value!!
            val rightValue = right!!.value!!
            value = 0
            left = null
            right = null
            addLeft(leftValue, parent!!, isLeftNode)
            addRight(rightValue, parent!!, isLeftNode)
        }

        private fun addLeft(value: Int, node: SFNode, childPos: Boolean) {
            if (!childPos) {
                // child was right, now add to the right on left node
                addToRightUp(value, node.left!!)
            } else {
                // child was left, go deeper
                if (node.parent != null) {
                    addLeft(value, node.parent!!, node.isLeftNode)
                }
            }
        }

        private fun addRight(value: Int, node: SFNode, childPos: Boolean) {
            if (childPos) {
                // parent was left, now add to the left on right node
                addToLeftUp(value, node.right!!)
            } else {
                // child was right, go deeper
                if (node.parent != null) {
                    addRight(value, node.parent!!, node.isLeftNode)
                }
            }
        }

        private fun addToRightUp(value: Int, node: SFNode) {
            if (node.value != null) {
                node.value = node.value!! + value
            } else {
                addToRightUp(value, node.right!!)
            }
        }

        private fun addToLeftUp(value: Int, node: SFNode) {
            if (node.value != null) {
                node.value = node.value!! + value
            } else {
                addToLeftUp(value, node.left!!)
            }
        }

        fun collectChildren(list: MutableList<SFNode>) {
            list.add(this)
            if (left != null) {
                left!!.collectChildren(list)
            }
            if (right != null) {
                right!!.collectChildren(list)
            }
        }
    }

    val sample = """
[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]""".trimIndent()

    @Test
    fun part1() {
        assertEquals(4140, solvePart1(sample))
        assertEquals(4145, solvePart1(File("files/2021/day18.txt").readText()))
    }

    @Test
    fun part2() {
        assertEquals(
            3993, solvePart2(
                """
[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]""".trimIndent()
            )
        )
        assertEquals(4855, solvePart2(File("files/2021/day18.txt").readText()))
    }
}