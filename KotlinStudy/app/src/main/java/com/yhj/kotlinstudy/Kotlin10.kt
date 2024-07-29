package com.yhj.kotlinstudy

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/11
 *    @desc   : 智能类型转换
 */
fun main() {
    val user: User = User("yhj", 18)
//    if(user is User){
//        println(user.name)
//    }

    var value: String? = null
    value = "yhj"
    /**
     * 作用范围 {}
     */
    if (value != null) {
        println(value.length)
    }


    if (tag != null) {
//        println(tag.length)
    }
    /**
     * 类型的安全转换
     * as?
     */
    println((user as? User)?.name)


}

/**
 * 不支持智能转换 可变属性
 */
var tag: String? = null

class Kotliner {

}

class User(name: String, age: Int) {
    
    val name: String = name
}
