package com.loiane.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.loiane.dto.CourseDTO;
import com.loiane.dto.mapper.CourseMapper;
import com.loiane.exception.RecordNotFoundException;
import com.loiane.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;

	public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
		this.courseRepository = courseRepository;
		this.courseMapper = courseMapper;
	}

	public List<CourseDTO> list() {
		final String FIELD = "name";
		
		return this.courseRepository.findAll(Sort.by(Sort.Direction.ASC, FIELD))
				.stream()
				//.map(course -> courseMapper.toDTO(course))
				.map(courseMapper::toDTO)
				.collect(Collectors.toList());
		
	}

	public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
		return this.courseRepository.findById(id)
				   .map(courseMapper::toDTO)
				   .orElseThrow(() -> new RecordNotFoundException(id));
	}

	public CourseDTO create(@Valid @NotNull CourseDTO course) {
		return this.courseMapper.toDTO(this.courseRepository.save(this.courseMapper.toEntity(course)));
	}

	public CourseDTO update(@PathVariable @NotNull @Positive Long id, @Valid @NotNull CourseDTO course) {
		return this.courseRepository.findById(id).map(recordFound -> {
			recordFound.setName(course.name());
			recordFound.setCategory(course.category());
			return this.courseMapper.toDTO(this.courseRepository.save(recordFound));
		}).orElseThrow(() -> new RecordNotFoundException(id));
	}

	public void delete(@PathVariable @NotNull @Positive Long id) {
		this.courseRepository
			.delete(this.courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
	}

}
