package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader

val lines = InputReader.lines(3)

lines

val course = lines.map { row ->
    row.map {
        when (it) {
            '.' -> false
            '#' -> true
            else -> throw IllegalStateException("unexpected char in map: $it")
        }
    }.toBooleanArray()
}.toTypedArray()

val xbound = course[0].size
val ybound = course.size
tailrec fun countTreesInPath(acc: Long = 0, pos: Pair<Int, Int> = Pair(0, 0), slope: Pair<Int, Int>): Long {
    if (pos.second >= ybound) {
        return acc
    }
    val tree = if(course[pos.second][pos.first]) 1 else 0
    return countTreesInPath(acc + tree, pos + slope, slope)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    val xpos = (this.first + other.first) % xbound
    val ypos = this.second + other.second
    return Pair(xpos, ypos)
}

Pair(31, 1) + Pair(1,1)

fun part1() = countTreesInPath(slope = Pair(3, 1))

fun part2() = listOf(
    Pair(1, 1),
    Pair(3, 1),
    Pair(5, 1),
    Pair(7, 1),
    Pair(1, 2),
).map { countTreesInPath(slope = it) }
    .reduce(Long::times)

90 * 244 * 97 * 92 * 48
90L * 244L * 97L * 92L * 48L

90 * 244 * 97 * 92
90L * 244L * 97L * 92L
part1()
part2()
