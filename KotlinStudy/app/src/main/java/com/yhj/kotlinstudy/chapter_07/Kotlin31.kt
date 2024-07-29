package com.yhj.kotlinstudy.chapter_07

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   : 单例object
 */
class Kotlin31 {
}

//java单例
//public class Singleton{
//    public static final Singleton INSTANCE=new Singleton();
//}
/**
 * 饿汉式单例
 * 定义单例，类加载时实例化对象
 * Singleton既是类名也是对象名
 */
object Singleton:Runnable {

    //静态成员  @JvmStatic
    @JvmStatic
    var x: Int = 2

    //    @JvmField 不生成getter/setter
    fun y() {

    }
    //object的成员直接按照java静态成员生成字节码，对kotlin内部使用毫无影响，
// java调用object的成员可直接视同调用静态成员一般

    //不能自定义构造器,可定义若干init块
    init {

    }

    //object的类继承 继承父类，实现接口
    override fun run() {}

}

//伴生对象   companion object  与普通类同名的object
//java写法
//public class a{
//    public static void y(){}
//}
class Fooling {
    companion object {
        @JvmField
        var x: Int = 11

        @JvmStatic
        fun y() {
        }
    }
}


fun main() {
    Singleton.y()

}