package me.ccooll.aoc

import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Cco0lL created 12/3/25 10:25AM
 **/
var maxJoltageRating: Long = 0

fun main() {
    val batteryBanks = Files.newBufferedReader(Paths.get("./input/day3.txt")).use { it.readLines() }
    //firstSolution(batteryBanks)
    secondSolution(batteryBanks)
    println(maxJoltageRating)
}

fun firstSolution(batteryBanks: Collection<String>) {
    for (batteryBank in batteryBanks) {
        var maxJoltageRatingFromBank = 0
        for (i in batteryBank.indices) {
            val digitChar = batteryBank[i]
            for (j in i + 1 until batteryBank.length) {
                val joltageRatingFromTwoBatteries = String(charArrayOf(digitChar, batteryBank[j])).toInt()
                if (joltageRatingFromTwoBatteries > maxJoltageRatingFromBank) {
                    maxJoltageRatingFromBank = joltageRatingFromTwoBatteries
                }
            }
        }
        println("max: $maxJoltageRatingFromBank")
        maxJoltageRating += maxJoltageRatingFromBank
    }
}

fun secondSolution(batteryBanks: Collection<String>) {
    for (batteryBank in batteryBanks) {
        var maxJoltageRatingFromBank = 0L
        for (i in batteryBank.indices) {

            val maxNumSB = StringBuilder(32).append(batteryBank[i])
            var pointer = i + 1
            var lastIdx = i + (11 - maxNumSB.length)

            while (maxNumSB.length < 12) {
                if (lastIdx >= batteryBank.length) {
                    break
                }

                val j = batteryBank.length - (11 - maxNumSB.length)
                if (j <= 1) {
                    maxNumSB.append(pointer)
                    pointer++
                    continue
                }

                var maxDigitIdx = pointer
                for (k in maxDigitIdx + 1 until j) {
                    if (batteryBank[maxDigitIdx].digitToInt() < batteryBank[k].digitToInt()) {
                        maxDigitIdx = k
                    }
                }

                maxNumSB.append(batteryBank[maxDigitIdx])
                pointer = maxDigitIdx + 1

                lastIdx = pointer + 11 - maxNumSB.length
                if (lastIdx < pointer) {
                    break
                }
            }

            maxNumSB.toString().toLong().also {
                if (it > maxJoltageRatingFromBank) {
                    maxJoltageRatingFromBank = it
                }
            }
        }
        println("max: $maxJoltageRatingFromBank")
        maxJoltageRating += maxJoltageRatingFromBank
    }
}