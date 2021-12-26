import utils.*

object Day11{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInputToSingleInts("Day11_test")
        test(1, testInput, 1656)
        test(2, testInput, 195)


        val input = readInputToSingleInts("Day11")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 1686)
        test(2, input, 360)
    }

    private fun part1(input: Array<IntArray>): Int {
        val grid: Array<IntArray> = input.map { it.map { it }.toIntArray() }.toTypedArray()
        var flashed = 0
        repeat(100){
            flashed += step(grid)
        }

        return flashed
    }

    private fun step(input: Array<IntArray>): Int {
        increaseAll(input)
        val flashed = flash(input)
        reset(input)
        return flashed
    }

    private fun flash(input: Array<IntArray>, flashed:Array<BooleanArray>? = null): Int {
        if (input.all { it.all { it < 9 } }) return 0
        val newFlashed = input.mapIndexed { idx, row -> row.mapIndexed { idy, it -> it > 9 && (flashed == null || !flashed[idx][idy]) }.toBooleanArray() }.toTypedArray()
        //println(newFlashed.joinToString("\n") { it.joinToString("") { if (it) "#" else "." } } + "\n")
        if(newFlashed.all { it.all { !it } }) return 0
        val allFlashed = newFlashed.mapIndexed {idx, row -> row.mapIndexed { idy, it -> it || flashed?.get(idx)?.get(idy) == true }.toBooleanArray() }.toTypedArray()
        val flashedIndexes: List<Pair<Int, Int>> = newFlashed.flatMapIndexed{ i, row -> row.mapIndexed{ j, value -> if (value) Pair(i, j) else null }.filterNotNull() }
        return flashedIndexes.size + increaseNeighbors(input, allFlashed, flashedIndexes)
    }

    private fun increaseNeighbors(input: Array<IntArray>, flashed: Array<BooleanArray>, flashedIndexes: List<Pair<Int, Int>>): Int {
        flashedIndexes.forEach { (x, y) ->
            increaseNeighbor(input, x, y)
        }
        return flash(input, flashed)
    }

    private fun increaseNeighbor(input: Array<IntArray>, x: Int, y: Int) {
        val neighbors = listOf(
            x - 1 to y - 1,
            x to y - 1,
            x + 1 to y - 1,
            x - 1 to y,
            x + 1 to y,
            x - 1 to y + 1,
            x to y + 1,
            x + 1 to y + 1
        )
        neighbors.forEach { (x, y) ->
            if (x in input.indices && y in input[x].indices) {
                input[x][y]++
            }
        }
    }

    private fun reset(input: Array<IntArray>) {
        for (i in input.indices) {
            for (j in input[i].indices) {
                if(input[i][j] > 9)
                    input[i][j] = 0
            }
        }
    }

    private fun increaseAll(input: Array<IntArray>) {
        input.indices.forEach { i ->
            input[i].indices.forEach { j ->
                input[i][j]++
            }
        }
    }


    private fun part2(input: Array<IntArray>): Int {
        val grid = input.map { it.map { it }.toIntArray() }.toTypedArray()
        var round = 0
        while(true){
            step(grid)
            round++
            if(grid.all { it.all { it == 0 } }){
                return round
            }
        }

    }
}