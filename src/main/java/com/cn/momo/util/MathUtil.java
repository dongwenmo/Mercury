package com.cn.momo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongwenmo
 * @create 2020-05-13 19:20
 */
public class MathUtil {
    // 数组求和
    public static int getSum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

    // 多个数组求和
    public static int getSum(int[]... array) {
        int sum = 0;
        for (int[] i : array) {
            sum += getSum(i);
        }
        return sum;
    }

    // 数组求最大值
    public static int getMax(int[] array) {
        int max = 0;
        for (int i : array) {
            max = max > i ? max : i;
        }
        return max;
    }

    // 多个数组求最大值
    public static int getMax(int[]... array) {
        int[] maxArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            maxArray[i] = getMax(array[i]);
        }

        return getMax(maxArray);
    }

    /**
     * 数组去重
     * dongwenmo 2021-01-26
     */
    public static List<Integer> forAAtoA(List<Integer> a){
        List<Integer> list = new ArrayList<>();
        boolean flag;
        for(Integer i:a){
            if (i == null) {
                continue;
            }
            flag = false;
            for(Integer j:list){
                if(i.equals(j)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                list.add(i);
            }
        }
        return list;
    }

    /**
     * 差集：A - B
     * 返回在A集合中，但不在B集合中的元素
     * dongwenmo 2021-01-26
     */
    public static List<Integer> inAnotB(List<Integer> a, List<Integer> b) {
        List<Integer> list = new ArrayList<>();
        for (Integer i : a) {
            if (i == null) {
                continue;
            }
            list.add(i);
            for (Integer j : b) {
                if (i.equals(j)) {
                    list.remove(i);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 取并集
     * dongwenmo 2021-01-26
     */
    public static List<Integer> inAandB(List<Integer> a, List<Integer> b) {
        List<Integer> list = new ArrayList<>();
        list.addAll(a);
        list.addAll(b);

        return forAAtoA(list);
    }
}
