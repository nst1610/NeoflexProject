package com.github.nst1610.neoflex.project.dossier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.github.nst1610.neoflex.project.dossier.client")
public class DossierApplication {

	public static void main(String[] args) {
		SpringApplication.run(DossierApplication.class, args);
	}

}
