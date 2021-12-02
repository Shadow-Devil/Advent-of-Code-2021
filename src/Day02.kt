object Day02 {
    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = readInput("Day02_test")
        test(1, testInput, 150)
        test(2, testInput, 900)


        val input = readInput("Day02")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")

    }



    private fun part1(input: List<String>): Int = input.fold(Pair(0, 0)) { (x, y), line ->
        val parts = line.split(" ")
        assert(parts.size == 2)
        val length = parts[1].toInt()
        when (parts[0]) {
            "forward" -> Pair(x + length, y)
            "down" -> Pair(x, y + length)
            "up" -> Pair(x, y - length)
            else -> error("Unknown direction: ${parts[0]}")
        }
    }.run { first * second }

    private fun part2(input: List<String>): Int = input.fold(Triple(0, 0, 0)) { (x, y, aim), line ->
            val parts = line.split(" ")
            assert(parts.size == 2)
            val length = parts[1].toInt()
            when (parts[0]) {
                "forward" -> Triple(x + length, y + aim * length, aim)
                "down" -> Triple(x, y, aim + length)
                "up" -> Triple(x, y, aim - length)
                else -> error("Unknown direction: ${parts[0]}")
            }
        }.run { first * second }
}
