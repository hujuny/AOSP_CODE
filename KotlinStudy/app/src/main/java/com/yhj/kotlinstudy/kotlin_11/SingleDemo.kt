package com.yhj.kotlinstudy.kotlin_11

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/12
 *    @desc   : kotlin 五种单例
 */
class SingleDemo {

    /**
     * 饿汉式单例
     */
    //Java实现
//    public class SingletonDemo {
//        private static SingletonDemo instance=new SingletonDemo();
//        private SingletonDemo(){
//
//        }
//        public static SingletonDemo getInstance(){
//            return instance;
//        }
//    }
    //Kotlin实现
    object SingletonDemo
}

/**
 * 懒汉式单例
 */
//Java实现
//public class SingletonDemo {
//    private static SingletonDemo instance;
//    private SingletonDemo(){}
//    public static SingletonDemo getInstance(){
//        if(instance==null){
//            instance=new SingletonDemo();
//        }
//        return instance;
//    }
//}
//Kotlin实现
class SingletonDemo private constructor() {
    companion object {
        private var instance: SingletonDemo? = null
            get() {
                if (field == null) {
                    field = SingletonDemo()
                }
                return field
            }

        fun get(): SingletonDemo {
            //细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
            return instance!!
        }
    }
}

/**
 * 线程安全的懒汉式
 */
//Java实现
//public class SingletonDemo {
//    private static SingletonDemo instance;
//    private SingletonDemo(){}
//    public static synchronized SingletonDemo getInstance(){//使用同步锁
//        if(instance==null){
//            instance=new SingletonDemo();
//        }
//        return instance;
//    }
//}
//Kotlin实现
class Singleton1Demo private constructor() {
    companion object {
        private var instance: Singleton1Demo? = null
            get() {
                if (field == null) {
                    field = Singleton1Demo()
                }
                return field
            }

        @Synchronized
        fun get(): Singleton1Demo {
            return instance!!
        }
    }

}

/**
 * 双重校验锁式
 */
//Java实现
//public class SingletonDemo {
//    private volatile static SingletonDemo instance;
//    private SingletonDemo(){}
//    public static SingletonDemo getInstance(){
//        if(instance==null){
//            synchronized (SingletonDemo.class){
//                if(instance==null){
//                    instance=new SingletonDemo();
//                }
//            }
//        }
//        return instance;
//    }
//}
//kotlin实现
class Singleton2Demo private constructor() {
    companion object {
        //延迟属性Lazy
        val instance: Singleton2Demo by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Singleton2Demo()
        }
    }
}

/**
 * 静态内部类单例
 */
//Java实现
//public class SingletonDemo {
//    private static class SingletonHolder{
//        private static SingletonDemo instance=new SingletonDemo();
//    }
//    private SingletonDemo(){
//        System.out.println("Singleton has loaded");
//    }
//    public static SingletonDemo getInstance(){
//        return SingletonHolder.instance;
//    }
//}
//kotlin实现
class Singleton3Demo private constructor() {
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = Singleton3Demo()
    }

}

/**
 * 添加参数
 */
class Singleton4Demo private constructor(private val property: Int) {//这里可以根据实际需求发生改变

    companion object {
        @Volatile
        private var instance: Singleton4Demo? = null
        fun getInstance(property: Int) =
            instance ?: synchronized(this) {
                instance ?: Singleton4Demo(property).also { instance = it }
            }
    }
}

fun main() {
}