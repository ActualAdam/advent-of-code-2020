package com.actualadam.aoc.aoc2020.worksheet

import com.actualadam.aoc.aoc2020.InputReader

val lines = InputReader.lines(7)

lines
lines[0]

val bagModifierPattern = "(\\w+ \\w+) bags".toRegex()

val rules: List<List<String>> = lines.map { line ->
    bagModifierPattern.findAll(line).toList()
        .map { it.groupValues[1] }
}


data class TreeNode(
    val value: String,
    val parent: String,
)

tailrec fun buildTree(rules: List<List<String>>, acc: List<TreeNode> = listOf()): List<TreeNode> {
    if (rules.isEmpty()) {
        return acc
    }
    val rule = rules.first()
    val parentValue = rule.first()
    return buildTree(rules.drop(1), acc + rule.drop(1).map { TreeNode(it, parentValue) })
}

val tree = buildTree(rules)

tree.count()


tailrec fun getDepth(value: String, tree: List<TreeNode>, acc: Int = 0): Int {
    val parent = tree.find { it.value == value } ?: return acc
    println(parent)
    return getDepth(parent.value, tree, acc + 1)
}

getDepth("bright gold", tree)

// tree.find { it.value == "bright gold" }

