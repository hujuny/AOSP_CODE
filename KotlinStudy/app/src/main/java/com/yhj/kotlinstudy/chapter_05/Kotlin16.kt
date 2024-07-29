package com.yhj.kotlinstudy

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/14
 *    @desc   : 为People实现equals和hashCode
 */
class Kotlin16 {


}

class People(val age: Int, val name: String) {

    override fun equals(other: Any?): Boolean {

        val other = (other as? People) ?: return false
        return other.age == age && other.name == name
    }

    override fun hashCode(): Int {
        return 1 + 7 * age + 13 * name.hashCode()
    }
}

fun main() {
    val peoples = HashSet<People>()
    (0..5).forEach { peoples += People(18, "yhj") }
    //6和1(运算符重载)
    println(peoples.size)

    val people1=People(18,"hujuny")
    peoples+=people1
    println(peoples.size)
//    people1.age++
    //移除不掉
    peoples-=people1

    //通过这种方式来处理，
    val people2=People(people1.age+1,people1.name)
    println(peoples.size)
}
