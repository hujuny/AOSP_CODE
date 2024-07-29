package com.yhj.kotlinstudy.chapter_07.kotlin_36;

/**
 * @author : 杨虎军
 * @date :  2021/05/01
 * @desc :
 */
public class EnumTest {

    public static final int WALK = 0;
    public static final int BUS = 1;
    public static final int CAR = 2;

    public static void setRouteType(@RouteTypeDef int routeType) {
    }

    public static void main(String[] args) {
        EnumTest.setRouteType(BUS);
    }
}
