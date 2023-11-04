fun main() {
    val size = readln().toInt()
    val array = IntArray(size)
    for (i in 1..size) {
        if (i == size) {
            array[0] = readln().toInt()
            break
        }
        array[i] = readln().toInt()
    }
    println(array.joinToString(" "))
}