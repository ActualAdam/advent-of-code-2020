package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader
import com.actualadam.aoc.aoc2020.days.day05.calculateSeatAssignments
import com.actualadam.aoc.aoc2020.days.day05.calculateSeatId

val lines = InputReader.lines(5)

lines

val seatIds = calculateSeatAssignments(lines)
    .map{ calculateSeatId(it) }

seatIds.maxOrNull()
seatIds.minOrNull()
seatIds.count()

894 - 888

seatIds.sorted().filterIndexed { i, v ->
    i + seatIds.minOrNull()!! != v
}.first() - 1

seatIds.sorted()
