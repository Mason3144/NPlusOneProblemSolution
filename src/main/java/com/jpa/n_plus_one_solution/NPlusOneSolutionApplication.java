package com.jpa.n_plus_one_solution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.jpa.n_plus_one_solution.entities")
public class NPlusOneSolutionApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "n-plus-one-problem"); // N + 1 문제 관련 예제 코드
		SpringApplication.run(NPlusOneSolutionApplication.class, args);
	}

}
