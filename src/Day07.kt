import kotlin.math.abs

@Suppress("unused", "UNUSED_PARAMETER")
object Day07 {

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToIntList("Day07_test")
        test(1, testInput, 37)
        test(2, testInput, 168L)

        val input = readInputToIntList("Day07")
        println("Part 1: ${part1(input)}")
        test(1, input, 348664)
        println("Part 2: ${part2(input)}")
        test(2, input, 100220525L)
    }

    private fun part1(input: List<Int>): Int = input.lowestToHighest().minOf { target ->
        input.sumOf { abs(it - target) }
    }


    private fun part2(input: List<Int>): Long = input.lowestToHighest().minOf { target ->
        input.sumOf { (0..abs(it - target).toLong()).sum() }
    }
}

private fun List<Int>.lowestToHighest(): IntRange = minOf { it }..maxOf { it }
