import java.util.*

fun main() {
    listOf(5, 5, 3, 2, 1).apply {
        PowerSet(iterator())
            .apply { while (hasNext()) println(next().joinToString(",") { it.toString() }) }
    }
}

class PowerSet<T>(storage: Iterator<T>) : Iterator<List<T>> {

    private var leftMode = true
    private var memory = Optional.empty<List<T>>()
    private var delegate = Optional.empty<PowerSet<T>>()
    private var pivot = Optional.empty<T>()

    override fun hasNext(): Boolean {
        return when {
            delegate.isEmpty -> {
                val hasNext = leftMode
                leftMode = false
                hasNext
            }
            leftMode -> delegate.get().hasNext()
            else -> true
        }
    }

    override fun next(): List<T> {
        return when {
            delegate.isEmpty -> emptyList()
            else -> when {
                leftMode -> Optional.of(delegate.get().next())
                    .also { memory = it }
                    .also { leftMode = false }.get()
                else -> ArrayList<T>()
                    .apply { add(pivot.get()) }
                    .apply { addAll(memory.get()) }
                    .also { leftMode = true }
            }
        }
    }

    init {
        when {
            storage.hasNext() -> {
                pivot = Optional.of(storage.next()!!)
                delegate = Optional.of(PowerSet(storage))
            }
            else -> {
                delegate = Optional.empty()
                pivot = Optional.empty()
            }
        }
    }
}