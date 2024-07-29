package com.yhj.kotlinstudy.chapter_08

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   :模拟Self Type
 */
class Kotlin43 {

    open class NotificationBuilder {
        fun title(title: String): NotificationBuilder {
            this.title(title)
            //this 父类类型，但实际上是子类的类型
            return this
        }
    }
}

private val EmptyFunction = {}

open class Notification(val title: String, val content: String)
class ConfirmNotification(
    title: String,
    content: String,
    val onConfirm: OnConfirm,
    val onCancel: OnCancel
)

interface SelfType<Self> {
    val self: Self
        get() = this as Self
}

open class NotificationBuilder<Self : NotificationBuilder<Self>> : SelfType<Self> {
    protected var title = ""
    protected var content = ""
    fun title(title: String): Self {
        this.title(title)
        return self
    }

    fun content(content: String): Self {
        this.content(content)
        return self
    }

    open fun build() = Notification(this.title, this.content)
}

class ConfirmNotificationBuilder : NotificationBuilder<ConfirmNotificationBuilder>() {
    private var onConfirm: OnConfirm = EmptyFunction
    private var onCancel: OnCancel = EmptyFunction

    fun OnConfirm(onConfirm: OnConfirm): ConfirmNotificationBuilder {
        this.onConfirm = onConfirm
        return this
    }

    fun onCancel(onCancel: OnCancel): ConfirmNotificationBuilder {
        this.onCancel = onCancel
        return this
    }

    override fun build() = ConfirmNotification(title, content, onConfirm, onCancel)
}

fun main() {

}