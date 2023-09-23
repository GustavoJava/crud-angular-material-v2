package com.loiane.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loiane.model.Course;
import com.loiane.repository.CourseRepository;

import lombok.AllArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
	
	private final CourseRepository repository;
	
	@GetMapping
	public  @ResponseBody List<Course> list() {
		return this.repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
		return this.repository.findById(id)
				   .map(recordFound -> ResponseEntity.ok().body(recordFound))
				   .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Course create(@RequestBody @Valid Course  course) {
		System.out.println(course);
		return this.repository.save(course);
	}
	
	@PutMapping("/{id}")
	public  ResponseEntity<Course>  update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
		return this.repository.findById(id)
				   .map(recordFound -> {
					   recordFound.setName(course.getName());
					   recordFound.setCategory(course.getCategory());
					   Course updated = this.repository.save(recordFound);
					   return ResponseEntity.ok().body(updated);
				   })
				   .orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
		return this.repository.findById(id)
				   .map(recordFound -> {
					   this.repository.deleteById(id);
					   return ResponseEntity.noContent().<Void>build();
				   })
				   .orElse(ResponseEntity.notFound().build());
	}

}
