import java.io.File
import java.lang.RuntimeException
import java.net.URI

class Day3 {
    fun solvePart1() {
        val inputs = this::class.java.getResource("day3_1.txt")?.let { readFile(it.toURI()) }

        val matrix = arrayListOf<List<Char>>()

        inputs?.forEach { line ->
            matrix.add(line.toCharArray().toList())
        }

        val yMax = matrix.size - 1
        val xMax = matrix[0].size - 1

        var lastNumber = ""
        var isLastNumberValid = false

        var sum = 0

        for (y in 0..yMax) {
            for (x in 0..xMax) {
                val currentChar = matrix[y][x]
                if (currentChar.isDigit()) {
                    lastNumber += currentChar
                    //left
                    if (x > 0 && isSymbol(matrix[y][x - 1])) {
                        isLastNumberValid = true
                    }
                    //right
                    if (x < xMax && isSymbol(matrix[y][x + 1])) {
                        isLastNumberValid = true
                    }
                    //top
                    if (y > 0 && isSymbol(matrix[y - 1][x])) {
                        isLastNumberValid = true
                    }
                    //bottom
                    if (y < yMax && isSymbol(matrix[y + 1][x])) {
                        isLastNumberValid = true
                    }
                    //left-top
                    if (x > 0 && y > 0 && isSymbol(matrix[y - 1][x - 1])) {
                        isLastNumberValid = true
                    }
                    //left-bottom
                    if (x > 0 && y < yMax && isSymbol(matrix[y + 1][x - 1])) {
                        isLastNumberValid = true
                    }
                    //right-top
                    if (x < xMax && y > 0 && isSymbol(matrix[y - 1][x + 1])) {
                        isLastNumberValid = true
                    }
                    //right-bottom
                    if (x < xMax && y < yMax && isSymbol(matrix[y + 1][x + 1])) {
                        isLastNumberValid = true
                    }
                } else {
                    //current number done
                    if (isLastNumberValid && lastNumber.isNotBlank()) {
                        sum += lastNumber.toInt()
                    }
                    lastNumber = ""
                    isLastNumberValid = false
                }
            }
        }

        println(sum)
    }

    private fun isSymbol(char: Char): Boolean {
        return !char.isDigit() && char != '.'
    }

    private fun isGear(char: Char): Boolean {
        return !char.isDigit() && char == '*'
    }

    fun solvePart2() {
        val inputs = this::class.java.getResource("day3_1.txt")?.let { readFile(it.toURI()) }

        val matrix = arrayListOf<List<Char>>()

        inputs?.forEach { line ->
            matrix.add(line.toCharArray().toList())
        }

        val yMax = matrix.size - 1
        val xMax = matrix[0].size - 1

        var lastNumber = ""
        var lastNumberGears = ArrayList<GearPosition>()

        val numbersWithGears = ArrayList<NumberWithGear>()

        var ratio = 0

        for (y in 0..yMax) {
            for (x in 0..xMax) {
                val currentChar = matrix[y][x]
                if (currentChar.isDigit()) {
                    lastNumber += currentChar
                    //left
                    if (x > 0 && isGear(matrix[y][x - 1])) {
                        val newPos = GearPosition(y, x - 1)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //right
                    if (x < xMax && isGear(matrix[y][x + 1])) {
                        val newPos = GearPosition(y, x + 1)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //top
                    if (y > 0 && isGear(matrix[y - 1][x])) {
                        val newPos = GearPosition(y - 1, x)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //bottom
                    if (y < yMax && isGear(matrix[y + 1][x])) {
                        val newPos = GearPosition(y + 1, x)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //left-top
                    if (x > 0 && y > 0 && isGear(matrix[y - 1][x - 1])) {
                        val newPos = GearPosition(y - 1, x - 1)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //left-bottom
                    if (x > 0 && y < yMax && isGear(matrix[y + 1][x - 1])) {
                        val newPos = GearPosition(y + 1, x - 1)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //right-top
                    if (x < xMax && y > 0 && isGear(matrix[y - 1][x + 1])) {
                        val newPos = GearPosition(y - 1, x + 1)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                    //right-bottom
                    if (x < xMax && y < yMax && isGear(matrix[y + 1][x + 1])) {
                        val newPos = GearPosition(y + 1, x + 1)
                        if (!lastNumberGears.contains(newPos)) {
                            lastNumberGears.add(newPos)
                        }
                    }
                } else {
                    //current number done
                    if (lastNumberGears.isNotEmpty() && lastNumber.isNotBlank()) {
                        numbersWithGears.add(NumberWithGear(lastNumber.toInt(), lastNumberGears))
                    }
                    lastNumber = ""
                    lastNumberGears = ArrayList()
                }
            }
        }

        for (first in numbersWithGears) {
            for (second in numbersWithGears) {
                if (first == second) {
                    continue
                }
                if (first.containsSameGear(second)) {
                    ratio += (first.number * second.number)
                    removeSameGear(first, second)
                }
            }
        }

        println(ratio)
    }

    private fun removeSameGear(first: NumberWithGear, second: NumberWithGear) {
        val gearToRemove = first.getSameGear(second)
        first.removeGear(gearToRemove)
        second.removeGear(gearToRemove)
    }

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()

    data class NumberWithGear(val number: Int, val gearPositions: ArrayList<GearPosition>) {
        fun containsSameGear(other: NumberWithGear): Boolean {
            for (gear in gearPositions) {
                if (other.gearPositions.contains(gear)) {
                    return true
                }
            }

            return false
        }

        fun getSameGear(other: NumberWithGear): GearPosition {
            for (gear in gearPositions) {
                for (otherGear in other.gearPositions) {
                    if (gear == otherGear) {
                        return gear
                    }
                }
            }

            throw RuntimeException("No gear")
        }

        fun removeGear(gearPosition: GearPosition) {
            gearPositions.remove(gearPosition)
        }
    }

    data class GearPosition(val gearY: Int, val gearX: Int) {
        override fun equals(other: Any?): Boolean {
            if (other !is GearPosition) {
                return false
            }
            return other.gearY == this.gearY && other.gearX == this.gearX
        }

        override fun hashCode(): Int {
            var result = gearY
            result = 31 * result + gearX
            return result
        }
    }
}