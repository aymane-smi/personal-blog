package com.example.personalblog;

import com.example.personalblog.Models.User;
import com.example.personalblog.Services.UserServices.UserServicesImpl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PersonalBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	CommandLineRunner run(UserServicesImpl userServices) {
		return args -> {
			userServices.saveUser(new User(null, "aymane", "aymane", "aymane belassiria"));
		};
	}
	
	@Bean
	PersonalBlogContext personalBlogContext() {
		return new PersonalBlogContext();
	}
	
}
