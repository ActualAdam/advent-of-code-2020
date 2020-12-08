package com.actualadam.aoc.aoc2020.days.day06

import com.actualadam.aoc.aoc2020.InputReader

private tailrec fun parseAnswerGroups(acc: List<Set<Char>> = listOf(), input: List<String>): List<Set<Char>> {
    if (input.isEmpty() || input.all { it.isEmpty() }) {
        return acc
    }
    // burn leading empty line
    val rest = input.dropWhile { it.isEmpty() }
    // records are separated by empty lines.
    val answerGroup = rest.takeWhile { it.isNotEmpty() }
    val nextInput = rest - answerGroup
    return parseAnswerGroups(acc.plusElement(answerGroup.joinToString("").toSet()), nextInput)
}

private fun part1(puzzleInput: List<String>): Int {
    return parseAnswerGroups(input = puzzleInput).map {it.count()}.sum()

}

fun main() {
    val puzzleInput = InputReader.lines(6)
    println(puzzleInput.count())
    println("Day 06, Part 1: ${part1(puzzleInput)}")
}
