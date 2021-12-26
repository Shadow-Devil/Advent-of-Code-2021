import utils.*

object Day16 {
    private const val VERSION_SIZE = 3
    private const val ID_SIZE = 3
    private const val BITGROUP_SIZE = 4

    data class Packet(val version: Int, val id: Int, val children: List<Packet>, val literal: Long) {
        operator fun iterator(): Iterator<Packet> = iterator {
            yield(this@Packet)
            children.forEach { yieldAll(it.iterator()) }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        //testLiteral
        var testInput = "D2FE28"
        println("Test Literal: " + parseInput(testInput))
        testInput = "38006F45291200"
        println("Test sub-packets: " + parseInput(testInput))
        testInput = "EE00D40C823060"
        println("Test TotalLength: " + parseInput(testInput))

        testInput = readInputToText("Day16_test")
        println(parseInput(testInput))
        test(1, testInput, 16)
        //test(2, testInput, -1)


        val input = readInputToText("Day16")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        //test(1, input, -1)
        //test(2, input, -1)
    }

    private fun part1(input: String): Int {
        val packet = parseInput(input)
        val allPacketVersions = packet.fold(0) { acc, p ->
            acc + p.version
        }
        return allPacketVersions
    }

    private fun part2(input: String): Long = calculate(parseInput(input))

    private fun parseInput(input: String): Packet = parsePacket(input.hexToBin()).first

    private fun parsePacket(binarray: String): Pair<Packet, Int> {
        var pointer = 0
        //version
        val version = binarray.substring(pointer, pointer + VERSION_SIZE).toInt(2)
        pointer += VERSION_SIZE

        //id
        val id = binarray.substring(pointer, pointer + ID_SIZE).toInt(2)
        pointer += ID_SIZE

        //children
        val (children, literal) = if (id == 4) {
            val (literal, parsed) = parseLiteral(binarray, pointer)
            pointer = parsed
            Pair(emptyList<Packet>(), literal.toLong(2))
        } else {
            val children = mutableListOf<Packet>()
            if (binarray[pointer++] == '1') {
                // the next 11 bits are a number that represents
                // the number of sub-packets immediately contained by this packet
                var numberOfImmediateSubPackets = binarray.substring(pointer, pointer + 11).toInt(2)
                pointer += 11
                while (numberOfImmediateSubPackets-- > 0){
                    val subBinArray = binarray.substring(pointer)
                    val (child, nextIndex) = parsePacket(subBinArray)
                    pointer += nextIndex
                    children.add(child)
                }
            } else {
                // the next 15 bits are a number that represents
                // the total length in bits of the sub-packets contained by this packet.
                val totalLengthOfSubPackets = binarray.substring(pointer, pointer + 15).toInt(2)
                pointer += 15
                val start = pointer
                while (pointer < start + totalLengthOfSubPackets){
                    val subBinArray = binarray.substring(pointer)
                    val (child, nextIndex) = parsePacket(subBinArray)
                    pointer += nextIndex
                    children.add(child)
                }
                assert(pointer == totalLengthOfSubPackets)
            }
            children to -1L
        }
        return Packet(version, id, children, literal) to pointer
    }

    private fun parseLiteral(input: String, start: Int = 0): Pair<String, Int> {
        val pointer = start + 1
        val end = pointer + BITGROUP_SIZE
        val bitGroup = input.substring(pointer, end)

        return if (input[start] == '0') {
            bitGroup to end
        }else{
            val (bitGroups, endPointer) = parseLiteral(input, end)
            bitGroup + bitGroups to endPointer
        }
    }

    private fun calculate(packet: Packet): Long = packet.children.map { calculate(it) }.run {
        when (packet.id){
            0 -> sum()
            1 -> prod()
            2 -> minOrNull() ?: Long.MAX_VALUE
            3 -> maxOrNull() ?: Long.MIN_VALUE
            4 -> packet.literal
            5 -> (get(0) > get(1)).toLong()
            6 -> (get(0) < get(1)).toLong()
            7 -> (get(0) == get(1)).toLong()
            else -> error("Unknown Packet Id: " + packet.id)
        }
    }

    private fun <R> Packet.fold(initial: R, operation: (acc: R, Packet) -> R): R{
        var accumulator = initial
        for (element in this) accumulator = operation(accumulator, element)
        return accumulator
    }
}
