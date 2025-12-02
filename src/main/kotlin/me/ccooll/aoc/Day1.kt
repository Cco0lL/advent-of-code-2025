package me.ccooll.aoc

import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Cco0lL created 12/2/25 1:46AM
 **/
//https://adventofcode.com/2025/day/1

var password = 0
var dialPointer = 50

fun main() {
    val instructions = Files.newBufferedReader(Paths.get("./input/day1.txt")).use { it.readLines() }
    //firstPart(instructions)
    secondPart(instructions)
    println("password: $password")
}

//solution of the first part
fun firstPart(instructions: Collection<String>) {
    for (instruction in instructions) {
        println(instruction)
        when {
            instruction.startsWith("L") -> { // rotate left
                val distance = instruction.substring(1).toInt()
                dialPointer = (dialPointer - distance) % 100
                if (dialPointer < 0) {
                    dialPointer += 100
                }
            }
            instruction.startsWith("R") -> { // rotate right
                val distance = instruction.substring(1).toInt()
                dialPointer = (dialPointer + distance) % 100
            }
        }
        if (dialPointer == 0) {
            password++
        }
    }
}

//solution of the second part 
private fun secondPart(instructions: Collection<String>) {
    for (instruction in instructions) {
        println(instruction)
        when {
            instruction.startsWith("L") -> rotateLeft(instruction.substring(1).toInt())
            instruction.startsWith("R") -> rotateRight(instruction.substring(1).toInt())
        }
    }
}

private fun rotateLeft(distance: Int) {
    var distance = distance
    while (distance != 0) {
        val step = if (distance >= 99) 99 else distance
        val rotatedFromZero = dialPointer == 0
        dialPointer -= step
        if (dialPointer == 0) {
            password++
        } else if (dialPointer < 0) {
            dialPointer += 100
            if (!rotatedFromZero) {
                password++
            }
        }
        distance -= step
    }
}

private fun rotateRight(distance: Int) {
    var distance = distance
    while (distance != 0) {
        val step = if (distance >= 99) 99 else distance
        dialPointer += step
        if (dialPointer > 99) {
            dialPointer -= 100
            password++
        }
        distance -= step
    }
}
