import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day7KtTest {

    @Test
    fun `parse containing stuff`() {
        assertEquals(
            parseContaining("light red bags contain 1 bright white bag, 2 muted yellow bags."),
            listOf(Pair(1, "bright white"), Pair(2, "muted yellow"))
        )
        assertEquals(
            parseContaining("bright white bags contain 1 shiny gold bag."),
            listOf(Pair(1, "shiny gold"))
        )
        assertEquals(
            parseContaining("faded blue bags contain no other bags."), emptyList<Pair<Int, String>>()
        )
    }
}