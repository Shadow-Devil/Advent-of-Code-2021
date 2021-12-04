object Day03 {
    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = readInput("Day03_test")
        test(1, testInput, 198)
        test(2, testInput, 230)


        val input = readInput("Day03")
        test(1, input, 3958484)
        test(2, input, 1613181)
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")

    }

    private fun part1(input: List<String>): Int = countBits(input)
        .map { if (it > 0) 1 else 0 }.joinToString(separator = "").toInt(2).let { gammaRate ->
        val epsilonRate = inverse(gammaRate, input.first().length)
        gammaRate * epsilonRate
    }

    private fun inverse(x: Int, size: Int): Int = x.inv().and(1.shl(size) - 1)

    private fun countBits(input: List<String>) =
        input.fold(IntArray(input.first().length)) { acc, curr ->
            curr.forEachIndexed { i, c ->
                acc[i] += when (c) {
                    '1' -> 1
                    '0' -> -1
                    else -> error("Invalid input $c")
                }
            }
            acc
        }

    private fun part2(input: List<String>): Int {
        val size = input.first().length
        fun calcPossibleSolutions(co2: Boolean): List<String> {
            var possibleSolutions = input
            var position = 0
            while(possibleSolutions.size > 1 && position < size) {
                val countedBits = countBits(possibleSolutions)
                possibleSolutions = possibleSolutions.filter {
                    when{
                        countedBits[position] > 0 -> (it[position] == '1') xor co2
                        countedBits[position] < 0 -> (it[position] == '0') xor co2
                        else -> (it[position] == '1') xor co2
                } }
                position++
            }
            assert(possibleSolutions.size == 1)
            return possibleSolutions
        }
        val possibleSolutionsOx = calcPossibleSolutions(co2 = false).first()
        val possibleSolutionsCo2 = calcPossibleSolutions(co2 = true).first()

        return possibleSolutionsOx.toInt(2) * possibleSolutionsCo2.toInt(2)
    }

}
