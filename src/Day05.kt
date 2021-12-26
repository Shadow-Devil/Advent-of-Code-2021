import java.lang.Integer.max
import java.lang.Integer.min
import utils.*

object Day05 {

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInput("Day05_test")
        test(1, testInput, 5)
        test(2, testInput, 12)


        val input = readInput("Day05")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 6687)
        test(2, input, 19851)
    }


    private fun part1(input: List<String>): Int = buildMap<Point, Int> {
        parseInput(input).filter { (p1, p2) -> p1.x == p2.x || p1.y == p2.y }.forEach { (p1, p2) ->
            if (p1.x == p2.x) {
                for (i in min(p1.y, p2.y)..max(p1.y, p2.y)) {
                    put(Point(p1.x, i), get(Point(p1.x, i))?.inc() ?: 1)
                }
            } else {
                for (i in min(p1.x, p2.x)..max(p1.x, p2.x)) {
                    put(Point(i, p1.y), get(Point(i, p1.y))?.inc() ?: 1)
                }
            }
        }
    }.count { it.value > 1 }

    private fun parseInput(input: List<String>): List<Line> =
        input.map { line ->
            line.split(" -> ").map { pair ->
                pair.split(",").map(String::toInt)
            }.let { (p1, p2) -> Line(Point(p1[0], p1[1]), Point(p2[0], p2[1])) }
        }


    private fun part2(input: List<String>): Int = buildMap<Point, Int> {
        parseInput(input).forEach { (p1, p2) ->
            if (p1.x == p2.x) {
                for (i in min(p1.y, p2.y)..max(p1.y, p2.y)) {
                    put(Point(p1.x, i), get(Point(p1.x, i))?.inc() ?: 1)
                }
            } else if (p1.y == p2.y) {
                for (i in min(p1.x, p2.x)..max(p1.x, p2.x)) {
                    put(Point(i, p1.y), get(Point(i, p1.y))?.inc() ?: 1)
                }
            } else {
                //Diagonal
                val xstep = if (p1.x < p2.x) 1 else -1
                val ystep = if (p1.y < p2.y) 1 else -1
                var p = p1
                while (p.x != p2.x && p.y != p2.y) {
                    put(p, get(p)?.inc() ?: 1)
                    p = Point(p.x + xstep, p.y + ystep)
                }
                put(p2, get(p2)?.inc() ?: 1)
            }
        }
    }.count { it.value >= 2 }

}