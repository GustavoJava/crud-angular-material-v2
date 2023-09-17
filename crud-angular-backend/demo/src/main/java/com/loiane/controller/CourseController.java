package com.loiane.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loiane.model.Course;
import com.loiane.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
	
	private final CourseRepository repository;
	
	@GetMapping
	public List<Course> list() {
		return this.repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Course create(@RequestBody Course course) {
		System.out.println(course);
		return this.repository.save(course);
	}

}
