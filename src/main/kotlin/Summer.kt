fun sum(x: Int, y: Int) = x + y
fun difference(x: Int, y: Int) = x - y

val lambdaSum = { x: Int, y: Int -> x + y }

class Summer : (Int, Int) -> Int {
    val f: (Int, Int) -> Int = ::sum
    override fun invoke(x: Int, y: Int): Int = f(x, y)

    fun callAndPrint(function: (Int, Int) -> Int, x: Int, y: Int): Int {
        val ret = function(x, y)
        println("Your function call will return: $ret")
        return ret
    }

    fun composeAndMultiply(firstFunc: (Int, Int) -> Int, secondFunc: (Int, Int) -> Int, x: Int, y: Int): Int {
        return firstFunc(x, y) * secondFunc(x, y)
    }
}
