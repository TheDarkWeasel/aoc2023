import java.io.File
import java.net.URI

class Day2 {
    private val maxRedCubes = 12
    private val maxGreenCubes = 13
    private val maxBlueCubes = 14

    fun solvePart1() {
        val inputs = this::class.java.getResource("day2_1.txt")?.let { readFile(it.toURI()) }

        val possibleGameIds = ArrayList<Int>()

        inputs?.forEach { line ->
            val id = line.substring(line.indexOf('e') + 1, line.indexOf(':')).trim().toInt()
            val sets = line.substring(line.indexOf(':') + 1).split(';')

            for (set in sets) {
                if (!isSetPossible(set)) {
                    return@forEach
                }
            }

            possibleGameIds.add(id)
        }

        println(possibleGameIds.sum())
    }

    private fun isSetPossible(set: String): Boolean {
        val colorsWithNumbers = set.split(',')

        for (color in colorsWithNumbers) {
            val colorCubeNumber = color.filter { it.isDigit() }.toInt()
            if (color.contains("red")) {
                if (colorCubeNumber > maxRedCubes) {
                    return false
                }
            }
            if (color.contains("green")) {
                if (colorCubeNumber > maxGreenCubes) {
                    return false
                }
            }
            if (color.contains("blue")) {
                if (colorCubeNumber > maxBlueCubes) {
                    return false
                }
            }
        }

        return true
    }

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()
}