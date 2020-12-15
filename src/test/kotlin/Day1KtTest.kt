import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1KtTest {

    @Test
    fun part1() {
        assertEquals(part1(intArrayOf(299, 366, 675, 979, 1456, 1721), 2020), 514579)
    }

    @Test
    fun part2() {
        assertEquals(part2(intArrayOf(299, 366, 675, 979, 1456, 1721), 2020), 241861950)
    }
}