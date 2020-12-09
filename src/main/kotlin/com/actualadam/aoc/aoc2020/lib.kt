package com.actualadam.aoc.aoc2020

import java.io.File

object InputReader {
    fun lines(day: Int): List<String> {
        return file(day).useLines {
            it.toList()
        }
    }

    fun file(day: Int) = File(javaClass.getResource("./days/day${formatDay(day)}/input.txt").toURI())

    private fun formatDay(day: Int) = day.toString().padStart(2, '0')
}
