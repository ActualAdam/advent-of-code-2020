package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader

val lines = InputReader.lines(4)

lines

lines.takeWhile { it.isNotBlank() }
val next = lines.dropWhile { it.isNotEmpty() }
next
next.dropWhile { it.isNotBlank() }
data class Passport(val map: Map<String, String>) {
    val byr: String? by map
    val iyr: String? by map
    val eyr: String? by map
    val hgt: String? by map
    val hcl: String? by map
    val ecl: String? by map
    val pid: String? by map
    val cid: String? by map
}

tailrec fun parseRecords(acc: List<Passport> = listOf(), input: List<String>): List<Passport> {
    if (input.isEmpty() || input.all { it.isEmpty() }) {
        return acc
    }
    // burn the blank
    val rest = input.dropWhile { it.isEmpty() }
    val record: Map<String, String> = rest.takeWhile { it.isNotEmpty() }
        .flatMap { it.split(" ") }
        .associate {
            val (k, v) = it.split(":")
            k to v
        }
    val passport = Passport(record)
    return parseRecords(
        acc = acc + passport,
        input = rest.dropWhile { it.isNotEmpty() })
}

val passports = parseRecords(input = lines)

passports[0].byr

