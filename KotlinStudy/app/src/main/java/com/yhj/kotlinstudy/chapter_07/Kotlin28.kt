package com.yhj.kotlinstudy.chapter_07

import android.widget.TextView

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   : 类属性的延迟初始化
 */
class Kotlin28 {
    /**
     * 类属性必须在构造时进行初始化
     * 某些成员只有在类构造之后才会被初始化
     *
     * 1.初始化为null
     * 2.lateinit
     * 3.lazy
     */

//    private val nameView: TextView? =null

    //必须是var
//    private lateinit var nameView: TextView
//
//    if (::nameView.isInitialized) {
//        nameView = findViewById(R.id.tv)
//    }
    /**
     * lateinit 会让编译器忽略变量的初始化，不支持int等基本类型
     * 开发者能够在完全确定变量值的生命周期下使用lateinit
     * 不要在复杂的逻辑中使用lateinit，它会让你的代码更加脆弱
     * Kotlin 1.2添加了判断lateinit属性是否初始化的api最好不要用
     */

    //属性代理
    //只有在nameView首次被访问时执行
//    private val nameView by lazy {
//        findViewById<TextView>(R.id.tv)
//    }



}

fun main() {

     lateinit var nameView: TextView
}