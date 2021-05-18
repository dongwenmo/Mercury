package com.cn.momo.util.page;

/**
 * dongwenmo 2021-04-17
 */
public class Test {
    public static void main(String[] args) {
        String keyWord = "misp.per_info.test";
        String[] words = keyWord.split("\\.",2);
        System.out.println(words.length);
        for(String i:words){
            System.out.print(1);
            System.out.println(i);
        }
    }
}