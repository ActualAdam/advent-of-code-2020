package com.actualadam.aoc.aoc2020.days.day03

import com.actualadam.aoc.aoc2020.InputReader

class Day03(val course: Array<BooleanArray>) {
    private val xbound = course[0].size
    private val ybound = course.size
    private tailrec fun countTreesInPath(acc: Long = 0, pos: Pair<Int, Int> = Pair(0, 0), slope: Pair<Int, Int>): Long {
        if (pos.second >= ybound) {
            return acc
        }
        val tree = if(course[pos.second][pos.first]) 1 else 0
        return countTreesInPath(acc + tree, pos + slope, slope)
    }

    private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        val xpos = (this.first + other.first) % xbound
        val ypos = this.second + other.second
        return Pair(xpos, ypos)
    }

    fun part1() = countTreesInPath(slope = Pair(3, 1))

    fun part2() = listOf(
            Pair(1, 1),
            Pair(3, 1),
            Pair(5, 1),
            Pair(7, 1),
            Pair(1, 2),
        ).map { countTreesInPath(slope = it) }
            .reduce(Long::times)
}

fun main() {
    val puzzleInput = InputReader.lines(3).map { row ->
        row.map {
            when (it) {
                '.' -> false
                '#' -> true
                else -> throw IllegalStateException("unexpected char in map: $it")
            }
        }.toBooleanArray()
    }.toTypedArray()
    val day3 = Day03(puzzleInput)
    println("Day 03, Part 1: ${day3.part1()}")
    println("Day 03, Part 2: ${day3.part2()}")
}
