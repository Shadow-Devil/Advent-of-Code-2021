package utils

@Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
inline fun <reified T, reified R: Number> Any.test(part: Int, testInput: T, expectedOutput: R) {
    require(part in 1..2) { "Part must be between 1 and 2" }

    val name = if (part == 1) "part1" else "part2"
    val jclass = this::class.java
    jclass.declaredMethods.filter{it.name.contains(name) and !it.name.contains("lambda|\\\$".toRegex())}.forEach {
        require(it.parameterCount == 1) { "Method must have exactly one parameter but has ${it.parameterCount}, ${it.toGenericString()}" }
        require(it.parameterTypes[0].equals(T::class.java)) { "Parameter must be of type ${T::class.java} but was ${it.parameterTypes[0]}" }
        val result = it.invoke(this, testInput)
        requireNotNull(result as? R) { "Method must return ${R::class.java} but was ${result::class.java}" }

        check(result == expectedOutput) { "Expected output for $name($testInput) to be $expectedOutput but was $result" }
//        assertThat(it.invoke(this, testInput)).isEqualTo(expectedOutput)
        println("Test passed: ${it.name} for $testInput")
    }

    jclass.declaredFields.filter { it.name.contains(name)}.forEach {
//        assertThat((it.get(this) as (T) -> Int)(testInput)).isEqualTo(expectedOutput)
        check((it.get(this) as (T) -> R)(testInput) == expectedOutput) { "Expected output for $name($testInput) to be $expectedOutput but was ${it.get(this) as (T) -> R}($testInput)" }
        println("Test passed: ${it.name} for $testInput")
    }
}
