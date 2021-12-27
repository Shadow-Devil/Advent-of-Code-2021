package utils

data class Point(val x: Int, val y: Int)
data class Line(val p1: Point, val p2: Point)

data class Area(val xRange: IntRange, val yRange: IntRange){
    fun contains(x: Int, y: Int): Boolean = x in xRange && y in yRange
    operator fun contains(p: Point): Boolean = contains(p.x, p.y)
    fun map(any: Any) {

    }
    fun <T> map(transform: (Point) -> T): List<T> {
        val destination = ArrayList<T>()
        for (item in this)
            destination.add(transform(item))
        return destination
    }

    private operator fun iterator(): Iterator<Point> =
        iterator { for (x in xRange) for (y in yRange) yield(Point(x, y)) }

}