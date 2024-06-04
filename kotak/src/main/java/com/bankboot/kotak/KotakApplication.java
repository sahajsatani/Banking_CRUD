package com.bankboot.kotak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KotakApplication {
	public static void main(String[] args) {
		SpringApplication.run(KotakApplication.class, args);
		System.out.println("API Start......");
	}
}
