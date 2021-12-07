import kotlin.math.abs

@Suppress("unused", "UNUSED_PARAMETER")
object Day07{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToIntList("Day07_test")
        test(1, testInput, 37)
        test(2, testInput, 168L)

        val input = readInputToIntList("Day07")
        println("Part 1: ${part1(input)}")
        test(1, input, 348664)
        println("Part 2: ${part2(input)}")
        test(2, input, 100220525)
    }

    private fun part1(input: List<Int>): Int {
        var lowest: Int = Int.MAX_VALUE
        for(i in input.lowestToHighest()){
            val current = input.computeFuel(i)
            lowest = current.coerceAtMost(lowest)
        }

        return lowest
    }


    private fun part2(input: List<Int>): Long {
        var lowest: Long = Long.MAX_VALUE
        for(i in input.lowestToHighest()){
            val current = input.computeFuel2(i)
            lowest = current.coerceAtMost(lowest)
        }

        return lowest
    }

}

private fun List<Int>.lowestToHighest(): IntRange {
    val sorted = this.sortedBy { it }
    return sorted.first()..sorted.last()
}

private fun List<Int>.computeFuel2(target: Int): Long {
    var result = 0L
    for (i in this){
        result += (0..abs(i - target)).sum()
    }
    return result
}

private fun List<Int>.computeFuel(target: Int): Int {
    var result = 0
    for (i in this){
        result += abs(i - target)
    }
    return result
}
