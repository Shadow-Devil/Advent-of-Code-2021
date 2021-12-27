import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import utils.*
import kotlin.math.absoluteValue

typealias Velocity = Point
typealias IntMatrix = Area

object Day17{
    enum class Direction{
        LEFT, RIGHT, TO_HIGH
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val testIn = Area(IntRange(20, 30), IntRange(-10, -5))
        println(simulate(Velocity(7, 2), testIn))
        println(simulate(Velocity(6, 3), testIn))
        println(simulate(Velocity(9, 0), testIn))
        println(simulate(Velocity(17, -4), testIn))
        println(simulate(Velocity(6, 9), testIn))


        val testInput = readInputToText("Day17_test")
        println(parse(testInput))
        test(1, testInput, 45)
        test(2, testInput, 112)


        val input = readInputToText("Day17")
        println("Part 1: ${part1(input)}")
        println("Part 2: ${part2(input)}")
        test(1, input, 7750)
        test(2, input, 4120)
    }

    private fun part1(input: String): Int = (parse(input).yRange.first.absoluteValue - 1).gaussSum()


    private fun part2(input: String): Int {
        val tgt = parse(input)
        val minX = findMinX(tgt.xRange)
        val maxX = tgt.xRange.last
        val minY = tgt.yRange.first
        val maxY = findMaxY(tgt.yRange)

        return IntMatrix(minX..maxX, minY..maxY).map { (x, y) ->
            simulate(Velocity(x, y), tgt)
        }.count { it.isNotEmpty() }
    }

    private fun parse(input: String): Area{
        val r = Regex("""target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)""")
        val (x1, x2, y1, y2) = r.matchEntire(input)?.destructured ?: error("Couldnt match")
        return Area(IntRange(x1.toInt(), x2.toInt()), IntRange(y1.toInt(), y2.toInt()))
    }

    private fun step(p: Point, v: Velocity): Pair<Point, Velocity> =
        Point(p.x + v.x, p.y + v.y) to
        Velocity(v.x.decTowardsZero(), v.y - 1)

    private fun simulate(velocity: Velocity, tgt: Area): Either<Direction, Int> {
        var p = Point(0, 0)
        var lastPoint = p
        var v = velocity
        var highestY = p.y
        while(p.y >= tgt.yRange.first){
            if(p in tgt) return Right(highestY)

            step(p, v).also { (newP, newV) ->
                lastPoint = p
                p = newP
                v = newV
            }

            highestY = highestY.coerceAtLeast(p.y)
        }
        return Left( when{
            lastPoint.x < tgt.xRange -> Direction.LEFT
            lastPoint.x > tgt.xRange -> Direction.RIGHT
            lastPoint.y < tgt.yRange -> Direction.TO_HIGH
            else -> Direction.TO_HIGH
        })
    }


    private fun findMinX(xRange: IntRange): Int{
        if(xRange.last < 0) TODO("not implemented")
        if(0 in xRange) return 0

        var result = 1
        while (result.gaussSum() < xRange){
            result++
        }
        return result
    }

    private fun findMaxY(yRange: IntRange): Int{
        if (0 in yRange) return Int.MAX_VALUE
        if(yRange > 0) TODO("not implemented")

        return yRange.first.absoluteValue - 1
    }
}
