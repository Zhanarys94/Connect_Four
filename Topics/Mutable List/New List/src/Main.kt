fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val finished = mutableListOf<Int>()
    finished.addAll(numbers)
    finished.add(number)
    return finished
}