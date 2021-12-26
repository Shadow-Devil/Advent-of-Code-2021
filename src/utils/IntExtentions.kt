package utils

fun Int.inverse(size: Int): Int = inv().and(1.shl(size) - 1)

fun Int.decTowardsZero() = when{
    this == 0 -> 0
    this > 0 -> this - 1
    else -> this + 1
}