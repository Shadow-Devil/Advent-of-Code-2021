import utils.readInput
import utils.split
import utils.test
import java.lang.Integer.max

object Day18{

    @JvmStatic
    fun main(args: Array<String>) {
        val testInput = readInput("Day18_test")
        test(1, testInput, 4140)
        test(2, testInput, 3993)


        val input = readInput("Day18")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 3654)
        test(2, input, 4578)
    }

    private fun part1(input: List<String>): Int {
        var (result, rest) = parse(input).split()
        for (sfn in rest) {
            result += sfn
        }
        return result.magnitude()
    }

    private fun part2(input: List<String>): Int {
        val sfn = parse(input)
        var result = -1
        for (x in sfn){
            for (y in sfn){
                if (x == y) continue
                result = max(result, (x.clone() + y.clone()).magnitude())
            }
        }

        return result
    }

    private fun parse(input: List<String>): List<SnailFishNumber> = input.map {

        fun findMiddle(s: String): Int{
            var lvl = 0
            s.forEachIndexed {idx, it ->
                if (it == '[') lvl++
                else if(it == ']') lvl--

                if(idx != 0 && lvl == 1 && it == ']') return idx + 1
            }
            error("no middle found \"$s\"")
        }

        fun parseInteral(s: String): SnailFishNumber{
            assert(s[0] == '[')

            when{
                Regex("""\[(-?\d+),(-?\d+)]""").matches(s) -> {
                    val (left, right) = Regex("""\[(-?\d+),(-?\d+)]""").matchEntire(s)!!.destructured
                    return SnailFishNumber(SnailFishNumber(left.toInt()), SnailFishNumber(right.toInt()))
                }
                Regex("""\[(-?\d+),(.*)]""").matches(s) -> {
                    val (left, right) = Regex("""^\[(-?\d+),(.*)]""").matchEntire(s)!!.destructured

                    return SnailFishNumber(
                        SnailFishNumber(left.toInt()),
                        parseInteral(right.trim())
                    )
                }
                Regex("""\[(.*),(-?\d+)]""").matches(s) -> {
                    val (left, right) = Regex("""^\[(.*),(-?\d+)]""").matchEntire(s)!!.destructured
                    return SnailFishNumber(
                        parseInteral(left.trim()),
                        SnailFishNumber(right.toInt())
                    )
                }
                else -> {
                    val middle = findMiddle(s)

                    return SnailFishNumber(
                        parseInteral(s.substring(1, middle)),
                        parseInteral(s.substring(middle + 1, s.lastIndex))
                    )
                }
            }
        }


        parseInteral(it)
    }

    class SnailFishNumber(
            left: SnailFishNumber?,
            right: SnailFishNumber?,
            private val value: Int? = null
        ){
            private var parent: SnailFishNumber? = null
            private var left = left
                set(value) {
                    field = value
                    field?.parent = this
                }

            private var right = right
                set(value) {
                    field = value
                    field?.parent = this
                }

            constructor(value: Int) : this(null, null, value)

            init {
                assert((left == null && right == null) || value == null)
                if(value == null){
                    left!!.parent = this
                    right!!.parent = this
                }
            }


            override fun toString(): String {
                if(value != null) return value.toString()
                return "[$left,$right]"
            }

            operator fun plus(other: SnailFishNumber): SnailFishNumber{
                if(value != null && other.value != null)
                    return SnailFishNumber(value + other.value)

                return SnailFishNumber(this, other, null).reduce()
            }

            private fun left(): SnailFishNumber?{
                if(value == null) TODO()

                var child: SnailFishNumber = this
                var pointer: SnailFishNumber? = parent
                while(pointer != null){
                    if(pointer.left == child){
                        //need to go up
                        child = pointer
                        pointer = pointer.parent
                    }else if(pointer.right == child){
                        pointer = pointer.left
                        //need to go down right
                        while (pointer!!.value == null){
                            pointer = pointer.right
                        }
                        return pointer
                    }else{
                        error("Child is not of parent")
                    }
                }
                //no left neighbor
                return null
            }

            private fun right(): SnailFishNumber?{
                if(value == null) error("")

                var child: SnailFishNumber = this
                var pointer: SnailFishNumber? = parent
                while(pointer != null){
                    if(pointer.right == child){
                        //need to go up
                        child = pointer
                        pointer = pointer.parent
                    }else if(pointer.left == child){
                        pointer = pointer.right
                        //need to go down right
                        while (pointer!!.value == null){
                            pointer = pointer.left
                        }
                        return pointer
                    }else{
                        error("Child is not of parent")
                    }
                }
                //no right neighbor
                return null
            }

            private fun setChild(old: SnailFishNumber, new: SnailFishNumber?){
                when(old){
                    left -> left = new
                    right -> right = new
                    else -> error("$old was not a child left=$left, right=$right")
                }
            }

            private tailrec fun reduce(): SnailFishNumber{
                val explodeSFN = findExplodeSFN()
                if(explodeSFN != null){
                    val explodeParent = explodeSFN.parent ?: error("")
                    val (l, r) = explodeSFN.run { left to right }
                    if(l != null && r != null && l.value != null && r.value != null){
                        val ll = l.left()
                        val rr = r.right()
                        if(ll != null) ll.parent?.setChild(ll, ll + l)
                        if(rr != null) rr.parent?.setChild(rr, rr + r)
                        explodeParent.setChild(explodeSFN, SnailFishNumber(0))
                        return reduce()
                    }else{
                        error("")
                    }

                }

                val split = findSplit()
                if(split != null){
                    assert(this != split && split.value != null)
                    val splitVal = split.value!!
                    val splitParent = split.parent!!
                    val newValue = splitVal / 2
                    splitParent.setChild(split, SnailFishNumber(SnailFishNumber(newValue), SnailFishNumber(newValue + splitVal % 2)))
                    return reduce()
                }
                return this
            }

            private fun findSplit(): SnailFishNumber?{
                if(value != null) return this.takeIf { value >= 10 }
                return left!!.findSplit() ?: right!!.findSplit()
            }

            private fun findExplodeSFN(level: Int = 0): SnailFishNumber? {
                if(value != null) return null
                if(level >= 4) return this

                return  left!!.findExplodeSFN(level + 1)
                    ?: right!!.findExplodeSFN(level + 1)
            }

            fun magnitude(): Int {
                if (value != null) return 0
                var result = 0
                val leftValue = left!!.value
                val rightValue = right!!.value
                if(leftValue != null){
                    result += leftValue * 3
                }

                if(rightValue != null){
                    result += rightValue * 2
                }
                return result + 3 * left!!.magnitude() + 2 * right!!.magnitude()
            }

            fun clone(): SnailFishNumber {
                return SnailFishNumber(left?.clone(), right?.clone(), value)
            }
        }
}