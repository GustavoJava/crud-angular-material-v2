package com.loiane.dto.mapper;

import org.springframework.stereotype.Component;

import com.loiane.dto.CourseDTO;
import com.loiane.enums.Category;
import com.loiane.model.Course;

@Component
public class CourseMapper {
	
	public CourseDTO toDTO(Course course) {
		if (course == null) {
			return null;
		}
		return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue());
	}
	
	public Course toEntity(CourseDTO courseDTO) {
		Course course = new Course();
		
		if (courseDTO == null) {
			return null;
		}
		
		if (course.getId() != null) {
			course.setId(courseDTO.id());
		}
		
		course.setName(courseDTO.name());
		course.setCategory(this.convertCategoryValue(courseDTO.category()));
		return course;
	}
	
	public Category convertCategoryValue(String value) {
		if (value == null) {
			return null;
		}
		
		return switch (value) {
			case "Front-end" -> Category.FRONT_END;
			case "Back-end"  ->  Category.BACK_END;
			default -> throw new IllegalArgumentException("Categoria inválida: " + value);
		};
		
		//return Stream.of(Category.values())
//				 .filter(c -> c.getValue().equals(value))
//				 .findFirst()
//				 .orElseThrow(IllegalArgumentException::new); 
	}

}
