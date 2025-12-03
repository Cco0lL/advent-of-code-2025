package me.ccooll.aoc

import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Cco0lL created 12/2/25 11:37AM
 **/

// https://adventofcode.com/2025/day/2

var invalidIdSum: Long = 0

fun main() {
    val ranges = Files.newBufferedReader(Paths.get("./input/day2.txt")).use {
        it.readLine()
            .split(",")
            .map { line -> with(line.split("-")) {
                this[0].toLong()..this[1].toLong() } }
    }
    //firstPart(ranges)
    secondPart(ranges)

    println(invalidIdSum)
}

//solves first part
private fun firstPart(ranges: Collection<LongRange>) {
    for (range in ranges) {
        for (id in range) {
            val stringifiedId = id.toString()
            if (stringifiedId.length and 1 == 0) {
                val leadDigits = stringifiedId.substring(0, stringifiedId.length / 2)
                val leastDigits = stringifiedId.substring(stringifiedId.length / 2)
                if (leadDigits == leastDigits) {
                    invalidIdSum += id
                }
            }
        }
    }
}

//solves second part
private fun secondPart(ranges: Collection<LongRange>) {
    for (range in ranges) {
        for (id in range) {
            val stringifiedId = id.toString()
            for (i in 1..stringifiedId.length / 2) {
                val numThatCanBeRepeated = stringifiedId.substring(0, i)
                var startLetterPtr = i
                var isValid = false
                while (startLetterPtr + i <= stringifiedId.length) {
                    if (stringifiedId.substring(startLetterPtr, startLetterPtr + i) != numThatCanBeRepeated) {
                        isValid = true
                        break
                    }
                    startLetterPtr += i
                }
                if (!isValid && startLetterPtr == stringifiedId.length) {
                    invalidIdSum += id
                    break
                }
            }
        }
    }
}


