package utils

import kotlin.math.absoluteValue

fun Int.inverse(size: Int): Int = inv().and(1.shl(size) - 1)

fun Int.decTowardsZero() = when{
    this == 0 -> 0
    this > 0 -> this - 1
    else -> this + 1
}

operator fun Int.compareTo(range: IntRange): Int = when{
    this in range -> 0
    this < range.first -> -1
    else -> 1
}

operator fun IntRange.compareTo(i: Int): Int = -(i.compareTo(this))

fun IntRange.abs(): IntRange = when{
    first < 0 && last > 0 -> error("")
    first < 0 && last < 0 -> IntRange(last.absoluteValue, first.absoluteValue)
    else -> this
}

fun Int.gaussSum(): Int = this * (this + 1) / 2