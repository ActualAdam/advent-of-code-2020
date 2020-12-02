package com.actualadam.aoc.aoc2020

import java.io.File
import java.nio.file.Paths

object InputReader {
   fun lines(day: Int): List<String> {
       val dayString = day.toString().padStart(2, '0')
       return File(javaClass.getResource("./days/day$dayString/input.txt").toURI()).useLines {
            it.toList()
        }
   }
}
