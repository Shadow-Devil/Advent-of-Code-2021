import utils.*

object Day14{

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = readInputToText("Day14_test")
        test(1, testInput, 1588)
        test(2, testInput, 2188189693529)


        val input = readInputToText("Day14")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        //test(1, input, -1)
        //test(2, input, -1)
    }

    private fun part1(input: String): Int {
        val (template, rules) = parseInput(input)
        var result = template
        repeat(10){
            result = result.windowed(2).joinToString(separator = "") {
                if (rules.contains(it)) {
                    it[0] + rules[it]!!
                } else {
                    it[0].toString()
                }
            } + result.last()
        }
        return result.let {
            val groups = it.groupBy { it }
            val max = groups.maxOf { it.value.size }
            val min = groups.minOf { it.value.size }
            max - min
        }
    }

    private fun parseInput(input: String): Pair<String, Map<String, String>> {
        val (template, rules) = input.split(lf + lf)
        return template to rules.lines().associate { it.split(" -> ").let { (from, to) -> from to to } }
    }

    private fun part2(input: String): Long {
        val (template, rules) = parseInput(input)
        val fst = template.first()
        val lst = template.last()
        var patterns = buildMap<String, Long>{
            template.windowed(2).forEach{
                put(it, getOrDefault(it, 0) + 1)
            }
        }.toMutableMap()

        repeat(40){
            val newPatterns = mutableMapOf<String, Long>()
            rules.forEach{ (pair, v) ->
                val c = patterns[pair]
                if(c != null){
                    val pattern1 = pair[0] + v
                    val pattern2 = v + pair[1]
                    newPatterns[pattern1] = newPatterns.getOrDefault(pattern1, 0) + c
                    newPatterns[pattern2] = newPatterns.getOrDefault(pattern2, 0) + c
                }
                //TODO why does this work? What if we have a pattern that has no rule?
            }
            patterns = newPatterns
        }
        val counts = patterns.entries.fold(mutableMapOf<Char, Long>()){ acc, cur ->
            acc[cur.key[0]] = acc.getOrDefault(cur.key[0], 0) + cur.value
            acc[cur.key[1]] = acc.getOrDefault(cur.key[1], 0) + cur.value
            acc
        }.mapValues { it.value / 2 }.toMutableMap()
        counts[fst] = counts.getOrDefault(fst, 0) + 1
        counts[lst] = counts.getOrDefault(lst, 0) + 1


        return counts.maxOf { it.value } - counts.minOf { it.value }
    }



}