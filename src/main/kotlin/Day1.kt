import java.io.File
import java.net.URI

class Day1 {

    fun solvePart1() {
        val inputs = this::class.java.getResource("day1_1.txt")?.let { readFile(it.toURI()) }

        printSumOfFirstAndLastDigitsInALine(inputs)
    }

    private fun printSumOfFirstAndLastDigitsInALine(inputs: List<String>?) {
        var sum = 0

        inputs?.let {
            inputs.forEach { line ->
                line.filter { it.isDigit() }.let { numbers ->
                    sum += ("" + numbers.first() + numbers.last()).toInt()
                }
            }
        }

        println(sum)
    }

    fun solvePart2() {
        val inputs = this::class.java.getResource("day1_1.txt")?.let { readFile(it.toURI()) }

        printSumOfFirstAndLastDigitsInALine(inputs?.map { replaceWordWithNumber(it) })
    }

    //hehe ;)
    private fun replaceWordWithNumber(input: String) : String {
        return input
            .replace("one", "o1e")
            .replace("two", "t2o")
            .replace("three", "t3e")
            .replace("four", "f4r")
            .replace("five", "f5e")
            .replace("six", "s6x")
            .replace("seven", "s7n")
            .replace("eight", "e8t")
            .replace("nine", "n9e")
    }

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()
}