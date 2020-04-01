package com.bootcamp.shoppingApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShoppingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingAppApplication.class, args);
	}

}
