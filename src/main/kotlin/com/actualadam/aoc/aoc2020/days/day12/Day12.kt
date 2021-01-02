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


private data class Position(
    val orientation: Orientation = Orientation.East,
    val northSouth: Int = 0,
    val eastWest: Int = 0
) {
    enum class Orientation(val degrees: Int) {
        North(0),
        East(90),
        South(180),
        West(270);

        companion object {
            private val orientationsByDegrees = values().associateBy { it.degrees }
            fun fromDegrees(degrees: Int): Orientation =
                orientationsByDegrees[degrees] ?: throw IllegalArgumentException("no specific orientation for $degrees")

            fun explement(degrees: Int): Int {
                require(degrees <= 360)
                return if (degrees == 0) 0 else 360 - degrees
            }
        }

        enum class RotationDirection {
            Left,
            Right;
        }

        /*
         * Returns a new Orientation with the given rotation applied. You can rotate left or right any number of degrees.
         */
        fun rotate(direction: RotationDirection, degrees: Int): Orientation {
            require(degrees >= 0)
            return when (direction) {
                RotationDirection.Left -> {
                    val degreesValue = (this.degrees - degrees) % 360
                    val leftAdjustedOrientation = if (degreesValue >= 0) {
                        degreesValue
                    } else {
                        explement(degreesValue.absoluteValue)
                    }
                    Orientation.fromDegrees(leftAdjustedOrientation)
                }
                RotationDirection.Right -> Orientation.fromDegrees((this.degrees + degrees) % 360)
            }
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
        Instruction.Action.North -> pos.copy(northSouth = pos.northSouth + ins.value)
        Instruction.Action.South -> pos.copy(northSouth = pos.northSouth - ins.value)
        Instruction.Action.East -> pos.copy(eastWest = pos.eastWest + ins.value)
        Instruction.Action.West -> pos.copy(eastWest = pos.eastWest - ins.value)
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
    return finalPosition.eastWest.absoluteValue + finalPosition.northSouth.absoluteValue
}


fun main() {
    val puzzleInput = InputReader.lines(12)
    println("Day 12, Part 1: ${day12part1(puzzleInput)}")
}
