data class Node(
    val name: String,
    val large: Boolean = name.all { !it.isLowerCase() },
    val isStart: Boolean = name == "start",
    val isEnd: Boolean = name == "end"
)

typealias Graph = Map<Node, List<Node>>

@Suppress("unused", "UNUSED_PARAMETER")
object Day12 {

    @JvmStatic
    fun main(args: Array<String>) {
        var testInput = parseGraph(readInput("Day12_test1"))
        test(1, testInput, 10)
        test(2, testInput, 36)

        testInput = parseGraph(readInput("Day12_test2"))
        test(1, testInput, 19)
        test(2, testInput, 103)

        testInput = parseGraph(readInput("Day12_test3"))
        test(1, testInput, 226)
        test(2, testInput, 3509)


        val input = parseGraph(readInput("Day12"))
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        //test(1, input, -1)
        //test(2, input, -1)

    }

    private fun parseGraph(input: List<String>): Graph {
        val graph = mutableMapOf<Node, MutableList<Node>>()
        input.forEach {
            val (from, to) = it.split("-")
            val nodeFrom = Node(from.trim())
            val nodeTo = Node(to.trim())
            graph.getOrPut(nodeFrom) { mutableListOf() }.add(nodeTo)
            graph.getOrPut(nodeTo) { mutableListOf() }.add(nodeFrom)
        }
        return graph
    }

    private fun part1(input: Graph): Int {
        assert(input.filter { it.key.isStart }.size == 1)
        val start: Node = input.filter { it.key.isStart }.entries.single().key

        val visited = mutableSetOf<Node>()

        fun getPathCountToEnd(current: Node, visited: MutableSet<Node>): Int {
            if (current.isEnd) return 1
            if (!current.large) visited.add(current)

            return input.unvisited(current, visited).sumOf { getPathCountToEnd(it, visited.toMutableSet()) }
        }
        return getPathCountToEnd(start, visited)
    }

    private fun part2(input: Graph): Int {
        assert(input.filter { it.key.isStart }.size == 1)
        val start: Node = input.filter { it.key.isStart }.entries.single().key

        fun getPathCountToEnd(
            current: Node,
            visited: MutableSet<Node>,
            usedDouble: Boolean,
            path: List<Node>
        ): Set<List<Node>> {
            if (current.isEnd) return mutableSetOf(path + current)
            val newPath = path + current
            val a = if (!(current.large || usedDouble || current.isStart))
                input.unvisited(current, visited).fold(setOf<List<Node>>()) { acc, next ->
                    acc union getPathCountToEnd(next, visited.copy(), true, newPath)
                }
            else setOf()

            if (!current.large) visited.add(current)

            return a.union(input.unvisited(current, visited).fold(setOf()) { acc, next ->
                acc union getPathCountToEnd(next, visited.copy(), usedDouble, newPath)
            })
        }
        return getPathCountToEnd(start, mutableSetOf(), false, mutableListOf()).size
    }

    private fun <E> MutableSet<E>.copy() = this.toMutableSet()

    private fun Graph.unvisited(current: Node, visited: Set<Node>) =
        this.getOrDefault(current, emptyList()).filter { !visited.contains(it) }
}
