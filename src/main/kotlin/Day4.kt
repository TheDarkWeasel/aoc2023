import java.io.File
import java.net.URI

class Day4 {

    fun solvePart1() {
        val inputs = this::class.java.getResource("day4_1.txt")?.let { readFile(it.toURI()) }

        var points = 0

        inputs?.forEach { line ->
            val numberSets = line.substring(line.indexOf(':') + 1).split('|')

            val myNumbers = ArrayList<Int>()
            val winningNumbers = ArrayList<Int>()

            var matches = 0

            numberSets[0].split(' ').forEach {
                if (it.isNotBlank()) {
                    winningNumbers.add(it.trim().toInt())
                }
            }

            numberSets[1].split(' ').forEach {
                if (it.isNotBlank()) {
                    myNumbers.add(it.trim().toInt())
                }
            }

            myNumbers.forEach {
                if (it in winningNumbers) {
                    if (matches == 0) {
                        matches = 1
                    } else {
                        matches *= 2
                    }
                }
            }

            points += matches
        }

        println(points)
    }

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()
}