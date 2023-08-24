package com.github.nst1610.neoflex.project.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.github.nst1610.neoflex.project.application.client")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
