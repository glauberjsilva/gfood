package com.glauber.gfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.glauber.gfood.infrastructure.respository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EntityScan(basePackages = "com.glauber.gfood") 
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class GfoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GfoodApiApplication.class, args);
	}

}
