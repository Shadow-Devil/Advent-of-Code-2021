@Suppress("unused", "UNUSED_PARAMETER")
object Day06{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInput("Day06_test")
        test(1, testInput, 5934)
        test(2, testInput, 26984457539)


        val input = readInput("Day06")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 363101)
        test(2, input, 1644286074024)
    }

    private fun part1(input: List<String>): Int {
        var groups: Map<Int, Number> = input.first().split(",").map { it.toInt() }.groupBy { it }.mapValues{ it.value.size }
        for(day in 1..80){
            groups = simulate(groups)
        }

        return groups.values.sumOf{ it.toInt() }
    }

    private fun simulate(groups: Map<Int, Number>): Map<Int, Number> {
        return buildMap {
            groups.filterKeys { it > 0 }.forEach { (key, value) ->
                put(key - 1, value)
            }
            val zeros = groups.getOrDefault(0, 0).toLong()
            put(6, getOrDefault(6, 0).toLong() + zeros)
            put(8, zeros)
        }
    }

    private fun part2(input: List<String>): Long {
        var groups: Map<Int, Number> = input.first().split(",").map { it.toInt() }.groupBy { it }.mapValues{ it.value.size }
        for(day in 1..256){
            groups = simulate(groups)
        }

        return groups.values.sumOf { it.toLong() }
    }

}