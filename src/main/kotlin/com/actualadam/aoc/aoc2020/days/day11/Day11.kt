package com.actualadam.aoc.aoc2020.days.day11

import com.actualadam.aoc.aoc2020.InputReader
import com.actualadam.aoc.aoc2020.days.day11.Day11Part1.settleInSeats
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

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

object Day11Part1 {

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
}

fun west(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row, pos.col - 1)
fun east(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row, pos.col + 1)
fun north(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row - 1, pos.col)
fun south(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row + 1, pos.col)
fun northwest(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row - 1, pos.col - 1)
fun northeast(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row - 1, pos.col + 1)
fun southwest(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row + 1, pos.col - 1)
fun southeast(layout: List<DeckPosition>, pos: DeckPosition) = findByCoordinates(layout, pos.row + 1, pos.col + 1)

object Day11Part2 {
    private fun nextSeat(
        layout: List<DeckPosition>,
        startPos: DeckPosition,
        direction: (List<DeckPosition>, DeckPosition) -> DeckPosition?,
        curPos: DeckPosition? = startPos
    ): DeckPosition? {
        return if (curPos != startPos && curPos?.state != DeckPositionState.Floor) {
            curPos
        } else {
            val nextPos = direction(layout, curPos)
            nextSeat(layout, startPos, direction, nextPos)
        }
    }

    private fun adjacentSeats(layout: List<DeckPosition>, pos: DeckPosition): List<DeckPosition> = listOf(
        nextSeat(layout, pos, ::north),
        nextSeat(layout, pos, ::northeast),
        nextSeat(layout, pos, ::east),
        nextSeat(layout, pos, ::southeast),
        nextSeat(layout, pos, ::south),
        nextSeat(layout, pos, ::southwest),
        nextSeat(layout, pos, ::west),
        nextSeat(layout, pos, ::northwest),
    ).filterNotNull()

    tailrec fun reseat(layout: List<DeckPosition>): List<DeckPosition> {
        fun helper(layout: List<DeckPosition>): List<DeckPosition> = layout.map { pos ->
            when (pos.state) {
                DeckPositionState.EmptySeat ->
                    if (adjacentSeats(layout, pos).none { it.state == DeckPositionState.OccupiedSeat }
                    ) {
                        pos.copy(state = DeckPositionState.OccupiedSeat)
                    } else {
                        pos
                    }
                DeckPositionState.OccupiedSeat ->
                    if (adjacentSeats(layout, pos).count { it.state == DeckPositionState.OccupiedSeat } >= 5
                    ) {
                        pos.copy(state = DeckPositionState.EmptySeat)
                    } else {
                        pos
                    }
                else -> pos
            }
        }

        val reseated = helper(layout)
        return if (reseated == layout) layout else reseat(reseated)
    }
}

fun day11part1(puzzleInput: List<String>): Int {
    return settleInSeats(parseInput(puzzleInput)).count { it.state == DeckPositionState.OccupiedSeat }
}

fun day11part2(puzzleInput: List<String>): Int {
    return Day11Part2.reseat(parseInput(puzzleInput)).count { it.state == DeckPositionState.OccupiedSeat }
}

@ExperimentalTime
fun main() {
    val puzzleInput = InputReader.lines(11)
    println("Day 11, Part 1: ${day11part1(puzzleInput)}")
    val mark = TimeSource.Monotonic.markNow()
    println("Day 11, Part 2: ${day11part2(puzzleInput)}")
    println(mark.elapsedNow())
}

