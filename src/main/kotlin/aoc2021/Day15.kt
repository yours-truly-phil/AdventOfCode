package aoc2021

import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class GFG(val size: Int) {
    val dist: IntArray = IntArray(size) { Int.MAX_VALUE }
    private val settled: MutableSet<Int> = mutableSetOf()
    private val pq: PriorityQueue<Node> = PriorityQueue(size, Node())
    private lateinit var adj: List<List<Node>>

    fun dijkstra(adj: List<List<Node>>, src: Int) {
        this.adj = adj

        pq.add(Node(src, 0))
        dist[src] = 0

        while (settled.size != size) {
            if (pq.isEmpty()) return

            val u = pq.remove().node
            if (settled.contains(u)) continue

            settled.add(u)
            processNeighbors(u)
        }
    }

    private fun processNeighbors(u: Int) {
        var edgeDistance: Int
        var newDistance: Int

        for (i in 0 until adj[u].size) {
            val v = adj[u][i]
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost
                newDistance = dist[u] + edgeDistance

                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance
                }

                pq.add(Node(v.node, dist[v.node]))
            }
        }
    }
}

class Node(val node: Int = 0, val cost: Int = 0) : Comparator<Node> {
    override fun compare(o1: Node, o2: Node): Int {
        return when {
            o1.cost < o2.cost -> -1
            o1.cost > o2.cost -> 1
            else -> 0
        }
    }
}

class Day15 {

    private fun solve(input: String, multi: Int): Int {
        val inputGrid = input.lines().map { it.map { c -> "$c".toInt() } }
        val size = inputGrid.size
        val grid = Array(size * multi) { IntArray(size * multi) }
        repeat(multi) { step ->
            for (y in 0 until size) {
                for (x in 0 until size) {
                    val risk = inputGrid[y][x] + step
                    grid[y + (step * size)][x] = if (risk > 9) risk - 9 else risk
                }
            }
        }
        repeat(multi - 1) { step ->
            for (y in grid.indices) {
                for (x in 0 until size) {
                    val risk = grid[y][x] + step + 1
                    grid[y][x + size + (step * size)] = if (risk > 9) risk - 9 else risk
                }
            }
        }

        val numElements = grid.size * grid[0].size
        val adj = ArrayList<MutableList<Node>>()
        for (i in 0 until numElements) {
            adj.add(ArrayList())
        }

        for (y in grid.indices) {
            for (x in 0 until grid[y].size) {
                val from = adj[xyTo1D(x, y, grid.size)]
                if (y > 0)
                    from.add(Node(xyTo1D(x, y - 1, grid.size), grid[y - 1][x]))
                if (y < grid.size - 1)
                    from.add(Node(xyTo1D(x, y + 1, grid.size), grid[y + 1][x]))
                if (x > 0)
                    from.add(Node(xyTo1D(x - 1, y, grid.size), grid[y][x - 1]))
                if (x < grid[y].size - 1)
                    from.add(Node(xyTo1D(x + 1, y, grid.size), grid[y][x + 1]))
            }
        }

        val gfg = GFG(numElements)
        gfg.dijkstra(adj, 0)
        return gfg.dist[xyTo1D(grid.size - 1, grid.size - 1, grid.size)]
    }

    private fun xyTo1D(x: Int, y: Int, size: Int): Int {
        return x + y * size
    }

    @Test
    fun part1() {
        val sample = """
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581""".trimIndent()
        assertEquals(40, solve(sample, 1))
        assertEquals(702, solve(File("files/2021/day15.txt").readText(), 1))
    }

    @Test
    fun part2() {
        assertEquals(
            315, solve(
                """
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581""".trimIndent(), 5
            )
        )
        assertEquals(2955, solve(File("files/2021/day15.txt").readText(), 5))
    }
}
