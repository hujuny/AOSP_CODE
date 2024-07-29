package com.yhj.kotlinstudy

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/09
 *    @desc   :基本类型
 */
fun main() {

    /**
     * 基本数据类型
     *     Int
     *     Byte
     *     Long
     *     Float
     *     Double
     *     Char
     *     String
     */
    /**
     * val 只读变量
     * var 可读写变量
     * b   变量名
     */
    val b: String = "hello Kotlin"
    var c: Int = 20
    c = 30
    /**
     * 快捷键
     * ctrl+shift+p 查看变量类型
     */
    val d = "hello"
    var e: Int = 20
    e = 30

    println(b)
    println(c)
    println(d)
    println(e == 100)

    /**
     * 容易混淆的Long类型标记，不能使用小写l,必须使用大写L
     */
    val f: Long = 12345678L

    /**
     * 数值类型转换
     * java的int转long不会报错，kotlin不行,规范
     */
    val g = 10
//    val h:Long=g
    val h: Long = g.toLong()

    println(h)

    val i: Float = 1.0f
    val j = 1.0

    /**
     * 无符号类型(Kotlin native 支持无符号类型，java不支持)
     * UByte
     * UShort
     * UInt
     * ULong
     * String
     */

    val k: UInt = 10u
    print("$k")

    val l = String("hello".toCharArray())
    println(l)

    /**
     * 比较内容
     */
    print(d == l)
    /**
     * 比较引用
     */
    print(d === l)

    /**
     * Raw String
     * java中各种转义，直接无语
     */
    val m = """{
    "success": "0",
    "msg": "获取退市详情成功！",
    "data": {
        "pdId": null,
        "versionId": null,
        "bizId": "$d",
        "wfInfoId": $g,
        "delistingDate": "2021-03-26",
        "reason": "111",
        "pdName": "对外销售里程碑测试2.00-刘杰元测试",
        "wfDrivenItem": null
    }
}"""
    println(m)
}