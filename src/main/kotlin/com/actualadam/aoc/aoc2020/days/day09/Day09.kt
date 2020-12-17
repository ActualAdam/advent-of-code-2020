package com.actualadam.aoc.aoc2020.days.day09

import com.actualadam.aoc.aoc2020.InputReader
import kotlin.IllegalStateException

fun allPairs(items: List<Long>): List<Pair<Long, Long>> {
    return items.flatMapIndexed { i, v ->
        items.mapIndexedNotNull { ii, vv ->
            if (ii >= i) {
                Pair(v, vv)
            } else null
        }
    }
}
fun findBrokenNumber(longs: List<Long>): Long {
    for (i in longs.indices) {
        val windowStart = i
        val windowSize = 25
        val windowEnd = windowStart + windowSize
        val sublist = longs.subList(windowStart, windowEnd)
        val pairs = allPairs(sublist)
        val filtered = pairs.filter {
            it.first + it.second == longs[windowEnd]
        }
        if (filtered.isEmpty()) return longs[windowEnd]
    }
    throw IllegalStateException("couldn't find broken entry")
}

tailrec fun findContiguousAddends(longs: List<Long>, targetSum: Long, startingIndex: Int = 0, endingIndex: Int = startingIndex + 1): Long? {
    val subList = longs.subList(startingIndex, endingIndex + 1)
    val sum = subList.sum()
    return when {
        sum == targetSum -> {
            println("found it")
            subList.minOrNull()!! + subList.maxOrNull()!!
        }
        sum > targetSum -> {
            println("over")
            null
        }
        else -> {
            println(sum)
            findContiguousAddends(longs, targetSum, startingIndex, endingIndex + 1)
        }
    }
}

fun findEncryptionWeakness(longs: List<Long>): Long {
    val invalidNumber = findBrokenNumber(longs)
    println("target: $invalidNumber")
    for (i in longs.indices) {
        println("\nIndex $i")
        findContiguousAddends(longs, invalidNumber, i)?.let {
            return it
        }
    }
    throw IllegalStateException("couldn't find encryption weakness")
}

fun day09part1(puzzleInput: List<String>): Long {
    val longs = puzzleInput.map{ it.toLong() }
    return findBrokenNumber(longs)
}

fun day09part2(puzzleInput: List<String>): Long {
    val longs = puzzleInput.map { it.toLong() }
    return findEncryptionWeakness(longs)
}

fun main() {
    val puzzleInput = InputReader.lines(9)
    println("Day 09, Part 1: ${day09part1(puzzleInput)}")
    println("Day 09, Part 2: ${day09part2(puzzleInput)}")
}
