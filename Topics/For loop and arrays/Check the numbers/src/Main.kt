fun main() {
    val arrayToString = Array(readLine()!!.toInt()) { readLine()!! }.joinToString("")
    val lastLine = readLine()!!.split(" ").joinToString("")
    val answer = if (arrayToString.contains(lastLine) || arrayToString.contains(lastLine.reversed())) "NO" else "YES"
    print(answer)
}