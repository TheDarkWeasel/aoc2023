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

    fun solvePart2() {
        val inputs = this::class.java.getResource("day2_1.txt")?.let { readFile(it.toURI()) }

        val powersOfGames = ArrayList<Int>()

        inputs?.forEach { line ->
            val sets = line.substring(line.indexOf(':') + 1).split(';')

            val maxValuesInGame = getMaxValuesInGame(sets)

            powersOfGames.add(maxValuesInGame.maxNumberBlue * maxValuesInGame.maxNumberRed * maxValuesInGame.maxNumberGreen)
        }

        println(powersOfGames.sum())
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

    private fun getMaxValuesInGame(sets: List<String>): MaxValuesInGame {
        var maxNumberBlue = 0
        var maxNumberRed = 0
        var maxNumberGreen = 0

        for (set in sets) {
            val colorsWithNumbers = set.split(',')

            for (color in colorsWithNumbers) {
                val colorCubeNumber = color.filter { it.isDigit() }.toInt()
                if (color.contains("red")) {
                    if (colorCubeNumber > maxNumberRed) {
                        maxNumberRed = colorCubeNumber
                    }
                }
                if (color.contains("green")) {
                    if (colorCubeNumber > maxNumberGreen) {
                        maxNumberGreen = colorCubeNumber
                    }
                }
                if (color.contains("blue")) {
                    if (colorCubeNumber > maxNumberBlue) {
                        maxNumberBlue = colorCubeNumber
                    }
                }
            }
        }

        return MaxValuesInGame(maxNumberRed, maxNumberGreen, maxNumberBlue)
    }

    private fun readFile(fileName: URI): List<String> = File(fileName).readLines()

    data class MaxValuesInGame(
        val maxNumberRed: Int,
        val maxNumberGreen: Int,
        val maxNumberBlue: Int
    )
}