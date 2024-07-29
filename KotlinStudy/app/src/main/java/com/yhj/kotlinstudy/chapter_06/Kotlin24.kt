package com.yhj.kotlinstudy

import java.io.File

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/18
 *    @desc   : HTML DSL   领域特定语言
 */

interface Node {
    fun render(): String
}

class StringNode(val content: String) : Node {
    override fun render(): String {
        return content
    }

}

class BlockNode(val name: String) : Node {
    val children = ArrayList<Node>()
    val properties = HashMap<String, Any>()
    override fun render(): String {
        //<html props>xxx</html>
        return """<$name ${
            properties.map { "${it.key}='${it.value}'" }.joinToString(" ")
        }>${children.joinToString("") { it.render() }}</$name>"""
    }




    operator fun String.invoke(block: BlockNode.() -> Unit): BlockNode {
        val node = BlockNode(this)
        node.block()
        this@BlockNode.children += node
        return node
    }

    operator fun String.invoke(value: Any) {
        this@BlockNode.properties[this] = value
    }

    operator fun String.unaryPlus() {
        this@BlockNode.children += StringNode(this)
    }

    fun head(block: BlockNode.() -> Unit): BlockNode {
        val head = BlockNode("head")
        head.block()
        this.children += head
        return head
    }

    fun body(block: BlockNode.() -> Unit): BlockNode {
        val body = BlockNode("body")
        body.block()
        this.children += body
        return body
    }

}
fun html(block: BlockNode.() -> Unit): BlockNode {
    val html = BlockNode("html")
    html.block()
    return html
}


fun main() {
    val htmlContent = html {
        head {
            "meta"{ "charset"("utf-8") }
        }
        body {
            "div"{
                "style"(
                    """
                            width:200px;
                            height:200px;
                            line-height=200px;
                        """.trimIndent()
                )
                "span"{
                    "style"(
                        """
                                color:black;
                            """.trimIndent()
                    )
                    +"hello html dsl"
                }
            }
        }
    }.render()

    File("kotlin.html").writeText(htmlContent)

}



