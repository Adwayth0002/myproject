package com.scope.Bootregistrationform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.Bootregistrationform.model.Course;


public interface courseRepository extends JpaRepository<Course, Integer> {
	public Course findById(int courseid);
}
