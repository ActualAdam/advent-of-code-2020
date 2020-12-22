package com.actualadam.aoc.aoc2020

import java.io.File

object InputReader {
    fun lines(day: Int, fileName: String = "input.txt"): List<String> {
        return file(day, fileName).useLines {
            it.toList()
        }
    }

    fun file(day: Int, fileName: String = "input.txt") = File(javaClass.getResource("./days/day${formatDay(day)}/$fileName").toURI())

    private fun formatDay(day: Int) = day.toString().padStart(2, '0')
}


