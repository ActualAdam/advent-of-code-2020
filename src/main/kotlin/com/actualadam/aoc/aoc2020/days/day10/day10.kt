package com.actualadam.aoc.aoc2020.days.day10

import com.actualadam.aoc.aoc2020.InputReader

val puzzleInput = InputReader.lines(10)

val adapterBag = puzzleInput.map { it.toInt() }.sorted()
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

tailrec fun countJoltageDifferences(
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

fun buildGraph(adapters: List<Int>): Map<Int, List<Int>> {
    val deviceAdapterJoltage = adapters.maxOrNull()!! + 3
    val adapters = (adapters + 0 + deviceAdapterJoltage).sorted()
    return adapters.map {
        Pair(it, qualifiedNextAdapters(adapters, it))
    }.toMap()
}

/**
 * Transposes a graph represented as an adjacency list. In other words returns a new graph with the edges going in
 * the opposite direction as the given graph.
 */
fun <K, V> transpose(graph: Map<K, List<V>>): Map<V, List<K>> {
    return graph.flatMap { entry -> entry.value.map { Pair(it, entry.key) } } // flatten and value -> key swap
        .groupBy { pair -> pair.first } // group pairs by new key
        .mapValues { entry -> entry.value.map { it.second } } // just the 2nd, the first is redundant with the key
}

fun day10part1(puzzleInput: List<String>): Int {
    val differences = countJoltageDifferences()
    return differences.first * differences.second
}

fun countPaths(adapters: List<Int>): Long {
    val adapters = (adapters + 0 + adapters.maxOrNull()!! + 3).sorted()
    val memo = mutableMapOf<Int, Long>()
    fun helper(adapter: Int): Long {
        if (adapter == 0) return 1
        return adapters.intersect((adapter - 3) until (adapter)).map {
            if(!memo.containsKey(it)) {
                memo[it] = helper(it)
            }
            memo[it]!!
        }.reduce(Long::plus)
    }
    return helper(adapters.maxOrNull()!!)
}

fun main() {
    val puzzleInput = InputReader.lines(10)
    println("Day 10, Part 1: ${day10part1(puzzleInput)}")
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

    val example2 = """
        28
        33
        18
        42
        31
        14
        46
        20
        48
        47
        24
        23
        49
        45
        19
        38
        39
        11
        1
        32
        25
        35
        8
        17
        7
        9
        4
        2
        34
        10
        3
    """.trimIndent().split("\n").map { it.toInt() }
    val puz = puzzleInput.map { it.toInt() }
    println("Day 10, Part 2: ${countPaths(puz)}")
}
