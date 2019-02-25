package com.fastdfs.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName Log4jConfigure
 * @description:
 **/
@ComponentScan
@ConfigurationProperties("classpath:log4j.properties")
public class Log4jConfigure {
}
