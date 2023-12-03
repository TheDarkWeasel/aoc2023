import java.io.File
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

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()
}