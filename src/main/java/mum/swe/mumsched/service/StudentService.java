package mum.swe.mumsched.service;

import java.util.List;

import mum.swe.mumsched.model.Student;

/**
 * @author Smith T

 * May 10, 2020

 */
public interface StudentService {    
	Student save(Student student);
	Student findOne(Long id);
	void delete(Long id);
	List<Student> findAll();  
	
	Student findByUsername(String userName);
}
