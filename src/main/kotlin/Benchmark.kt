import java.lang.System.*
import java.util.concurrent.Callable

fun micros(of: Runnable) {
    val start = nanoTime()
    of.run()
    val diff = nanoTime() - start
    println("Ran in ${diff / 1000} µs")
}

fun <T> micros(of: Callable<T>):T {
    val start = nanoTime()
    val res = of.call()
    val diff = nanoTime() - start
    println("Ran in ${diff / 1000} µs")
    return res
}
