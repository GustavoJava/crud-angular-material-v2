package com.loiane;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loiane.model.Course;
import com.loiane.repository.CourseRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository repository) {
		return args -> {
			repository.deleteAll();
			
			List<Course> lista = List.of(
					new Course(1L, "Java 17", "back-end"),
					new Course(2L, "AngularJS", "front-end"),
					new Course(3L, "Material Design", "front-end"),
					new Course(4L, "C++ Turbo", "back-end")
			);
			
			repository.saveAll(lista);
			
			
		};
	}
	
}
