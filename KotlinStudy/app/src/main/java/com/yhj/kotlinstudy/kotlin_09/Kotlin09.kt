package com.yhj.kotlinstudy

import com.yhj.kotlinstudy.kotlin_09.Po
import java.io.File

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/11
 *    @desc   : 空类型安全
 */
fun main() {
    //不会出现空指针
    var nonNull: String = "hello"
//    nonNull=null

    //加?可以为null
    var nullable: String? = "yhj"
//    nullable = null
    //可能出现空指针，编译错误
//    nullable.length
    /**
     * 强转为不可空类型
     */
    val length = nullable!!.length

    /**
     * 安全访问
     */
    val length1 = nullable?.length
    //但是 length1有可能为null
    val i: Int = nullable?.length ?: 0 //elvis运算符

    /**
     * 空类型的继承关系
     */
    var x: String = "hello"
    var y: String? = "world"
    //里氏替换原则，比较值可发现  String是String?的子类
//    x=y
    y = x

    var a: Int = 2
    var b: Number = 10.0
//    a=b
    //Int是Number的子类
    b = a

    /**
     * 平台类型
     * 客观存在，不能主观定义的类型
     * 平台类型编译器无法判断为null 手动处理
     */

    val po = Po()
    //String!类型
    val title = po.title
//    println(title.length)

    val file = File("abc")
    val listFiles = file.listFiles()
    //目录不存在，空指针
    listFiles.size


}
