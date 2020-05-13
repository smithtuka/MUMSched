package mum.swe.mumsched.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mum.swe.mumsched.enums.MonthEnum;
import mum.swe.mumsched.model.Faculty;
import mum.swe.mumsched.model.User;
import mum.swe.mumsched.repository.FacultyRepository;
import mum.swe.mumsched.service.FacultyService;
import mum.swe.mumsched.service.UserService;

/**
 * @author Smith T

 * May 10, 2020
 */
@Service("facultyService")
public class FacultyServiceImpl  implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Faculty save(Faculty faculty) {
    	User user = faculty.getUser();    	
    	if(faculty.getId() == null)
    	{
    		user = userService.save(user);
    		faculty.setUser(user);
    	}
    	else {
			if(user.getPasswordConfirm() != null && !user.getPasswordConfirm().isEmpty())
				user.setPassword(bCryptPasswordEncoder.encode(user.getPasswordConfirm()));
    	}
    		//userService.setUserPassword(faculty.getUser());
		return facultyRepository.save(faculty);	
    }

	@Override
	public Page<Faculty> findAll(Pageable pageable) {
		return facultyRepository.findAll(pageable);
	}
	
	@Override
	public List<Faculty> findAll() {
		return facultyRepository.findAll();
	}
	
	@Override
	public List<Faculty> findAllByMonth(MonthEnum month) {
		return facultyRepository.findAllByMonth(month);
	}

	@Override
	public Faculty findOne(Long id) {
		return facultyRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		facultyRepository.delete(id);
	}

	@Override
	public Faculty findByUsername(String userName) {
		return facultyRepository.findByUsername(userName);
	}
}
