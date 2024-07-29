package com.yhj.kotlinstudy.chapter_06.kotlin_22;

import java.util.HashSet;

/**
 * @author : 杨虎军
 * @date :  2021/04/17
 * @desc :
 */
public class EventManager {

   public interface OnEventListener{
        void onEvent(int event);
    }

    public HashSet<OnEventListener> onEventListeners;
    public void addOnEventListener(OnEventListener onEventListener){}


    public void removeOnEventListener(OnEventListener onEventListener){
        this.onEventListeners.remove(onEventListener);
    }
}
