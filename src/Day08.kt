import utils.*

@Suppress("unused", "UNUSED_PARAMETER", "LocalVariableName")
object Day08{

    private data class Entry(val knownPatterns: List<String>, val toDisplay: List<String>)

    private class Segment(var ul: Char, var uc: Char, var ur: Char,
                           var c: Char,
                          var ll: Char, var lc: Char, var lr: Char){
        fun get(s: String): Int{
            val set = s.toSet()
            return when{
                set.size == 2 && set.containsAll(listOf(ur, lr)) -> 1
                set.size == 3 && set.containsAll(listOf(uc, ur, lr)) -> 7
                set.size == 4 && set.containsAll(listOf(ul, c, ur, lr)) -> 4
                set.size == 5 && set.containsAll(listOf(uc, ur, c, ll, lc)) -> 2
                set.size == 5 && set.containsAll(listOf(uc, ur, c, lr, lc)) -> 3
                set.size == 5 && set.containsAll(listOf(ul, uc, c, lr, lc)) -> 5
                set.size == 6 && set.containsAll(listOf(ul, uc, ur, ll, lr, lc)) -> 0
                set.size == 6 && set.containsAll(listOf(ul, uc, c, ll, lr, lc)) -> 6
                set.size == 6 && set.containsAll(listOf(ul, uc, ur, c, lr, lc)) -> 9
                set.size == 7 && set.containsAll(listOf(ul, uc, ur, c, ll, lr, lc)) -> 8
                else -> error("Invalid segment")
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInput("Day08_test")
        test(1, testInput, 26)
        test(2, testInput, 61229)


        val input = readInput("Day08")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 473)
        test(2, input, 1097568)
    }

    private fun parseInput(input: List<String>): List<Entry> {
        return input.map {
            val (knowPatterns, toDisplay) = it.split("|").map(String::trim)
            Entry(knowPatterns.split(" ").sortedBy { it.length }, toDisplay.split(" "))
        }
    }

    private fun part1(input: List<String>): Int =
        parseInput(input).sumOf{(_, toDisplay) -> toDisplay.count { it.length in listOf(2, 3, 4, 7) }}

    private fun part2(input: List<String>): Int = parseInput(input).sumOf { (knownPatterns, toDisplay) ->
        val one = knownPatterns[0].toMutableSet()
        val four = knownPatterns[2].toMutableSet()
        val seven = knownPatterns[1].toMutableSet()
        val two_three_five = knownPatterns.subList(3, 6).map { it.toMutableSet() }
        val three = two_three_five.single { one.except(it).isEmpty() }
        val zero_six_nine = knownPatterns.subList(6, 9).map { it.toMutableSet() }

        val eight = knownPatterns.last().toMutableSet()

        val a: Char = seven.except(one).single()
        val b: Char = four.except(three).single()

        val two = two_three_five.single { !it.contains(b) && it != three }
        val five = two_three_five.single { it.contains(b) }

        val c: Char = one.except(five).single()
        val f: Char = one.except(two).single()
        val six = zero_six_nine.single { !it.contains(c) }
        val e: Char = six.except(five).single()
        val zero_nine = zero_six_nine.filter { it.contains(c) }
        val zero = zero_nine.single { it.contains(e) }
        val d: Char = eight.except(zero).single()
        val g: Char = eight.except(setOf(a, b, c, d, e, f)).single()

        val segment = Segment(
            b, a, c,
            d,
            e, g, f,
        )

        toDisplay.map { segment.get(it) }.joinToString("").toInt()
    }
}