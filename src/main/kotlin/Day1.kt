import java.io.File
import java.net.URI

class Day1 {

    fun solvePart1() {
        val inputs = this::class.java.getResource("day1_1.txt")?.let { readFile(it.toURI()) }

        var sum = 0

        inputs?.let {
            inputs.forEach { line ->
                line.filter { it.isDigit() }.let {numbers ->
                    sum += ("" + numbers.first() + numbers.last()).toInt()
                }
            }
        }

        println(sum)
    }

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()
}