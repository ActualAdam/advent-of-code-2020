package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader
import com.actualadam.aoc.aoc2020.days.day07.countReachableWithQuantity
import com.actualadam.aoc.aoc2020.days.day07.generateGraph
import com.actualadam.aoc.aoc2020.days.day07.parseBagRules

val lines = InputReader.lines(7)

val pt2example = """
    shiny gold bags contain 2 dark red bags.         // 2 
    dark red bags contain 2 dark orange bags.        // 4
    dark orange bags contain 2 dark yellow bags.     // 8
    dark yellow bags contain 2 dark green bags.      // 16
    dark green bags contain 2 dark blue bags.        // 32
    dark blue bags contain 2 dark violet bags, 2 brilliant white bags.       // 64 + 64
    dark violet bags contain no other bags.
    brilliant white bags contain 2 shitty brown bags. 128
""".trimIndent().split('\n')

pt2example
val input = parseBagRules(pt2example)
input

val graph = generateGraph(input)

graph

val q = countReachableWithQuantity(graph, "shiny gold")

q

128 + 32 + 16 + 8 + 4 + 2 + 128
