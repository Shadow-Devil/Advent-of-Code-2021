package utils

fun <T> List<T>.split() = Pair(first(), drop(1))

fun List<Int>.lowestToHighest(): IntRange = minOf { it }..maxOf { it }

fun Iterable<Long>.prod(): Long {
    var prod = 1L
    for (element in this) {
        prod *= element
    }
    return prod
}

fun Iterable<Int>.prod(): Int {
    var prod = 1
    for (element in this) {
        prod *= element
    }
    return prod
}