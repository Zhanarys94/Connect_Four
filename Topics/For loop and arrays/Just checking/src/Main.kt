fun main() {
    val array = IntArray(readln().toInt()) {readln().toInt()}
    val (p, m) = readln().split(" ").map { it.toInt() }
    println(if (array.contains(p) && array.contains(m)) "YES" else "NO")
}