import Day02.Direction.*
import utils.*

object Day02 {
    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = readInput("Day02_test")
        test(1, testInput, 150)
        test(2, testInput, 900)


        val input = readInput("Day02")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 1648020)
        test(2, input, 1759818555)
    }

    enum class Direction {
        UP, DOWN, FORWARD
    }

    private fun parseInput(input: List<String>): List<Pair<Direction, Int>> = input.map {
        val (dir, length) = it.uppercase().split(" ")
        Direction.valueOf(dir) to length.toInt()
    }

    private fun part1Refactored(input: List<String>):Int = parseInput(input).fold(Pair(0,0)) { (x, y), (dir, length) ->
        when (dir) {
            FORWARD -> Pair(x + length, y)
            DOWN -> Pair(x, y + length)
            UP -> Pair(x, y - length)
        }
    }.run { first * second }

    private fun part1(input: List<String>): Int = input.map(String::uppercase).fold(Pair(0, 0)) { (x, y), line ->
        val (dir, length) = line.split(" ")
        when (Direction.valueOf(dir)) {
            FORWARD -> Pair(x + length.toInt(), y)
            DOWN -> Pair(x, y + length.toInt())
            UP -> Pair(x, y - length.toInt())
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
