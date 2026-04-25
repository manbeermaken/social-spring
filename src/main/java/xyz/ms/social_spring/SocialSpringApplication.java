package xyz.ms.social_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class SocialSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialSpringApplication.class, args);
	}

}
