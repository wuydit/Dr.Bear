package com.bear.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wuyd
 */
@Slf4j
@EnableAsync
@SpringBootApplication
public class DrServerApplication {

	public static void main(String[] args) {
		log.info("HTTP server start!");
		ApplicationContext context = SpringApplication.run(DrServerApplication.class, args);
		Environment env = context.getBean(Environment.class);
		log.info("Server path: http://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
