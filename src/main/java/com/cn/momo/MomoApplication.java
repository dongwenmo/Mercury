package com.cn.momo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 * dongwenmo 2021-05-15
 */
@SpringBootApplication
@MapperScan({"com.cn.momo.apps.*.mapper", "com.cn.momo.system.*.mapper"})
public class MomoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MomoApplication.class, args);
    }
}
