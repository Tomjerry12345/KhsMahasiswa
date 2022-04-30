package com.khsmahasiswa.ui

data class Data(
    val data: String,
    val child: List<String>?
)

fun bfs(node: List<Data>, destination: String) {
    val output = ArrayList<String>()
    val stack = ArrayList<String>()

    output.add(node[0].data)
    stack.add(node[0].data)
    node[0].child?.forEach {
        stack.add(it)
    }

    while (stack.isNotEmpty()) {
        stack.removeAt(0)

        output.add(stack[0])

        if (stack[0] != destination) {
            node.forEach {
                if  (it.data == stack[0]) {
                    it.child?.forEach {  child ->
                        stack.add(child)
                    }
                }
            }
        } else {
            stack.clear()
        }
        println("test")
    }

//    print("stack = $stack")
    print("output = $output")
}

fun main() {
    val node = listOf(
        Data(
            "A",
            listOf(
                "B",
                "C"
            )
        ),
        Data(
            "B",
            listOf(
                "H",
                "G"
            )
        ),
        Data(
            "C",
            listOf(
                "D", "G", "F"
            )
        ),
        Data(
            "G",
            listOf(
                "E"
            )
        ),
    )

//    print("node : $node")

    bfs(node, "E")
}