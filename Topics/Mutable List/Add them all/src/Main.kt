fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    val all = mutableListOf<Int> ()
    all.addAll(first)
    all.addAll(second)
    return all
}