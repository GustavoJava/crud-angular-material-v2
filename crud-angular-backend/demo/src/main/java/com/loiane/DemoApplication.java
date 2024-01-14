package com.loiane;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.loiane.enums.Category;
import com.loiane.model.Course;
import com.loiane.model.Lesson;
import com.loiane.repository.CourseRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner initDataBase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			for (int i = 0; i < 4000; i++) {
				
				Course course = new Course();
				course.setName("AngularJS com Spring " + i);
				course.setCategory(Category.BACK_END);

				Lesson lesson = new Lesson();
				lesson.setName("Introdução - O que é Spring boot");
				lesson.setYoutubeUrl("01234567890");
				lesson.setCourse(course);

				course.getLessons().add(lesson);

				Lesson lesson2 = new Lesson();
				lesson2.setName("Spring boot - Hello World");
				lesson2.setYoutubeUrl("01234567890");
				lesson2.setCourse(course);

				course.getLessons().add(lesson2);

				Lesson lesson3 = new Lesson();
				lesson3.setName("Java 17 - Novidades");
				lesson3.setYoutubeUrl("01234567890");
				lesson3.setCourse(course);

				course.getLessons().add(lesson3);

				Lesson lesson4 = new Lesson();
				lesson4.setName("AngularJS - Introdução");
				lesson4.setYoutubeUrl("01234567890");
				lesson4.setCourse(course);

				course.getLessons().add(lesson4);

				courseRepository.save(course);
			}
		};
	}

//	@Bean
//	CommandLineRunner initDatabase(CourseRepository repository) {
//		return args -> {
//			repository.deleteAll();
//			
//			List<Lesson> lessons = List.of(
//					new Lesson(1L,"Introdução", "https://www.youtube.com/watch?v=1"),
//					new Lesson(2L,"Introdução", "https://www.youtube.com/watch?v=2"),
//					new Lesson(3L,"Introdução", "https://www.youtube.com/watch?v=3"),
//					new Lesson(4L,"Introdução", "https://www.youtube.com/watch?v=4"),
//					new Lesson(5L,"Introdução", "https://www.youtube.com/watch?v=5")
//			);
//			
//			List<Course> lista = List.of(
//					new Course(1L, "Java 17", Category.BACK_END, Status.ATIVO,lessons.indexOf(lessons.get(0))),
//					new Course(2L, "AngularJS", Category.FRONT_END,Status.ATIVO, lessons),
//					new Course(3L, "Material Design", Category.FRONT_END,Status.ATIVO, lessons),
//					new Course(4L, "C++ Turbo", Category.BACK_END,Status.ATIVO, lessons),
//					new Course(5L, "Python", Category.BACK_END,Status.INATIVO, lessons)
//			);
//			
//			repository.saveAll(lista);
//			
//			
//		};
//	}

}
