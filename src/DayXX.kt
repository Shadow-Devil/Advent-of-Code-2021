@Suppress("unused")

object DayXX{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToInt("DayXX_test")
        test(1, testInput, -1)
        //test(2, testInput, -1)


        val input = readInputToInt("DayXX")
        println("Part 1: ${part1(input)}")
        //test(1, input, -1)
        println("Part 2: ${part2(input)}")
        //test(2, input, -1)
    }

    private fun part1(input: List<Int>): Int = 0

    private fun part2(input: List<Int>): Int = 0

}