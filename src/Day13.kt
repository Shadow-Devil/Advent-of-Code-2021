import utils.*

object Day13{

    data class Point(val x: Int, val y: Int)
    data class Fold(val direction: String, val a: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToText("Day13_test")
        test(1, testInput, 17)
        //test(2, testInput, -1)


        val input = readInputToText("Day13")
        println("Part 1: ${part1(input)}")
        println("Part 2: \n${part2(input)}")
        //test(1, input, -1)
        //test(2, input, -1)
    }

    private fun part1(input: String): Int {
        val (points, folds) = parseInput(input)
        val fold = folds.first()
        return points.map { (x, y) ->
            if (fold.direction == "x"){
                Point(if(x < fold.a) x else fold.a - (x - fold.a), y)
            }else{
                Point(x, if(y < fold.a) y else fold.a - (y - fold.a))
            }
        }.toSet().size
    }

    private fun part2(input: String): String {
        val (points, folds) = parseInput(input)
        val newPoints = folds.fold(points) { current, fold ->
            current.map{ (x, y) ->
            if (fold.direction == "x"){
                Point(if(x < fold.a) x else fold.a - (x - fold.a), y)
            }else{
                Point(x, if(y < fold.a) y else fold.a - (y - fold.a))
            }}.toSet()
        }
        val maxX = newPoints.maxOf { it.x }
        val maxY = newPoints.maxOf { it.y }

        val grid = Array(maxX + 1) { Array(maxY + 1) { false } }
        newPoints.forEach { grid[it.x][it.y] = true }
        val result = buildString {
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    append(if (grid[x][y]) "#" else ".")
                }
                append(lf)
            }
        }
        return result
    }

    private fun parseInput(input: String): Pair<Set<Point>, List<Fold>>{
        val (points, folds) = input.split(lf + lf)
        return points.lines().map { it.split(",").map { it.trim().toInt() }.let { (a, b) -> Point(a, b) } }.toSet() to
            folds.lines().map{
                val (dir, a) = Regex(""".*([xy])=(\d+)""").matchEntire(it)?.destructured!!
                Fold(dir, a.toInt())
            }
    }

}