package com.actualadam.aoc.aoc2020.days.day11

import com.actualadam.aoc.aoc2020.InputReader

data class DeckPosition(val row: Int, val col: Int, val state: DeckPositionState)

enum class DeckPositionState(val display: Char) {
    OccupiedSeat('#'),
    EmptySeat('L'),
    Floor('.');

    companion object {
        private val statesByDisplay = DeckPositionState.values().associateBy { it.display }
        fun parse(display: Char) = statesByDisplay[display] ?: throw IllegalArgumentException("no state: $display")
    }
}

fun findByCoordinates(layout: List<DeckPosition>, row: Int, col: Int): DeckPosition? =
    layout.find { it.row == row && it.col == col }

fun parseInput(input: List<String>): List<DeckPosition> {
    return input.flatMapIndexed { rowNum, row ->
        row.mapIndexed { colNum, charValue ->
            DeckPosition(rowNum, colNum, DeckPositionState.parse(charValue))
        }
    }
}

fun buildGraph(layout: List<DeckPosition>): Map<DeckPosition, List<DeckPosition>> {
    return layout.map {
        val southRow = it.row + 1
        val northRow = it.row - 1
        val eastCol = it.col + 1
        val westCol = it.col - 1

        val adjacents = listOf(
            findByCoordinates(layout, northRow, westCol),
            findByCoordinates(layout, northRow, it.col),
            findByCoordinates(layout, northRow, eastCol),
            findByCoordinates(layout, it.row, westCol),
            findByCoordinates(layout, it.row, eastCol),
            findByCoordinates(layout, southRow, westCol),
            findByCoordinates(layout, southRow, it.col),
            findByCoordinates(layout, southRow, eastCol),
        ).filterNotNull()
        Pair(it, adjacents)
    }.toMap()
}

fun reseat(layout: List<DeckPosition>): List<DeckPosition> {
    val graph = buildGraph(layout)
    return layout.map { pos ->
        when (pos.state) {
            DeckPositionState.EmptySeat -> if (graph[pos]!!.none { it.state == DeckPositionState.OccupiedSeat }) {
                pos.copy(state = DeckPositionState.OccupiedSeat)
            } else pos
            DeckPositionState.OccupiedSeat -> if (graph[pos]!!.count { it.state == DeckPositionState.OccupiedSeat } >= 4) {
                pos.copy(state = DeckPositionState.EmptySeat)
            } else pos
            else -> pos
        }
    }
}

tailrec fun settleInSeats(layout: List<DeckPosition>): List<DeckPosition> {
    val reseated = reseat(layout)
    return if (reseated == layout) {
        reseated
    } else {
        settleInSeats(reseated)
    }
}
fun day11part1(puzzleInput: List<String>): Int {
    return settleInSeats(parseInput(puzzleInput)).count { it.state == DeckPositionState.OccupiedSeat}
}

fun main() {
    val puzzleInput = InputReader.lines(11)
    println("Day 11, Part 1: ${day11part1(puzzleInput)}")
}

