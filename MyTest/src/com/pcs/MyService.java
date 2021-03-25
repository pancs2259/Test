package com.pcs;

import java.util.ArrayList;
import java.util.List;

public class MyService {

    /**
     * ['1','2','+','3','*'] --> (1+2)*3  --> 9
     * ['4','13','5','/','+'] --> 4+(13/5) --> 6   操作符号可能为【+,-,*,/】按这个规律用代码实现。
     * @param arr
     * @return
     */
    public int calcu(String[] arr) throws Exception{
        int returnValue = 0;
        returnValue = eval(arr);
        return returnValue;
    }

    private Integer eval(String[] arr) throws Exception{
        String operator = "+-*/";
        int index = 0;//找到第一个运算操作符所在下标
        for (int i = 0; i < arr.length; i++) {
            if(operator.contains(arr[i])){
                index = i;
                break;
            }
        }
        if(arr.length < 3){
            throw new Exception("请检查数组数据，3个元素为一组！");
        }
        String a = arr[index-2];//运算符前面的第2个元素
        String b = arr[index-1];//运算符前面的第1个元素
        String c = arr[index];//运算符
        Integer num = 0;//运算符前面2个元素按该运算符进行运算之后的值
        if("+".equals(c)){
            num = Integer.parseInt(a) + Integer.parseInt(b);
        }else if("-".equals(c)){
            num = Integer.parseInt(a) - Integer.parseInt(b);
        }else if("*".equals(c)){
            num = Integer.parseInt(a) * Integer.parseInt(b);
        }else if("/".equals(c)){
            num = Integer.parseInt(a) / Integer.parseInt(b);
        }else{
            throw new Exception("运算符【"+c+"】错误，请检查数组数据！");
        }
        //将原数组元素(操作符前2个元素除外)依次放入集合中，当前运算符元素替换成运算之后的值
        /**
         * 例如：arr['1','2','+','3','*'] 转换后 集合list元素是｛"3","3","*"｝
         * arr['4','13','5','/','+'] 转换后 集合list元素是｛"4","2","+"｝
         */
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if(i == (index -2) || i == (index -1)){//当前运算符操作的两个元素不记录
                continue;
            }else if(i == index){//当前运算符所在位置替换成运算之后的值，加入集合 保持有序性
                list.add(num.toString());
            }else{
                list.add(arr[i]);
            }
        }
        String[] arr2 = new String[]{};
        String[] arr3 = list.toArray(arr2);
        //集合转换成数组后，如果数组元素个数不为1，则继续递归调用，直到数组元素个数为1，则该元素即是运算后的结果。
       if(arr3.length == 1){
           return Integer.parseInt(arr3[0]);
       }else{
           return eval(arr3);
       }
    }


}
