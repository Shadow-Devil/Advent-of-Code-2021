@file:Suppress("unused")
package utils

import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("input", "$name.txt").readLines()

/**
 * Reads lines from the given input txt file and converts them to Ints.
 */
fun readInputToInt(name: String) = readInput(name).map { it.toInt() }

fun readInputToIntList(name: String, delimiter: String = ",") = readInput(name).single().split(delimiter).map { it.toInt() }

fun readInputToSingleInts(name: String) = readInput(name).map { it.map { it.digitToInt() }.toIntArray() }.toTypedArray()

fun readInputToText(name: String) = File("input", "$name.txt").readText()

