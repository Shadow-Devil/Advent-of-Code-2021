@Suppress("unused", "UNUSED_PARAMETER")
object Day09{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInput("Day09_test")
        test(1, testInput, 15)
        test(2, testInput, 1134)


        val input = readInput("Day09")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 572)
        test(2, input, 847044)
    }

    private fun part1(input: List<String>): Int {
        return input.flatMapIndexed { idx: Int, it: String ->
            it.filterIndexed { idy, c ->
                c.isLowPoint(
                    input,
                    idx,
                    idy
                )
            }.toList()
        }.sumOf {
            it.digitToInt() + 1
        }
    }

    private fun part2(input: List<String>): Int {
        val booleanMap: Array<BooleanArray> = input.map { it.map { it != '9' }.toBooleanArray() }.toTypedArray()
        val lowPoints = input.flatMapIndexed { idx: Int, it: String ->
            it.mapIndexedNotNull { idy, c -> if (c.isLowPoint(input, idx, idy)) idx to idy else null }
        }
        val (fst, snd, trd) = lowPoints.map {(x, y) ->
            getSize(x, y, booleanMap)
        }.sortedDescending().also{
            println(it)
        }

        return fst * snd * trd
    }

    private fun getSize(x: Int, y: Int, booleanMap: Array<BooleanArray>): Int {
        if (booleanMap.getOrNull(x)?.getOrNull(y) == true) {
            booleanMap[x][y] = false
            return 1 +
                    getSize(x - 1, y, booleanMap) +
                    getSize(x + 1, y, booleanMap) +
                    getSize(x, y - 1, booleanMap) +
                    getSize(x, y + 1, booleanMap)
        }
        return 0
    }


}

private fun Char.isLowPoint(input: List<String>, idx: Int, idy: Int): Boolean {
    val digit: Int = this.digitToInt()
    val left: Int? = input[idx].getOrNull(idy - 1)?.digitToInt()
    val right: Int? = input[idx].getOrNull(idy + 1)?.digitToInt()
    val up: Int? = input.getOrNull(idx - 1)?.getOrNull(idy)?.digitToInt()
    val down: Int? = input.getOrNull(idx + 1)?.getOrNull(idy)?.digitToInt()
    return (left == null || digit < left) &&
            (right == null || digit < right) &&
            (up == null || digit < up)
            && (down == null || digit < down)
}
