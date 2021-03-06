package com.actualadam.aoc.aoc2020.days.day08

import com.actualadam.aoc.aoc2020.InputReader

data class Instruction(
    val operation: Operation,
    val argument: Int
) {
    enum class Operation {
        acc,
        jmp,
        nop;
    }

    companion object {
        fun parse(string: String): Instruction {
            val items = string.split(" ")

            return Instruction(
                operation = Operation.valueOf(items.first()),
                argument = items[1].toInt()
            )
        }
    }
}

data class ProgramState(
    val visitedInstructions: List<Int> = listOf(),
    val instructionIndex: Int = 0,
    val acc: Int = 0,
    val terminatedNormally: Boolean = true,
)

tailrec fun processInstructions(
    instructions: List<Instruction>,
    state: ProgramState = ProgramState()
): ProgramState {
    if (state.instructionIndex == instructions.size) {
        return state
    }
    val instruction = instructions[state.instructionIndex]
    val visits = state.visitedInstructions + state.instructionIndex

    val nextIndex = getNextInstructionIndex(instruction, state)
    val nextAcc = getNextAcc(instruction, state)
    val nextState = ProgramState(visits, nextIndex, nextAcc)
    return if (visits.contains(nextIndex)) {
        state.copy(terminatedNormally = false)
    } else {
        processInstructions(instructions, nextState)
    }
}

fun repairProgram(instructions: List<Instruction>): ProgramState {
    for (index in instructions.indices) {
        val repairAttempt = instructions.mapIndexed { i, v ->
            if (index == i) {
                repairInstruction(v)
            } else {
                v
            }
        }
        val state = processInstructions(repairAttempt)
        if (state.terminatedNormally) {
            return state
        }
    }
    throw IllegalStateException("Didn't find a repair that causes the program to terminate normally")
}

fun repairInstruction(instruction: Instruction): Instruction {
    return when (instruction.operation) {
        Instruction.Operation.nop -> instruction.copy(operation = Instruction.Operation.jmp)
        Instruction.Operation.jmp -> instruction.copy(operation = Instruction.Operation.nop)
        else -> instruction
    }
}

fun getNextAcc(instruction: Instruction, state: ProgramState): Int {
    return when (instruction.operation) {
        Instruction.Operation.acc -> state.acc + instruction.argument
        else -> state.acc
    }
}

fun getNextInstructionIndex(instruction: Instruction, state: ProgramState): Int {
    return when (instruction.operation) {
        Instruction.Operation.jmp -> state.instructionIndex + instruction.argument
        else -> state.instructionIndex + 1
    }
}

fun day08part1(puzzleInput: List<String>): Int {
    val instructions = puzzleInput.map { Instruction.parse(it) }
    val stoppedState = processInstructions(instructions)
    return stoppedState.acc
}

fun day08part2(puzzleInput: List<String>): Int {
    val instructions = puzzleInput.map { Instruction.parse(it) }
    return repairProgram(instructions).acc
}

fun main() {
    val puzzleInput = InputReader.lines(8)
    println("Day 08, Part 1: ${day08part1(puzzleInput)}")
    println("Day 08, Part 2: ${day08part2(puzzleInput)}")
}
