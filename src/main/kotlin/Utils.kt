fun <T> frequencyMap(arr: Array<T>): Map<T, Int> =
    HashMap<T, Int>().apply {
        arr.forEach {
            this.putIfAbsent(it, 0)
            this[it] = this[it]!! + 1
        }
    }