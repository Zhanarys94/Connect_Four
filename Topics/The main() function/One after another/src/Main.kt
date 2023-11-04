fun main(args: Array<String>) {
    if (args.size < 3) main(arrayOf("first", "second", "third")) else {
        for (i in args.indices) {
            println(args[i])
        }
    }
}