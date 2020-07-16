import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DemoTest {
    private val summer = Summer()

    @Test
    fun firstClassFunction() {
        val f: (Int, Int) -> Int = ::sum
        assertEquals(6, f(2, 4))
    }

    @Test
    fun classImplementingFunctionType() {
        assertEquals(8, summer.invoke(5, 3))
    }

    @Test
    fun lambda() {
        assertEquals(3, lambdaSum(1, 2))
    }

    @Test
    fun functionAsParam() {
        val f: (Int, Int) -> Int = ::sum
        assertEquals(9, summer.callAndPrint(f, 4, 5))
    }

    @Test
    fun functionComposition() {
        val sumFunc: (Int, Int) -> Int = ::sum
        val diffFunc: (Int, Int) -> Int = ::difference
        assertEquals(16, summer.composeAndMultiply(sumFunc, diffFunc, 5, 3))
    }

    @Test
    fun filterMap() {
        val mappings = mapOf(
            1 to "horse",
            2 to "cat",
            3 to "tiger",
            4 to "dog",
            5 to "elephant",
            6 to "giraffe",
            7 to "fish",
            8 to "penguin",
            9 to "bird"
        )
        val evenAnimals = mappings
            .filter { it.key % 2 == 0 }
            .map { it.value }
        println(evenAnimals)
    }

    @Test
    fun reduceTest() {
        val sum = listOf(1, 2, 3).reduce { sum, element -> sum + element }
        assertEquals(6, sum)
    }

    @Test
    fun foldTest() {
        val sum = listOf(1, 2, 3).fold(0, { acc, curr -> acc + curr })
        assertEquals(6, sum)

        val longSum = listOf(1, 2, 3).fold(0L, { acc, curr -> acc + curr.toLong() })
        assertEquals(6L, longSum)

        val complicatedMap = mapOf(
            1 to "uno",
            2 to "dos",
            3 to "tres"
        )
        val complicatedDoubleMap = mapOf(
            "uno" to "lions",
            "dos" to "tigers",
            "tres" to "bears"
        )
        val complicatedTripleMap = mapOf(
            "lions" to "oh",
            "tigers" to "my",
            "bears" to "!!!"
        )
        val complicatedReturn = listOf(1, 2, 3)
            .fold(mutableMapOf<Int, Map<String, Int>>(),
                { acc, curr ->
                    acc[curr] = mapOf(complicatedMap[curr]!! to complicatedTripleMap[complicatedDoubleMap[complicatedMap[curr]]]!!.hashCode())
                    acc
                })
        assertEquals(mapOf(
            1 to mapOf("uno" to "oh".hashCode()),
            2 to mapOf("dos" to "my".hashCode()),
            3 to mapOf("tres" to "!!!".hashCode())
        ), complicatedReturn)
    }

    @Test
    fun showReceivers() {
        val t = tree("root") {
            node("math") {
                node("algebra")
                node("trigonometry")
            }
            node("science") {
                node("physics")
            }
        }
    }

    @Test
    fun resultAsReturnType() {
        listOf(2347, 378249, 102, 901, 124890).map {
            val ret = retRes(it)
            if (ret.isSuccess) {
                println("$it is a success")
            } else if (ret.isFailure) {
                println("$it is a failure")
            }
        }
    }

    private fun retRes(x: Int): Result<Int> {
        return if (x % 2 == 0) {
            Result.success(x)
        } else {
            Result.failure(Exception("$x isn't gonna work."))
        }
    }


    private fun <T> Set<T>.powerSet(originalSet: Set<T> = this): Set<Set<T>> {
        if (originalSet.isEmpty()) {
            return setOf(emptySet())
        }
        val sets = mutableSetOf<Set<T>>()
        val head = originalSet.first()
        val rest = originalSet.toList().subList(1, originalSet.size).toSet()
        powerSet(rest).forEach {
            val newSet: MutableSet<T> = mutableSetOf(head)
            newSet.addAll(it)
            sets.add(newSet)
            sets.add(it)
        }
        return sets
    }

    // []
    // [1]
    // [2]
    // [3]
    // [1, 2]
    // [1, 3]
    // [2, 3]
    // [1, 2, 3]

    // h = 1
    // rest = [2, 3]
    // loop iteration 1:
    //   newSet = [1, 2, 3]
    //   sets = [[1, 2, 3], [2, 3]]
    // loop iter 2:
    //   newSet = [1, 3]
    //   sets = [[1, 2, 3], [2, 3], [1, 3], [3]]
    // loop iter 3:
    //   newSet = [1, 2]
    //   sets = [[1, 2, 3], [2, 3], [1, 3], [3], [1, 2], [2]]
    // loop iter 4:
    //   newSet = [1]
    //   sets = [[1, 2, 3], [2, 3], [1, 3], [3], [1, 2], [2], [1], []]
    // return [[1, 2, 3], [2, 3], [1, 3], [3], [1, 2], [2], [1], []]

    // h = 2
    // rest = [3]
    // loop iteration 1:
    //   newSet = [2, 3]
    //   sets = [[2, 3], [3]]
    // loop iteration 2:
    //   newSet = [2]
    //   sets = [[2, 3], [3], [2], []]
    // return [[2, 3], [3], [2], []]

    // h = 3
    // rest = []
    // loop iteration 1:
    //   newSet = [3]
    //   sets = [[3], []]
    // return [[3], []]

    // return [[]]

    private fun rescue(population: Set<Int>, limit: Int, numBoats: Int): Int {
        val viableSets: List<Set<Int>> = population.powerSet().filter { it.sum() <= limit }
        return if (viableSets.none { it.isNotEmpty() }) {
            numBoats + population.size
        } else {
            val maxLen = viableSets.maxBy { it.size }?.size
            val setToRemove = viableSets.filter { it.size == maxLen }.maxBy { it.sum() }!!
            println("Boat containing $setToRemove setting sail!")
            rescue(population.filter { !setToRemove.contains(it) }.toSet(), limit, numBoats + 1)
        }
    }

    @Test
    fun codeville() {
        val weights = setOf(2, 100, 180, 20, 80)
        val limit = 200
        assertEquals(2, rescue(weights, limit, 0))
    }

    private fun minLeftHelper(quxes: CharArray, red: Int, green: Int, blue: Int): Triple<Int, Int, Int> {
        if (quxes.isEmpty()) {
            return Triple(red, green, blue)
        }
        return when (quxes.first()) {
            'R' -> minLeftHelper(quxes.toList().subList(1, quxes.size).toCharArray(), red + 1, green, blue)
            'G' -> minLeftHelper(quxes.toList().subList(1, quxes.size).toCharArray(), red, green + 1, blue)
            'B' -> minLeftHelper(quxes.toList().subList(1, quxes.size).toCharArray(), red, green, blue + 1)
            else -> throw Exception("Input can only be R, G or B.")
        }
    }

    private fun minimumQuxes(quxes: CharArray): Int {
        val rgb = minLeftHelper(quxes, 0, 0, 0)
        val red = rgb.first
        val green = rgb.second
        val blue = rgb.third
        return if (red == quxes.size || green == quxes.size || blue == quxes.size) {
            quxes.size
        } else if (red % 2 == green % 2 && red % 2 == blue % 2) {
            2
        } else {
            1
        }
    }

    @Test
    fun quxes() {
        val input = charArrayOf("R".single(), "G".single(), "B".single(), "G".single(), "B".single())
        assertEquals(1, minimumQuxes(input))
    }

    @Test
    fun iojfew() {
        println("522081530," +
                "363277477," +
                "412830244," +
                "955852222," +
                "677269112," +
                "588530630," +
                "280048298," +
                "605299259," +
                "444477091," +
                "731867981," +
                "354477790,"+
                "375022199," +
                "354477790," +
                "491583776," +
                "448923369," +
                "594073806," +
                "505996267," +
                "516302448")
    }
}