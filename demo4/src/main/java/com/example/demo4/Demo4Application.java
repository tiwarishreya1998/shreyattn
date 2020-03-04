package com.example.demo4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Demo4Application {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(Demo4Application.class, args);

		//The Application Context is Spring's advanced container.
		// Similar to BeanFactory, it can load bean definitions, wire beans together, and dispense beans upon request.
		// Additionally, it adds more enterprise-specific functionality such as the ability to resolve textual messages from a properties file and the ability to publish application events to interested event listeners.
		// This container is defined by org.springframework.context.ApplicationContext interface.

		HotelBooking hotelBooking = applicationContext.getBean(HotelBooking.class);
		hotelBooking.hotelType();
	}
}