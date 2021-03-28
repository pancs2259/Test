package com.pcs;

import java.util.ArrayList;
import java.util.List;

/**
 * 算法service
 */
public class CalcuService {

    /**
     * [1,1,2,3,5,8,13,21.....N] 规律是：第N个数 = 第N-1个数 + 第N-2个数
     * arr[N-1] = arr[N-1-1] + arr[N-2-1]
     */
    public List<Integer> calcuTest1(Integer index){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(1);
        if(index != null){
            if(index > 2) {
                for (int i = 2; i < index; i++) {
                    list.add(list.get(i - 2) + list.get(i - 1));
                }
            }
            return list;
        }
        return null;
    }

}
