package com.webservice;

import com.utils.ResourcesHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJpaPostgreSqlApplication {

	public static void main(String[] args) {
			//ResourcesHandler.setPath("debug");
		SpringApplication.run(SpringJpaPostgreSqlApplication.class, args);
	}
}
