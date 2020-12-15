package com.actualadam.aoc.aoc2020.days.day07

import com.actualadam.aoc.aoc2020.InputReader

val bagModifierPattern =
    "(\\w+ \\w+) bags contain ((\\d+ (\\w+ \\w+) bag((s)*(, |.)))+)".toRegex() // excludes "no other entries"
val noOtherPattern = "(\\w+ \\w+) bags contain no.*$".toRegex()

/**
 * transposes the graph, but you lose the quantity information, because we wouldn't have quantities for every value. just preserves the color
 */
fun transpose(graph: Map<String, Set<BagSpec>>): Map<String, Set<String>> {
    val transposed = mutableMapOf<String, Set<String>>()
    graph.forEach { item ->
        item.value.forEach { bagspec ->
            val vertex = bagspec.color
            transposed[vertex] = transposed[vertex]?.plus(item.key) ?: setOf(item.key)
        }
    }
    return transposed
}

fun countReachableFrom(
    graph: Map<String, Set<String>>,
    startingVertex: String
): Int {
    val visited = mutableSetOf<String>()

    fun helper(graph: Map<String, Set<String>>, vertex: String) {
        visited.add(vertex)
        graph[vertex]?.forEach {
            helper(graph, it)
        }
    }
    helper(graph, startingVertex)
    return visited.count() - 1 //minus shiny gold
}

fun countReachableWithQuantity(
    graph: Map<String, Set<BagSpec>>,
    startingVertex: String
): Int {
    val visited = mutableMapOf<String, Int>()

    fun helper(graph: Map<String, Set<BagSpec>>, vertex: String, quantity: Int = 0) {
        visited.merge(vertex, quantity, Int::plus)  // it is in fact a directed graph not a tree, but
        graph[vertex]?.forEach {
            val quantityActualBags = it.quantity * quantity.coerceAtLeast(1)
            helper(graph, it.color, quantityActualBags)
        }
    }
    helper(graph, startingVertex)
    return visited.values.reduce(Int::plus)
}

fun parseBagRules(puzzleInput: List<String>): List<List<String>> = puzzleInput.map { line ->
    noOtherPattern.find(line)?.let {
        listOf(it.groupValues[1])
    } ?: bagModifierPattern.findAll(line).toList()
        .flatMap { res ->
            val vertex = res.groupValues[1]
            val adjacents = res.groupValues[2].split(",", ".").dropLastWhile { it.isBlank() }
                .map {
                    it.replace("\\Wbag(s)*$".toRegex(), "").trim()
                }
            listOf(vertex) + adjacents
        }
}.also {
    println("lines : ${it.count()}")
}

fun generateGraph(rules: List<List<String>>):Map<String, Set<BagSpec>> = rules.map { rule ->
    val key = rule.first()
    val value = rule.drop(1)
    val specs = value.map { BagSpec.parse(it) }.toSet()
    Pair(key, specs)
}.toMap()

data class BagSpec(
    val quantity: Int,
    val color: String,
) {
    companion object {
        fun parse(string: String): BagSpec {
            return BagSpec(
                quantity = string.takeWhile { it.isDigit() }.toInt(),
                color = string.dropWhile { it.isDigit() }.trim()
            )
        }
    }
}

fun day07part1(puzzleInput: List<String>): Int {
    val transposed =
        with(puzzleInput) {
            parseBagRules(this)
        }.let {
            generateGraph(it)
        }.let {
            transpose(it)
        }
    return countReachableFrom(transposed, start)
}

const val start = "shiny gold"

fun day07part2(puzzleInput: List<String>): Int {
    return with(puzzleInput) {
        parseBagRules(this)
    }.let {
        generateGraph(it)
    }.let {
        countReachableWithQuantity(it, start)
    }
}

fun main() {
    val puzzleInput = InputReader.lines(7)
    println("input lines: " + puzzleInput.count())
    println("Day 07, Part 1: ${day07part1(puzzleInput)}")
    println("Day 07, Part 2: ${day07part2(puzzleInput)}")
}
