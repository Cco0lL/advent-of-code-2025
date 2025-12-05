package me.ccooll.aoc

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * @author Cco0lL created 12/5/25 3:41PM
 **/

var availableFreshIngredients = 0

fun main() {
    val input = Files.newBufferedReader(Paths.get("./input/day5.txt")).use { it.readLines() }
    val blankLineIndex = input.indexOfFirst { it.isBlank() }
    val freshIngIdRanges = input.subList(0, blankLineIndex).map {
        with(it.split("-")) { this[0].toLong()..this[1].toLong() }
    }
    val availableIngIds = input.subList(blankLineIndex + 1, input.size).map { it.toLong() }
    firstSolution(freshIngIdRanges, availableIngIds)
    println(availableFreshIngredients)
    secondSolution(freshIngIdRanges)
}

fun firstSolution(freshIngIdRanges: List<LongRange>, availableIngIds: List<Long>) {
    for (availableId in availableIngIds) {
        if (freshIngIdRanges.any { it.contains(availableId) }) availableFreshIngredients++
    }
}

fun secondSolution(freshIngIdRanges: List<LongRange>) {

    class ExtendableRange(val initialRange: LongRange) {

        private var extendedFirst = initialRange.first
        private var extendedLast = initialRange.last

        val wasExtended get() = extendedFirst != initialRange.first || extendedLast != initialRange.last

        fun toLongRange() = extendedFirst..extendedLast

        fun checkIntersectionAndExtendIfDoes(range: LongRange): Boolean {
            if (range.first >= extendedFirst && range.last <= extendedLast) {
                return true
            } else if (range.last >= extendedFirst && range.first <= extendedFirst) {
                extendedFirst = range.first
                return true
            } else if (range.first <= extendedLast && range.last >= extendedLast) {
                extendedLast = range.last
                return true
            }
            return false
        }

        fun isAPartOf(range: LongRange): Boolean {
            return (range.first <= extendedFirst && range.last >= extendedLast)
        }
    }

    val unintersectedRanges = arrayListOf<LongRange>()
    val uncheckedOnIntersectionRanges = LinkedList(freshIngIdRanges)

    while (uncheckedOnIntersectionRanges.isNotEmpty()) {
        val extendableRange = ExtendableRange(uncheckedOnIntersectionRanges.removeFirst())

        val itr = uncheckedOnIntersectionRanges.iterator()
        var isAPartOfAnotherRange = false
        while (itr.hasNext()) {
            val next = itr.next()
            if (extendableRange.isAPartOf(next)) {
                isAPartOfAnotherRange = true
                break
            }
            if (extendableRange.checkIntersectionAndExtendIfDoes(next)) {
                itr.remove()
            }
        }

        if (isAPartOfAnotherRange) {
            continue
        }

        if (!extendableRange.wasExtended) {
            unintersectedRanges += extendableRange.toLongRange()
        } else {
            uncheckedOnIntersectionRanges.addFirst(extendableRange.toLongRange())
        }
    }

    println(unintersectedRanges.sumOf { (it.last + 1) - it.first }) //it.last + 1 because the last included too
}
