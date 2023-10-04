package com.loiane;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loiane.enums.Category;
import com.loiane.enums.Status;
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
					new Course(1L, "Java 17", Category.BACK_END, Status.ATIVO),
					new Course(2L, "AngularJS", Category.FRONT_END,Status.ATIVO),
					new Course(3L, "Material Design", Category.FRONT_END,Status.ATIVO),
					new Course(4L, "C++ Turbo", Category.BACK_END,Status.ATIVO),
					new Course(5L, "Python", Category.BACK_END,Status.INATIVO)
			);
			
			repository.saveAll(lista);
			
			
		};
	}
	
}
