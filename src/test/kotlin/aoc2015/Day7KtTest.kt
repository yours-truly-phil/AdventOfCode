package aoc2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day7KtTest {

    @Test
    fun `clamp to 16 bits`() {
        val i = Int.MAX_VALUE
        assertEquals("1111111111111111111111111111111", i.toString(2))
        assertEquals("1111111111111111", (i shl 16 ushr 16).toString(2))
        assertEquals("1111111111111111", (i and 0xffff).toString(2))
    }
}