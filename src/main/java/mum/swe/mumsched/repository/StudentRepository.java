package mum.swe.mumsched.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mum.swe.mumsched.model.Student;

/**
 * @author Smith 
 * May 10, 2020
 */
@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	@Query("select s from mum.swe.mumsched.model.Student s join s.user u where u.username = :userName")
	public Student findByUsername(@Param("userName") String userName);
}