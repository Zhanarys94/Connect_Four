fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    for (index in strings.indices) {
        if (strings[index] == str) strings[index] = "Banana"
    }
    return strings
}