package com.fastdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource("classpath:transaction.xml")
public class FastdfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastdfsApplication.class, args);
    }

}
