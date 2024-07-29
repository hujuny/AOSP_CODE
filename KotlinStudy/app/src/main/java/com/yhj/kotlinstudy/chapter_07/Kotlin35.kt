package com.yhj.kotlinstudy.chapter_07

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/01
 *    @desc   : 密封类  特殊的抽象类
 */
class Kotlin35 {


}

/**
 * 密封类的子类必须定义在与自身相同的文件中
 * 密封类的子类个数是有限的
 */

sealed class PlayState {
    //私有构造器
    constructor()
    constructor(int: Int)

    object Idle : PlayState()
    class Playing(val song: Kotlin35) : PlayState() {
        fun start() {}
        fun stop() {}
    }

    class Error(val error: Kotlin35) : PlayState() {
        fun recover() {}
    }

    class Player {
        var state: PlayState = Idle
        fun play(song: Kotlin35) {
            this.state = when (val state = this.state) {
                Idle -> {
                    Playing(song).also(Playing::start)
                }
                is Playing -> {
                    state.stop()
                    Playing(song).also(Playing::start)
                }
                is Error -> {
                    state.recover()
                    Playing(song).also(Playing::start)
                }
            }
        }
    }

    /**
     * 子类分支
     * 子类可数，分支完备
     */
//        this.state=when(val state=this.state){
//            Idle->{}
//            is Playing->{}
//            is Error->{}
//        }
}