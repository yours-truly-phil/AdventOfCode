import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day15KtTest {

    @Test
    fun findLastNumber() {
        assertEquals(findLastNumber("0,3,6", 2020), 436)
        assertEquals(findLastNumber("1,3,2", 2020), 1)
        assertEquals(findLastNumber("2,1,3", 2020), 10)
        assertEquals(findLastNumber("1,2,3", 2020), 27)
        assertEquals(findLastNumber("2,3,1", 2020), 78)
        assertEquals(findLastNumber("3,2,1", 2020), 438)
        assertEquals(findLastNumber("3,1,2", 2020), 1836)

        assertEquals(findLastNumber("0,3,6", 30000000), 175594)
    }
}