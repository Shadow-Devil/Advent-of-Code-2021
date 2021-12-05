import java.io.File

@Suppress("unused")
typealias SequenceOfBingoNumbers = List<Int>
typealias BingoBoard = List<List<Int>>
val lf: String = System.getProperty("line.separator")

object Day04{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = File("src", "Day04_test.txt").readText()
        test(1, testInput, 4512)
        test(2, testInput, 1924)


        val input = File("src", "Day04.txt").readText()
        println("Part 1: ${part1(input)}")
        test(1, input, 49686)
        println("Part 2: ${part2(input)}")
        test(2, input, 26878)
    }

    private fun parseInput(input: String): Pair<SequenceOfBingoNumbers, MutableList<BingoBoard>>{
        val (drawnNumbers, bingos) = input.split(lf + lf).split()
        return  drawnNumbers.split(",").map { it.trim().toInt() } to
                bingos.map { it.split(lf).map{ it.trim().split(" +".toRegex()).map { it.trim().toInt() } } }.toMutableList()
    }

    private fun part1(input: String): Int {
        val (allNumbers, bingos) = parseInput(input)
        val winner = getWinnersAndIdx(allNumbers, bingos).first()

        return winner.let { (idx, bingo) -> bingo.flatten().filter { it !in allNumbers.take(idx) }.sum() * allNumbers[idx - 1] }
    }

    private fun getWinners(bingos: List<BingoBoard>, drawnNumbers: SequenceOfBingoNumbers): List<BingoBoard> {
        return bingos.filter {bingo ->  bingo.any { drawnNumbers.containsAll(it) } }.union(
        bingos.filter { bingo -> List(bingo.size) { index ->
            bingo.map { it[index] }
        }.any { drawnNumbers.containsAll(it) }}).toList()
    }

    private fun getWinnersAndIdx(allNumbers: SequenceOfBingoNumbers, bingos: MutableList<BingoBoard>): List<Pair<Int, BingoBoard>> {
        var turn = 1
        val winners = mutableListOf<Pair<Int, BingoBoard>>()
        while (turn <= allNumbers.size && bingos.isNotEmpty()) {
            val drawnNumbers = allNumbers.take(++turn)
            val winnersForTurn = getWinners(bingos, drawnNumbers)
            winners.addAll(winnersForTurn.map { turn to it })
            bingos.removeAll(winnersForTurn)
        }
        return winners.toList()
    }


    private fun part2(input: String): Int {
        val (allNumbers, bingos) = parseInput(input)
        val winner = getWinnersAndIdx(allNumbers, bingos).last()

        return winner.let { (idx, bingo) -> bingo.flatten().filter { it !in allNumbers.take(idx) }.sum() * allNumbers[idx - 1] }
    }

}

