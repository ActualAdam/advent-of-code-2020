package com.actualadam.aoc.aoc2020.days.day05

import com.actualadam.aoc.aoc2020.InputReader

private const val MAX_ROWS = 128
private const val MAX_COLS = 8

fun calculateSeatId(seat: Seat) = seat.row * MAX_COLS + seat.col

private enum class BspInstruction {
    Upper,
    Lower;

    companion object {
        fun fromRowInstructions(char: Char): BspInstruction {
            return when (char) {
                'F' -> Lower
                'B' -> Upper
                else -> throw IllegalArgumentException("Unrecognized row instruction: $char")
            }
        }

        fun fromColInstruction(char: Char): BspInstruction {
            return when (char) {
                'L' -> Lower
                'R' -> Upper
                else -> throw IllegalArgumentException("Unrecognized row instruction: $char")
            }
        }
    }
}

private fun bspTraverse(instructions: List<BspInstruction>, range: IntRange): Int {
    val count = range.count()
    if (count == 1) {
        return range.first
    }
    require(count % 2 == 0)
    val nextRange = when (instructions.first()) {
        BspInstruction.Upper -> IntRange(range.first + count / 2, range.last)
        BspInstruction.Lower -> IntRange(range.first, range.first + count / 2 - 1)
    }
    return bspTraverse(instructions.drop(1), nextRange)
}

data class Seat(
    val row: Int,
    val col: Int
)

fun calculateSeatAssignments(input: List<String>): List<Seat> {
    return input.map { instructions ->
        val row = instructions.take(7)
            .map { BspInstruction.fromRowInstructions(it) }
            .let { bspTraverse(it, IntRange(0, MAX_ROWS - 1)) }
        val col = instructions.takeLast(3)
            .map { BspInstruction.fromColInstruction(it) }
            .let { bspTraverse(it, IntRange(0, MAX_COLS - 1)) }
        Seat(row, col)
    }
}

object Day05Part1 {
    fun part1(puzzleInput: List<String>): Int? {
        return calculateSeatAssignments(puzzleInput)
            .map { calculateSeatId(it) }
            .maxOrNull()
    }
}

object Day05Part2 {
    fun part2(puzzleInput: List<String>): Int {
        return calculateSeatAssignments(puzzleInput)
            .map { calculateSeatId(it) }
            .let { seatIds ->
                // show me the first value (shifting for those first missing seats)
                // that doesn't track with the indices
                // the value before that is the missing seat
                seatIds.sorted().filterIndexed { i, v ->
                    i + seatIds.minOrNull()!! != v
                }.first() - 1
            }
    }
}

fun main() {
    val puzzleInput = InputReader.lines(5)

    println("Day 05, Part 1: ${Day05Part1.part1(puzzleInput)}")
    println("Day 05, Part 2: ${Day05Part2.part2(puzzleInput)}")
}
