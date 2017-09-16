package me.mawood.data_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
public class DataApiApplication
{
    // Goal to build jar: clean package spring-boot:repackage
	public static void main(String[] args) {
		SpringApplication.run(DataApiApplication.class, args);
	}
}
