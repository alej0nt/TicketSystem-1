package com.leoalelui.ticketsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketsystemApplication.class, args);
		System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(10).encode("TuPasswordSegura123"));
	}

}
