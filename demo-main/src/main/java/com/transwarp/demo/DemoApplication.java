package com.transwarp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by huangnx on 2018/8/13.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ImportResource(locations = "classpath:spring/application-aop.xml")
public class DemoApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
    }
}
