package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader

val lines = InputReader.lines(1).map { it.toInt() }

fun part2TheHardWay(entries: List<Int>): Set<Int> {
    entries.forEach first@{ first ->
        entries.forEach second@{ second ->
            if (second == first) {
                return@first
            }
            entries.forEach { third ->
                if (third == second || third == first) {
                    return@second
                }
                val subset = setOf(first, second, third)
                if (subset.sum() == 2020) {
                    return subset
                }
            }
        }
    }
    throw IllegalStateException("couldn't find a match")
}

part2TheHardWay(lines)

