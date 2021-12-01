@Suppress("unused")

object Day01{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToInt("Day01_test")
        test(1, testInput, 7)
        test(2, testInput, 5)


        val input = readInputToInt("Day01")
        test(1, input, 1446)
        test(2, input, 1486)
        println(part1Refactored(input))
        println(part2(input))
    }


    private fun part1FirstSolution_(input: List<Int>): Int = input.stream().reduce(Pair(0, input.first()), { (count, prev), cur ->
        Pair(count + if (prev < cur) 1 else 0, cur)
    }, {(a, b), (c, d) -> Pair(a + c, b + d)}).first


    private fun part1FirstSolution(input: List<Int>): Int = input.fold(Pair(0, input.first())) { (count, prev), cur ->
        Pair(count + if (prev < cur) 1 else 0, cur)
    }.first



    private fun part1Refactored(input: List<Int>): Int = input.zipWithNext().sumOf { (a, b) -> if (a < b) 1.toInt() else 0 }

    private fun part2(input: List<Int>): Int =
        part1Refactored(input.zipWithNext().zipWithNext { (first, second), (_, third) -> first + second + third })



    private fun part1__(input: List<Int>): Int = input.windowed(2).count { (a, b) -> a < b }
    private fun part2__(input: List<Int>): Int = part1__(input.windowed(3).map { it.sum() })


    private val part1___: (List<Int>) -> Int = { it.windowed(2).count { (a, b) -> a < b } }
    private val part2___: (List<Int>) -> Int = { part1___(it.windowed(3).map(List<Int>::sum)) }

}