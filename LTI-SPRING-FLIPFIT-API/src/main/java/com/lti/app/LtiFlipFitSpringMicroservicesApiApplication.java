package com.lti.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LtiFlipFitSpringMicroservicesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiFlipFitSpringMicroservicesApiApplication.class, args);
	}

}
