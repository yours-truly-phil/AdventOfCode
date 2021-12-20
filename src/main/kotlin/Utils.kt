fun <T> frequencyMap(arr: Array<T>): Map<T, Int> =
    HashMap<T, Int>().apply {
        arr.forEach {
            this.putIfAbsent(it, 0)
            this[it] = this[it]!! + 1
        }
    }

inline fun <reified T> getAdjacentFromGridOrDefault(grid: Array<Array<T>>, x: Int, y: Int, default: T): Array<T> {
    val res = Array(9) { default }
    res[0] = getFromGridOrDefault(grid, x - 1, y - 1, default)
    res[1] = getFromGridOrDefault(grid, x, y - 1, default)
    res[2] = getFromGridOrDefault(grid, x + 1, y - 1, default)
    res[3] = getFromGridOrDefault(grid, x - 1, y, default)
    res[4] = getFromGridOrDefault(grid, x, y, default)
    res[5] = getFromGridOrDefault(grid, x + 1, y, default)
    res[6] = getFromGridOrDefault(grid, x - 1, y + 1, default)
    res[7] = getFromGridOrDefault(grid, x, y + 1, default)
    res[8] = getFromGridOrDefault(grid, x + 1, y + 1, default)
    return res
}

fun <T> getFromGridOrDefault(grid: Array<Array<T>>, x: Int, y: Int, default: T): T =
    if (y in grid.indices && x in grid[y].indices) grid[y][x] else default