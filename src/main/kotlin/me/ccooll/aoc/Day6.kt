package me.ccooll.aoc

import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Cco0lL created 12/6/25 2:32PM
 **/

private val opFunctions = mapOf(
    "*" to { x: Long, y: Long -> x * y },
    "+" to { x: Long, y: Long -> x + y }
)

private var grandTotal = 0L

fun main() {
    val input = Files.newBufferedReader(Paths.get("./input/day6.txt")).use { it.readLines() }
    //firstSolution(input)
    secondSolution(input)
    println(grandTotal)
}

private fun firstSolution(input: List<String>) {
    val firstVerticalLine = input.first().split(" ").filter { it.isNotEmpty() }

    val numsMatrix = Array(firstVerticalLine.size) {
        val horizontalLine = LongArray(input.size - 1)
        horizontalLine[0] = firstVerticalLine[it].toLong()
        horizontalLine
    }

    val ops = input.last().split(" ")
        .filter { it.isNotEmpty() }

    for (i in 1 until input.size - 1) {
        val verticalLine = input[i].split(" ").filter { it.isNotEmpty() }
        for (j in verticalLine.indices) {
            numsMatrix[j][i] = verticalLine[j].toLong()
        }
    }

    for (i in numsMatrix.indices) {
        val line = numsMatrix[i]
        val opFunction = opFunctions[ops[i]]!!
        var opProduct = line[0]
        for (j in 1 until line.size) {
            opProduct = opFunction(opProduct, line[j])
        }
        grandTotal += opProduct
    }
}

private fun secondSolution(input:List<String>) {
    var pointer = input.first().length - 1
    val ops = input.last().split(" ").filter { it.isNotEmpty() }.reversed().iterator()

    var opNums = arrayListOf<Long>()
    var opFunction = opFunctions[ops.next()]!!

    fun performOp() {
        var opProduct = opNums[0]
        for (j in 1 until opNums.size) {
            opProduct = opFunction(opProduct, opNums[j])
        }
        grandTotal += opProduct
    }

    while (pointer >= 0) {
        val numSb = StringBuilder()
        for (i in 0 until input.size - 1) {
            val digitChar = input[i][pointer]
            if (digitChar != ' ') {
                numSb.append(digitChar)
            }
        }
        if (numSb.isNotEmpty()) {
            opNums += numSb.toString().toLong()
        } else {
            performOp()
            opNums = arrayListOf()
            opFunction = opFunctions[ops.next()]!!
        }
        pointer--
    }

    performOp()
}