package com.actualadam.aoc.aoc2020.days.day02

import com.actualadam.aoc.aoc2020.InputReader

object Day02 {
    val inputPattern = "^(\\d+)-(\\d+)\\s(\\w):\\s(\\w+)$".toRegex()

    object Part1 {
        data class PolicyPassword(
            val validOccurrences: IntRange,
            val char: Char,
            val password: String
        ) {
            fun isValid(): Boolean =
                validOccurrences.contains(password.count { it == char })

            companion object {
                fun parse(item: String): PolicyPassword {
                    val result = inputPattern.find(item)!!
                    return PolicyPassword(
                        IntRange(
                            result.groupValues[1].toInt(),
                            result.groupValues[2].toInt()
                        ),
                        result.groupValues[3].toCharArray().first(),
                        result.groupValues[4]
                    )
                }
            }
        }

        fun part1(input: List<String>): Int {
            return input.map { PolicyPassword.parse(it) }
                .count { it.isValid() }
        }
    }

    object Part2 {
        data class PolicyPassword(
            val positions: List<Int>,
            val char: Char,
            val password: String
        ) {
            fun isValid(): Boolean = positions.filter { passwordCharAtPosition(it) == char }.count() == 1

            private fun passwordCharAtPosition(position: Int): Char = password[position - 1]

            companion object {
                fun parse(item: String): PolicyPassword {
                    val result = inputPattern.find(item)!!
                    return PolicyPassword(
                        listOf(
                            result.groupValues[1].toInt(),
                            result.groupValues[2].toInt()
                        ),
                        result.groupValues[3].toCharArray().first(),
                        result.groupValues[4]
                    )
                }
            }
        }

        fun part2(input: List<String>): Int = input.map { PolicyPassword.parse(it) }.count { it.isValid() }
    }
}

fun main() {
    val puzzleInput = InputReader.lines(2)
    println("Day 02, Part 1: ${Day02.Part1.part1(puzzleInput)} passwords are valid.")
    println("Day 02, Part 2: ${Day02.Part2.part2(puzzleInput)} passwords are valid.")
}
