package mum.swe.mumsched.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.model.Faculty;

/**
 * @author Smith T

 * May 10, 2020

 */
public interface FacultyService {    
	Faculty save(Faculty faculty);
	Faculty findOne(Long id);
	void delete(Long id);
	Page<Faculty> findAll(Pageable pageable);  	
	Faculty findByUsername(String userName);	
	
	/**
	 * @author Elsabeth

	 * @return
	 */
	List<Faculty> findAll();
	List<Faculty> findAllByMonth(MonthEnum month);  
}
