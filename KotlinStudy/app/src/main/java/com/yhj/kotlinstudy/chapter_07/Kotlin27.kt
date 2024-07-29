package com.yhj.kotlinstudy.chapter_07

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/21
 *    @desc   :类与成员的可见性
 */
class Kotlin27 {
    //internal  模块内可见   无default
    //protected 类内及子类可见
    //private  类或文件内可见
    //public 默认
    /**
     * 模块
     * 任务中一次调用kotlinc命令的文件
     * 可以认为是一个jar包或aar
     */

    /**
     * internal vs  default
     * 一般由SDK或公共组件开发者用于隐藏模块内部的细节实现
     * default可通过创建外部创建相同包名来访问，访问控制非常弱
     * default会导致不同抽象层次的类聚集到相同包下
     * internal可方便处理内外隔离，提升模块代码内聚减少接口暴露
     * internal修饰的kotlin类或成员在java当中可以直接访问  注意：代码报错但是编译器能运行；可以设置jvmName注解就访问不到了
     */

    /**
     * 构造器私有化
     */
    private constructor()

    /**
     * 私有化属性，外部无法访问
     */
    class Kotlin27(private var age: Int) {}
    class a(var age: Int, var name: String) {
        private var firstName = ""
    }

    /**
     * 私有化属性的setter,外部只能读取
     * 注意
     * setter的可见性不能大于属性的可见性
     * getter的可见性必须和属性保持一致
     */
    class b() {
        var firstName = ""
            private set
    }
    /**
     * 顶级声明的可见性
     * 顶级声明指在文件中定义的属性、函数、类等
     * 顶级声明不支持protected（没有包内可见的含义）
     * 顶级声明被private修饰表示文件内部可见
     */

}