package utils

import java.math.BigInteger
import java.security.MessageDigest

val lf: String = System.getProperty("line.separator")

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun String.toMutableSet() = this.toList().toMutableSet()

fun MutableSet<Char>.except(vararg sets: Set<Char>): MutableSet<Char> = this.filterNot { it in sets.flatMap { it }.toSet() }.toMutableSet()

private val digiMap: Map<Char, String> = buildMap {
    this['0'] = "0000"
    this['1'] = "0001"
    this['2'] = "0010"
    this['3'] = "0011"
    this['4'] = "0100"
    this['5'] = "0101"
    this['6'] = "0110"
    this['7'] = "0111"
    this['8'] = "1000"
    this['9'] = "1001"
    this['A'] = "1010"
    this['B'] = "1011"
    this['C'] = "1100"
    this['D'] = "1101"
    this['E'] = "1110"
    this['F'] = "1111"
}

fun String.hexToBin(): String {
    return map { digiMap[it] }.joinToString(separator = "")
}
