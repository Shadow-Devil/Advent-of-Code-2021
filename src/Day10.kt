@Suppress("unused", "UNUSED_PARAMETER")
object Day10{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInput("Day10_test")
        test(1, testInput, 26397)
        test(2, testInput, 288957L)


        val input = readInput("Day10")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 374061)
        test(2, input, 2116639949L)
    }

    private fun part1(input: List<String>): Int = input.sumOf(String::getCorruptedCount)

    private fun part2(input: List<String>): Long {
        val openToInt = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
        val incomplete = input.filter { it.getCorruptedCount() == 0 }
        return incomplete.map{ it.complete().fold(0L) { acc, s: Char -> acc * 5 + openToInt[s]!! }
        }.sorted().run {
            this[this.size / 2]
        }
    }



}

private fun String.complete(): List<Char> {
    val openToClose = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
    val stack = ArrayDeque<Char>()
    for(c: Char in this){
        when(c){
            '[', '{', '(', '<' -> stack.addFirst(c)
            else -> {
                val last = stack.removeFirst()
                assert (openToClose[last] == c)
            }
        }
    }
    return stack
}

private fun String.getCorruptedCount(): Int {
    val closeToInt: Map<Char, Int> = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val stack = ArrayDeque<Char>()
    for(c: Char in this){
        when(c){
            '[', '{', '(', '<' -> stack.addFirst(c)
            else -> {
                when(stack.removeFirstOrNull() ?: return 0){
                    '[' -> if(c != ']') return closeToInt[c]!!
                    '{' -> if(c != '}') return closeToInt[c]!!
                    '(' -> if(c != ')') return closeToInt[c]!!
                    '<' -> if(c != '>') return closeToInt[c]!!
                }
            }
        }
    }
    return 0
}

