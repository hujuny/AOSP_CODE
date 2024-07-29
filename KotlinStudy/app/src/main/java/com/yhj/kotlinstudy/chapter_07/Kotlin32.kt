package com.yhj.kotlinstudy.chapter_07

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   : 内部类
 */
class Kotlin32 {
}

//java内部类
//public class Outer{
//    class inner{}
//    static class StaticInner{}
//}
/**
 * 定义
 */
class Outer {
    //非静态内部类
    inner class Inner

    //静态内部类
    class StaticInner

    //非静态，实例持有外部类实例引用
}

/**
 * 内部object
 */
object OuterObject{
    object StaticInnerObject
    //内部object不存在非静态情况，故不可用inner修饰
}

/**
 * 本地类
 * 本地函数
 */
class localClass:Runnable,Cloneable{
    override fun run() {

    }

}

fun main() {

    Outer().Inner()
    Outer.StaticInner()

    /**
     * 匿名内部类
     * object省略了名字即匿名
     * 可以继承父类，实现多个接口
     * 匿名内部类定义在非静态区域，会持有外部类的引用；定义在静态区域没有
     */
    object : Runnable,Cloneable{
        override fun run() {

        }
    }

    //交叉类型，Runnable+Cloneable  联合类型支持Runnable/Cloneable
    val runnableCloneable=object  :Cloneable,Runnable{
        override fun run() {

        }
    }

}