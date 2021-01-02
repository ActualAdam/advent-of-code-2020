package com.actualadam.aoc.aoc2020.days.day12

import com.actualadam.aoc.aoc2020.InputReader
import kotlin.math.absoluteValue

private data class Instruction(
    val action: Action,
    val value: Int
) {
    companion object {
        fun parse(str: String) = Instruction(Action.parse(str[0]), str.drop(1).toInt())
    }

    enum class Action(val char: Char) {
        North('N'),
        South('S'),
        West('W'),
        East('E'),
        Forward('F'),
        Right('R'),
        Left('L');

        companion object {
            private val actionsByChar = values().associateBy { it.char }
            fun parse(char: Char): Action = actionsByChar[char] ?: throw IllegalArgumentException("no Action for $char")
        }
    }
}

private val orientationToAction = mapOf(
    Position.Orientation.North to Instruction.Action.North,
    Position.Orientation.South to Instruction.Action.South,
    Position.Orientation.West to Instruction.Action.West,
    Position.Orientation.East to Instruction.Action.East,
)

private fun move(pos: Position, ins: Instruction): Position {
    return when (ins.action) {
        Instruction.Action.North -> pos.copy(location = pos.location.copy(northSouth = pos.location.northSouth + ins.value))
        Instruction.Action.South -> pos.copy(location = pos.location.copy(northSouth = pos.location.northSouth - ins.value))
        Instruction.Action.East -> pos.copy(location = pos.location.copy(eastWest = pos.location.eastWest + ins.value))
        Instruction.Action.West -> pos.copy(location = pos.location.copy(eastWest = pos.location.eastWest - ins.value))
        Instruction.Action.Forward -> move(
            pos,
            Instruction(orientationToAction[pos.orientation]!!, ins.value)
        ) // recursion loosely guaranteed to terminate because of the contents of orientationToAction
        Instruction.Action.Right -> pos.copy(
            orientation = pos.orientation.rotate(
                Position.Orientation.RotationDirection.Right,
                ins.value
            )
        )
        Instruction.Action.Left -> pos.copy(
            orientation = pos.orientation.rotate(
                Position.Orientation.RotationDirection.Left,
                ins.value
            )
        )
    }
}


private tailrec fun runCourse(position: Position = Position(), instructions: List<Instruction>): Position {
    return if (instructions.isEmpty()) {
        position
    } else {
        runCourse(move(position, instructions.first()), instructions.drop(1))
    }
}


fun day12part1(puzzleInput: List<String>): Int {
    val instructions = puzzleInput.map{ Instruction.parse(it) }
    val finalPosition = runCourse(instructions = instructions)
    return finalPosition.location.eastWest.absoluteValue + finalPosition.location.northSouth.absoluteValue
}


fun main() {
    val puzzleInput = InputReader.lines(12)
    println("Day 12, Part 1, expect 1601: ${day12part1(puzzleInput)}")
}
