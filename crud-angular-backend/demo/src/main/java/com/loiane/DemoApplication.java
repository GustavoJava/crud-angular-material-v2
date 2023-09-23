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
					new Course(1L, "Java 17", "Back-end","Ativo"),
					new Course(2L, "AngularJS", "Front-end","Ativo"),
					new Course(3L, "Material Design", "Front-end","Ativo"),
					new Course(4L, "C++ Turbo", "Back-end","Inativo")
			);
			
			repository.saveAll(lista);
			
			
		};
	}
	
}
