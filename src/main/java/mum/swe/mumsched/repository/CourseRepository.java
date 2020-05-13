package mum.swe.mumsched.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mum.swe.mumsched.model.Course;

/**
 * @author Rachel
 * May 10, 2020
 */
@Repository("courseRepository")
public interface CourseRepository extends JpaRepository<Course, Long> {
	
	@Query("SELECT c FROM Course c")
	public Iterable<Course> courseList(Sort sort);
	
	public Course findOneByCode(String code);
}

