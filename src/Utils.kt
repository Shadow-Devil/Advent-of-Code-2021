import org.jetbrains.annotations.Contract
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads lines from the given input txt file and converts them to Ints.
 */
fun readInputToInt(name: String) = readInput(name).map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
inline fun <reified T> Any.test(part: Int, testInput: T, expectedOutput: Int) {
    require(part in 1..2) { "Part must be between 1 and 2" }

    val name = if (part == 1) "part1" else "part2"
    val jclass = this::class.java
    jclass.declaredMethods.filter{it.name.contains(name) and !it.name.contains("lambda")}.forEach {
        require(it.parameterCount == 1) { "Method must have exactly one parameter" }
        require(it.parameterTypes[0].equals(T::class.java)) { "Parameter must be of type ${T::class.java}" }
        require(it.returnType == Int::class.java) { "Method must return Int" }

        check(it.invoke(this, testInput) == expectedOutput)
        println("Test passed: ${it.name} for $testInput")
    }

    jclass.declaredFields.filter { it.name.contains(name)}.forEach {
        check((it.get(this) as (T) -> Int)(testInput) == expectedOutput)
        println("Test passed: ${it.name} for $testInput")
    }
}
