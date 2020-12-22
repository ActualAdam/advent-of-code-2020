package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader

val puzzleInput = InputReader.lines(10)
puzzleInput
val adapterBag = puzzleInput.map { it.toInt() }.sorted()
adapterBag

val startingJoltage = 0
val deviceAdapterJoltage = adapterBag.maxOrNull()!! + 3

fun findNextAdapter(adapters: List<Int>, curJoltage: Int): Int? {
    val qualified = qualifiedNextAdapters(adapters, curJoltage)
    return qualified.minOrNull()
}

fun qualifiedNextAdapters(adapters: List<Int>, curJoltage: Int) = adapters.filter { it - curJoltage in 1..3 }

fun buildGraph(adapters: List<Int>): Map<Int, List<Int>> {
    val deviceAdapterJoltage = adapters.maxOrNull()!! + 3
    val adapters = (adapters + 0 + deviceAdapterJoltage).sorted()
    return adapters.map {
        Pair(it, qualifiedNextAdapters(adapters, it))
    }.toMap()
}

fun findAllPaths(graph: Map<Int, List<Int>>): List<List<Int>> {
    val visited = mutableListOf<Int>()
    val destination = graph.keys.maxOrNull()!!
    val paths = mutableListOf<List<Int>>()
    fun helper(graph: Map<Int, List<Int>>, cur: Int = 0, acc: List<Int> = listOf()) {
        visited.add(cur)
        if (cur == destination) {
            paths.add(acc)
        }
        graph[cur]!!.forEach {
                helper(graph, it, acc + cur)
        }
    }
    helper(graph)
    return paths.toList()
}

fun countJoltageDifferences(
    adapters: List<Int> = adapterBag + deviceAdapterJoltage,
    curJoltage: Int = startingJoltage,
    acc: Pair<Int, Int> = Pair(0, 0)
): Pair<Int, Int> {
    if (adapters.isEmpty()) {
        return acc
    }
    val nextAdapter = findNextAdapter(adapters, curJoltage)
    val diff = nextAdapter!! - curJoltage
    val nextAcc = when (diff) {
        1 -> Pair(acc.first + 1, acc.second)
        3 -> Pair(acc.first, acc.second + 1)
        else -> acc
    }
    val nextBag = adapters.filter { it != nextAdapter }
    return countJoltageDifferences(nextBag, nextAdapter, nextAcc)
}

val example1 = """
   16
   10
   15
   5
   1
   11
   7
   19
   6
   12
   4
""".trimIndent().split("\n").map{ it.toInt() }

example1
val graph = buildGraph(example1)
graph
val paths = findAllPaths(graph)
paths.size
