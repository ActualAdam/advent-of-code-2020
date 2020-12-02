package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader
import com.actualadam.aoc.aoc2020.days.day02.Day02

val lines = InputReader.lines(2)

val policyPasswords = lines.map { Day02.Part1.PolicyPassword.parse(it) }
policyPasswords

val abacab = "abacab"
abacab.count { it == 'a'}

val policyPassword = policyPasswords[1]
policyPassword
policyPassword.isValid()
