import java.util.*
import utils.*

object Day15{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToSingleInts("Day15_test")
        test(1, testInput, 40)
        test(2, testInput, 315)


        val input = readInputToSingleInts("Day15")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        //test(1, input, -1)
        //test(2, input, -1)
    }

    private fun part1(input: Array<IntArray>): Int {
        val distances = Array(input.size) { IntArray(input.size){ Int.MAX_VALUE } }
        distances[0][0] = 0
        val q = PriorityQueue<Pair<Point, Int>>(compareBy { it.second })
        q.add(Pair(Point(0, 0), 0))
        while (q.isNotEmpty()) {
            val (p, d) = q.poll()
            if (p.x == input.lastIndex && p.y == input[0].lastIndex) {
                return d
            }
            for (i in 0 until 4) {
                val (x, y) = p
                val (dx, dy) = when (i) {
                    0 -> Pair(x + 1, y)
                    1 -> Pair(x, y + 1)
                    2 -> Pair(x - 1, y)
                    3 -> Pair(x, y - 1)
                    else -> throw IllegalStateException()
                }
                if (dx in input.indices && dy in input.indices) {
                    val newD = d + input[dx][dy]
                    if (newD < distances[dx][dy]) {
                        distances[dx][dy] = newD
                        q.add(Pair(Point(dx, dy), newD))
                    }
                }
            }
        }


        return distances[distances.lastIndex][distances[0].lastIndex]
    }

    private fun part2(input: Array<IntArray>): Int {
        return part1(enlargeMap(input))
    }

    private fun enlargeMap(input: Array<IntArray>): Array<IntArray> {
        val newInput = Array(input.size * 5) { IntArray(input.size * 5) }
        for (i in newInput.indices) {
            for (j in newInput.indices) {
                val addition = (i / input.size) + (j / input[0].size)
                newInput[i][j] = (input[i % input.size][j % input[0].size] + addition)
                if(newInput[i][j] > 9){
                    newInput[i][j] = newInput[i][j] % 9
                }
                if(newInput[i][j] == 0){
                    newInput[i][j] = 9
                }
            }
        }
        println(newInput.joinToString("\n") { it.joinToString("") })
        return newInput
    }
}