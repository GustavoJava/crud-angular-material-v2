package com.loiane.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.loiane.exception.RecordNotFoundException;
import com.loiane.model.Course;
import com.loiane.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

	private CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public List<Course> list() {
		final String FIELD = "name";
		return this.courseRepository.findAll(Sort.by(Sort.Direction.ASC, FIELD));
	}

	public Course findById(@PathVariable @NotNull @Positive Long id) {
		return this.courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
	}

	public Course create(@Valid Course course) {
		return this.courseRepository.save(course);
	}

	public Course update(@PathVariable @NotNull @Positive Long id, @Valid Course course) {
		return this.courseRepository.findById(id).map(recordFound -> {
			recordFound.setName(course.getName());
			recordFound.setCategory(course.getCategory());
			return this.courseRepository.save(recordFound);
		}).orElseThrow(() -> new RecordNotFoundException(id));
	}

	public void delete(@PathVariable @NotNull @Positive Long id) {
		this.courseRepository
			.delete(this.courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
	}

}