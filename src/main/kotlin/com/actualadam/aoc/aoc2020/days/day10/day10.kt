package com.actualadam.aoc.aoc2020.days.day10

import com.actualadam.aoc.aoc2020.InputReader

val puzzleInput = InputReader.lines(10)

val adapterBag = puzzleInput.map{ it.toInt() }.sorted()
val startingJoltage = 0
val deviceAdapterJoltage = adapterBag.maxOrNull()!! + 3

fun findNextAdapter(adapters: List<Int>, curJoltage: Int): Int? {
    val qualified = qualifiedNextAdapters(adapters, curJoltage)
    return qualified.minOrNull()
}

private fun qualifiedNextAdapters(
    adapters: List<Int>,
    curJoltage: Int
) = adapters.filter { it - curJoltage in 1..3 }

fun countJoltageDifferences(adapters: List<Int> = adapterBag + deviceAdapterJoltage, curJoltage: Int = startingJoltage, acc: Pair<Int, Int> = Pair(0, 0)): Pair<Int, Int> {
    if (adapters.isEmpty()) { return acc }
    val nextAdapter =  findNextAdapter(adapters, curJoltage)
    val diff = nextAdapter!! - curJoltage
    val nextAcc = when(diff) {
        1 -> Pair(acc.first + 1, acc.second)
        3 -> Pair(acc.first, acc.second + 1)
        else -> acc
    }
    val nextBag = adapters.filter { it != nextAdapter }
    return countJoltageDifferences(nextBag, nextAdapter, nextAcc)
}

fun buildGraph(adapters: List<Int>): Map<Int, List<Int>> {
    val deviceAdapterJoltage = adapters.maxOrNull()!! + 3
    val adapters = (adapters + 0 + deviceAdapterJoltage).sorted()
    return adapters.map {
        Pair(it, qualifiedNextAdapters(adapters, it))
    }.toMap()
}

fun countAllPaths(graph: Map<Int, List<Int>>): Long {
    val visited = mutableListOf<Int>()
    val destination = graph.keys.maxOrNull()!!
    var pathCount = 0L
    fun helper(graph: Map<Int, List<Int>>, cur: Int = 0, acc: List<Int> = listOf()) {
        visited.add(cur)
        if (cur == destination) {
            pathCount++
        }
        graph[cur]!!.forEach {
            helper(graph, it, acc + cur)
        }
    }
    helper(graph)
    return pathCount
}

fun day10part1(puzzleInput: List<String>): Int {
    val differences = countJoltageDifferences()
    return differences.first * differences.second
}

fun day10part2(puzzleInput: List<String>): Long {
    val adapters = puzzleInput.map { it.toInt() }
    val graph = buildGraph(adapters)
    return countAllPaths(graph)
}

fun main() {
    val puzzleInput = InputReader.lines(10)
    println("Day 10, Part 1: ${day10part1(puzzleInput)}")
    println("Day 10, Part 2: ${day10part2(puzzleInput)}")
}
