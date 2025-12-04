package me.ccooll.aoc

import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Cco0lL created 12/4/25 10:09AM
 **/

var paperRollsThatCanBeAccessed = 0

data class PaperRollGraphNode(
    val i: Int,
    val j: Int,
) {
    val linkedNodes: MutableList<PaperRollGraphNode> = mutableListOf()
}

lateinit var paperRollGraphMatrix: Array<Array<PaperRollGraphNode?>>

fun main() {
    val paperRollsScheme = Files.newBufferedReader(Paths.get("./input/day4.txt")).use { it.readLines() }

    val lines = paperRollsScheme.size
    val lineSize = paperRollsScheme.first().length

    paperRollGraphMatrix = Array(lines) { arrayOfNulls(lineSize) }
    //firstSolution(paperRollsScheme)
    secondSolution(paperRollsScheme)
    println(paperRollsThatCanBeAccessed)
}

fun firstSolution(paperRollsScheme: List<String>) {
    buildGraph(paperRollsScheme)
    paperRollsThatCanBeAccessed = paperRollGraphMatrix.sumOf { line ->
        line.sumOf { node ->
            node?.run {
                linkedNodes.size.let { size -> if (size < 4) 1 else 0 }
            } ?: 0
        }
    }
}

fun secondSolution(paperRollsScheme: List<String>) {
    buildGraph(paperRollsScheme)
    while (true) { if (clearAccessiblePaperRolls().also {
        paperRollsThatCanBeAccessed += it
            println(it)
    } == 0) break }
}

private fun clearAccessiblePaperRolls(): Int {
    val forClear = arrayListOf<PaperRollGraphNode>()
    for (i in paperRollGraphMatrix.indices) {
        val line = paperRollGraphMatrix[i]
        for (j in line.indices) {
            val node = line[j]
            if (node !== null && node.linkedNodes.size < 4) {
                forClear += node
            }
        }
    }
    forClear.forEach {
        paperRollGraphMatrix[it.i][it.j] = null
        it.linkedNodes.forEach { linked -> linked.linkedNodes -= it }
    }
    return forClear.size
}


private fun buildGraph(paperRollsScheme: List<String>) {
    for (lineIdx in paperRollsScheme.indices) {
        val line = paperRollsScheme[lineIdx]
        for (columnIdx in paperRollsScheme.indices) {
            var char = line[columnIdx]
            if (char == '@') {
                val node = PaperRollGraphNode(lineIdx, columnIdx)
                paperRollGraphMatrix[lineIdx][columnIdx] = node

                val upperLineIdx = lineIdx - 1
                val columnLeftIdx = columnIdx - 1
                val columnRightIdx = columnIdx + 1

                if (columnLeftIdx >= 0) {
                    val leftNode = paperRollGraphMatrix[lineIdx][columnLeftIdx]
                    if (leftNode !== null) {
                        leftNode.linkedNodes += node
                        node.linkedNodes += leftNode
                    }
                }

                if (upperLineIdx >= 0) {
                    val upperNode = paperRollGraphMatrix[upperLineIdx][columnIdx]
                    if (upperNode !== null) {
                        upperNode.linkedNodes += node
                        node.linkedNodes += upperNode
                    }
                    if (columnLeftIdx >= 0) {
                        val upperLeftNode = paperRollGraphMatrix[upperLineIdx][columnLeftIdx]
                        if (upperLeftNode !== null) {
                            upperLeftNode.linkedNodes += node
                            node.linkedNodes += upperLeftNode
                        }
                    }
                    if (columnRightIdx < line.length) {
                        val upperRightNode = paperRollGraphMatrix[upperLineIdx][columnRightIdx]
                        if (upperRightNode !== null) {
                            upperRightNode.linkedNodes += node
                            node.linkedNodes += upperRightNode
                        }
                    }
                }
            }
        }
    }
}