package com.pcs;

import org.junit.Test;

public class Main {

    public static void main(String[] args) {
//        Main main = new Main();
//        main.testCalcu();//海鼎面试算法编程题

        ThreadService threadService = new ThreadService();
        threadService.ThreadTest12();
    }

    /***
     * 海鼎面试算法编程题
     * ['1','2','+','3','*'] --> (1+2)*3  --> 9
     * ['4','13','5','/','+'] --> 4+(13/5) --> 6
     * 操作符号可能为【+,-,*,/】按这个规律用代码实现。(编程题)
     */
    @Test
    public void testCalcu(){
        try {
            MyService service = new MyService();
            String[] arr1 = new String[]{"1", "2", "+", "3", "*"};
            String[] arr2 = new String[]{"4", "13", "5", "/", "+"};
            int result1 = service.calcu(arr1);
            int result2 = service.calcu(arr2);
            System.out.println("result1:" + result1);
            System.out.println("result2:" + result2);

            System.out.println("------------验证公式正确性-------------");
            String[] arr3 = new String[]{"2", "4", "1", "13", "5", "/", "+", "*","-"};
            int result3 = service.calcu(arr3);
            System.out.println("result3:" + result3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ////实现两线程交替打印 1212
    @Test
    public void testThread(){
        try {
            ThreadService threadService = new ThreadService();
            threadService.ThreadTest();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //实现两线程交替打印 abab
    @Test
    public void testThreadCD(){
        try {
            ThreadService threadService = new ThreadService();
            threadService.ThreadTestCD();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //实现两线程交替打印 100以内 奇偶数交替打印
    @Test
    public void testThread12(){
        try {
            ThreadService threadService = new ThreadService();
            threadService.ThreadTest12();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
