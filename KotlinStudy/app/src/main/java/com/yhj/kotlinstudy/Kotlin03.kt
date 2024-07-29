package com.yhj.kotlinstudy

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/09
 *    @desc   : 区间
 */
fun main() {

    val intRange = 1..10
    val charRange = 'a'..'z'
    val longRange = 1L..100L

    /**
     * 开区间
     */
    val intRangeExclusive = 1 until 10 //[1,10)
    val charRangeExclusive = 'a' until 'z'
    val longRangeExclusive = 1L until 100L

    /**
     * 倒序区间 由大到小
     */
    val intRangeReverse = 10 downTo 1 //[10,1]
    val charRangeReverse = 'z' downTo 'a'
    val longRangeReverse = 100L downTo 1L

    /**
     * 区间的步长
     */
    val intRangeWithStep = 1..10 step 2 //1,3,5,7,9
    val charRangeWithStep = 'a'..'z' step 2
    val longRangeWithStep = 1L..100L step 2

    /**
     * 无符号
     */
    val uIntRange = 1u..10u
    val uLongRange = 1uL..10uL

    /**
     * 区间的迭代
     */

    val array = intArrayOf(1, 3, 5, 7)
    for (i in 0 until array.size) {
        println(array[i])
    }
    //两种写法等同
    for (i in array.indices) {
        println(array[i])
    }


}