package com.cn.momo.util;

/**
 * 排序
 * dongwenmo 2021-03-01
 */
public class SortUtil {
    /**
     * 冒泡排序，正序
     * dongwenmo 2021-03-01
     */
    public static int[] bubbleSort(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        return numbers;
    }

    /**
     * 冒泡排序，倒序
     * dongwenmo 2021-03-01
     */
    public static int[] bubbleSortDesc(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (numbers[j] < numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        return numbers;
    }

    /**
     * 冒泡排序，默认正序，识别desc关键字倒序
     * dongwenmo 2021-03-01
     */
    public static int[] bubbleSort(int[] numbers, String orderBy) {
        orderBy = orderBy.toLowerCase();
        if ("desc".equals(orderBy)) {
            return bubbleSortDesc(numbers);
        } else {
            return bubbleSort(numbers);
        }
    }

    /**
     * 冒泡排序，正序
     * dongwenmo 2021-03-01
     */
    public static double[] bubbleSort(double[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    double temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        return numbers;
    }

    /**
     * 冒泡排序，倒序
     * dongwenmo 2021-03-01
     */
    public static double[] bubbleSortDesc(double[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i; j++) {
                if (numbers[j] < numbers[j + 1]) {
                    double temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        return numbers;
    }

    /**
     * 冒泡排序，默认正序，识别desc关键字倒序
     * dongwenmo 2021-03-01
     */
    public static double[] bubbleSort(double[] numbers, String orderBy) {
        orderBy = orderBy.toLowerCase();
        if ("desc".equals(orderBy)) {
            return bubbleSortDesc(numbers);
        } else {
            return bubbleSort(numbers);
        }
    }


    public static void main(String[] args) {
        int[] numbers = new int[]{1, 5, 8, 13, 4, 56, 7, 1, 23, 45};
        bubbleSortDesc(numbers);
        System.out.println(TransUtil.ints2string(numbers));
    }
}
