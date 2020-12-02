package com.actualadam.aoc.aoc2020.days.day01

import com.actualadam.aoc.aoc2020.InputReader

object Day01 {
    tailrec fun part1(entries: List<Int>): Int {
        if (entries.isEmpty()) {
            throw IllegalStateException("Out of entries.")
        }
        val first = entries.first()
        val rest = entries.drop(1)
        val matchingEntry = rest.filter { it + first == 2020 }
        if (matchingEntry.size > 1) {
            throw IllegalStateException("More matches than expected. First: $first Matches: $matchingEntry")
        }
        if (matchingEntry.size == 1) {
            return matchingEntry[0].times(first)
        }
        return part1(rest)
    }

    tailrec fun findEntriesThatMakeSumWhichDoesNotWork(entries: List<Int>, subsetSize: Int, targetSum: Int): List<Int> {
        require(entries.size >= subsetSize)
        require(subsetSize > 1) { "subset too small" }
        if (entries.size < subsetSize) {
            throw IllegalStateException("Out of entries.")
        }
        val fixed = entries.subList(0, subsetSize - 1)
        val rest = entries.drop(subsetSize - 1)
        val matchingEntry = rest.filter {
            val addends = fixed.plusElement(it)
            println("$addends = ${addends.sum()}")
            addends.sum() == targetSum }
        if (matchingEntry.size > 1) {
            throw IllegalStateException("More matches than expected. First: $fixed Matches: $matchingEntry")
        }
        if (matchingEntry.size == 1) {
            return fixed.plusElement(matchingEntry.first())
        }
        return findEntriesThatMakeSumWhichDoesNotWork(entries.drop(1), subsetSize, targetSum)
    }

    fun part2TheHardWay(entries: List<Int>): List<Int> {
        entries.forEach first@{ first ->
            entries.forEach second@{ second ->
                if (second == first) {
                    return@first
                }
                entries.forEach { third ->
                    if (third == second || third == first) {
                        return@second
                    }
                    val subset = listOf(first, second, third)
                    if (subset.sum() == 2020) {
                        return subset
                    }
                }
            }
        }
        throw IllegalStateException("couldn't find a match")
    }

    fun part2(entries: List<Int>): Int = part2TheHardWay(entries).reduceRight(Int::times)

}

fun main() {
    val puzzleInput = InputReader.lines(1).map { it.toInt() }
    println("Day 01, Part 1: ${Day01.part1(puzzleInput)}")
    println("Day 01, Part 2: ${Day01.part2(puzzleInput)}")
}
