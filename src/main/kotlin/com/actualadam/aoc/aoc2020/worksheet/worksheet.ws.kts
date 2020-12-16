package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader

val lines = InputReader.lines(7)

val example = """
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
""".trimIndent().split("\n")

example

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

example.map { Instruction.parse(it) }

data class ProgramState(
    val visitedInstructions: List<Int> = listOf(),
    val instructionIndex: Int = 0,
    val acc: Int = 0
)

tailrec fun processInstructions(instructions: List<Instruction>, state: ProgramState = ProgramState()): ProgramState {
    val instruction = instructions[state.instructionIndex]
    val visits = state.visitedInstructions + state.instructionIndex

    val nextIndex = getNextInstructionIndex(instruction, state)
    val nextAcc = getNextAcc(instruction, state)
    val nextState = ProgramState(visits, nextIndex, nextAcc)
    return if (visits.contains(nextIndex)) {
        state
    } else {
        processInstructions(instructions, nextState)
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

processInstructions(example.map { Instruction.parse(it)} )
