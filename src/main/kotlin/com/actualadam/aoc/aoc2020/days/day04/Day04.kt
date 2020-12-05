package com.actualadam.aoc.aoc2020.days.day04

import com.actualadam.aoc.aoc2020.InputReader

data class Passport(val map: Map<String, String>) {
    val byr: String? by map.withDefault { null }
    val iyr: String? by map.withDefault { null }
    val eyr: String? by map.withDefault { null }
    val hgt: String? by map.withDefault { null }
    val hcl: String? by map.withDefault { null }
    val ecl: String? by map.withDefault { null }
    val pid: String? by map.withDefault { null }
    val cid: String? by map.withDefault { null }
}

private tailrec fun parseRecords(acc: List<Passport> = listOf(), input: List<String>): List<Passport> {
    if (input.isEmpty() || input.all { it.isEmpty() }) {
        return acc
    }
    // burn the blank
    val rest = input.dropWhile { it.isEmpty() }
    // records are separated by empty lines.  to a map
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

class Day04Part1(val puzzleInput: List<String>) {

    private fun Passport.isValid(): Boolean {
        return byr != null &&
            iyr != null &&
            eyr != null &&
            hgt != null &&
            hcl != null &&
            ecl != null &&
            pid != null
        // cid != null &&
    }

    fun part1(): Int {
        val passports = parseRecords(input = puzzleInput)
        return passports.count { it.isValid() }
    }
}

class Day04Part2(val puzzleInput: List<String>) {
    private fun Passport.isValid(): Boolean {
        fun isByrValid() = byr?.let { byr ->
            byr.toInt().let {
                (1920..2002).contains(it)
            }
        } ?: false

        fun isIyrValuid() = iyr?.let { iyr ->
            iyr.toInt().let {
                (2010..2020).contains(it)
            }
        } ?: false

        fun isEyrValid() = eyr?.let { eyr ->
            eyr.toInt().let {
                (2020..2030).contains(it)
            }
        } ?: false

        fun isHgtValid() = hgt?.let {
            val hgtPattern = "^(\\d{2,3})([A-Za-z]{2})$".toRegex()
            hgtPattern.find(it)?.let { parsedHeight ->
                val increment = parsedHeight.groupValues[1].toInt()
                val unit = parsedHeight.groupValues[2]
                when (unit) {
                    "cm" -> (150..193).contains(increment)
                    "in" -> (59..76).contains(increment)
                    else -> false
                }
            } ?: false
        } ?: false

        fun isHclValid() = hcl?.let {
            val hclPattern = "^#[0-9a-f]{6}$".toRegex()
            hclPattern.containsMatchIn(hcl!!)
        } ?: false

        fun isEclValid() = ecl?.let {
            val validColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            validColors.contains(ecl)
        } ?: false

        fun isPidValid() = pid?.let {
            val pidPattern = "^\\d{9}$".toRegex()
            pidPattern.containsMatchIn(it)
        } ?: false

        fun isCidValid(): Boolean = true

        return isByrValid() &&
            isCidValid() &&
            isEclValid() &&
            isEyrValid() &&
            isHclValid() &&
            isHgtValid() &&
            isIyrValuid() &&
            isPidValid()
    }

    fun part2(): Int {
        val passports = parseRecords(input = puzzleInput)
        return passports.count { it.isValid() }
    }
}

fun main() {
    val puzzleInput = InputReader.lines(4)

    println("Day 04, Part 1: ${Day04Part1(puzzleInput).part1()}")
    println("Day 04, Part 2: ${Day04Part2(puzzleInput).part2()}")
}
