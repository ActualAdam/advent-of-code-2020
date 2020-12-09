package com.actualadam.aoc.aoc2020.days.day06

import com.actualadam.aoc.aoc2020.InputReader

private tailrec fun parseAnswerGroups(input: List<String>, acc: List<List<String>> = listOf()): List<List<String>> {
    if (input.isEmpty() || input.all { it.isEmpty() }) {
        return acc
    }
    val allAnswers = input.dropWhile { it.isEmpty() } // burn a possible leading empty line
    val answerGroup = allAnswers.takeWhile { it.isNotEmpty() }
    val rest = allAnswers.dropWhile { it.isNotEmpty() }
    return parseAnswerGroups(rest, acc.plusElement(answerGroup))
}

private fun calculateGroupAnswerCounts(puzzleInput: List<String>, reducer: (Set<Char>, Set<Char>) -> Set<Char>) : Int {
    return parseAnswerGroups(input = puzzleInput).map { group ->
        group.map { it.toSet() }
            .reduce(reducer)
            .count()
    }.sum()
}

private fun union() = { acc:Set<Char>, str: Set<Char> -> acc.union(str) }
private fun intersect() = { acc: Set<Char>, str: Set<Char> -> acc.intersect(str) }

private fun part1(puzzleInput: List<String>): Int {
    return calculateGroupAnswerCounts(puzzleInput, union())
}

private fun part2(puzzleInput: List<String>): Int {
    return calculateGroupAnswerCounts(puzzleInput, intersect())
}

fun main() {
    val puzzleInput = InputReader.lines(6)
    println("Day 06, Part 1: ${part1(puzzleInput)}")
    println("Day 06, Part 2: ${part2(puzzleInput)}")
}
