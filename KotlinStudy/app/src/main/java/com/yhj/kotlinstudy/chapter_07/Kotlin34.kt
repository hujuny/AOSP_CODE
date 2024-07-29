package com.yhj.kotlinstudy.chapter_07

import android.graphics.Color

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   :  枚举数据类
 */
class Kotlin34 {
}

//enum class State {
//    Idle, Busy
//}
//定义构造器
//enum class State(val id: Int) {
//    Idle(0), Busy(1)
//}
//实现接口
enum class State : Runnable {
    Idle, Busy {
        override fun run() {
            println("For every state.")
        }

    };

    override fun run() {
        println("For every state.")
    }
    /**
     * 枚举的父类是Enum，所以不能继承其他类
     */
    /**
     * 为枚举定义扩展
     */
    fun State.next(): State {
        return values().let {
            val i = (ordinal + 1) % it.size
            it[i]
        }
    }
}

/**
 * 枚举比较
 */



fun main() {

    State.Idle.name
    State.Idle.ordinal
    State.Idle.run()

    /**
     * 条件分支
     */
//    val value=when(state){
//        State.Idle->{0}
//        State.Busy->{1}
//    }

    /**
     * 区间
     */
    Color.BLUE..Color.GREEN

}