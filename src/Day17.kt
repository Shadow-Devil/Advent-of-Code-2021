import utils.*

typealias Velocity = Point

object Day17{

    @JvmStatic
    fun main(args: Array<String>) {
        val testIn = Area(IntRange(20, 30), IntRange(-10, -5))
        println(simulate(Velocity(7, 2), testIn))
        println(simulate(Velocity(6, 3), testIn))
        println(simulate(Velocity(9, 0), testIn))
        println(simulate(Velocity(17, -4), testIn))



        val testInput = readInputToText("Day17")
        println(parse(testInput))
        test(1, testInput, -1)
        //test(2, testInput, -1)


        val input = readInputToText("Day17")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        //test(1, input, -1)
        //test(2, input, -1)
    }

    private fun step(p: Point, v: Velocity): Pair<Point, Velocity> =
        Point(p.x + v.x, p.y + v.y) to
        Velocity(v.x.decTowardsZero(), v.y.dec())


    private fun simulate(velocity: Velocity, tgt: Area): Pair<Boolean, Int> {
        var p = Point(0, 0)
        var v = velocity
        var highestY = p.y
        while(p.y > tgt.yRange.first){
            if(p in tgt) return true to highestY

            step(p, v).also { (newP, newV) ->
                p = newP
                v = newV
            }

            highestY = highestY.coerceAtLeast(p.y)
        }

        return false to highestY
    }

    private fun parse(input: String): Area{
        val r = Regex("""target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""")
        val (x1, x2, y1, y2) = r.matchEntire(input)?.destructured ?: error("Couldnt match")
        return Area(IntRange(x1.toInt(), x2.toInt()), IntRange(y1.toInt(), y2.toInt()))
    }

    private fun part1(input: String): Int {
        TODO("not implemented")
    }

    private fun part2(input: String): Int {
        TODO("not implemented")
    }



}