package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader
import com.actualadam.aoc.aoc2020.days.day09.findEncryptionWeakness
import java.lang.IllegalStateException

val lines = InputReader.lines(9)

// val longs = lines.map { it.toLong() }
// longs.size

val example = """
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
""".trimIndent().map{ it.toLong() }

findEncryptionWeakness(example)
