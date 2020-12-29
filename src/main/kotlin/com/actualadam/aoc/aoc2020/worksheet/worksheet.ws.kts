package com.actualadam.aoc.aoc2020.worksheet

import arrow.syntax.function.memoize
import com.actualadam.aoc.aoc2020.InputReader
import java.lang.Integer.sum

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
""".trimIndent().split("\n").map { it.toInt() }

val adapters = (example1 + 0 + (example1.maxOrNull()!! + 3)).sorted()

adapters
val adapter = 22
// countPaths(22)
example1
val graph = buildGraph(example1)
graph

fun <K, V> transpose(graph: Map<K, List<V>>): Map<V, List<K>> {
    return graph.flatMap { entry -> entry.value.map { Pair(it, entry.key) } } // flatten and value -> key swap
        .groupBy { pair -> pair.first } // group pairs by new key
        .mapValues { entry -> entry.value.map { it.second } } // just the 2nd, the first is redundant with the key
}

val transposed = transpose(graph)

graph.keys.count()
transposed.keys.count()
transposed
val pathCountByVertex = mutableMapOf<Int, Int>()

val destination = example1.maxOrNull()!!
val source = 0

fun countPaths(graph: Map<Int, List<Int>>, from: Int, to: Int): Long {
    var pathCount = 0L
    fun helper(cur: Int) {
        if (cur == to) {
            pathCount++
        } else {
            graph[cur]!!.forEach {
                helper(it)
            }
        }
    }
    return pathCount
}

val memoizedCountPaths = { graph: Map<Int, List<Int>>, from: Int, to:Int -> countPaths(graph, from, to) }.memoize()

